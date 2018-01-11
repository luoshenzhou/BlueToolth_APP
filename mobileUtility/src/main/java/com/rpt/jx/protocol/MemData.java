package com.rpt.jx.protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemData implements IBody {
	private short memoryAddr;
	private byte dataLength;
	private byte[] data;

	public MemData(){
		
	}
	
	public MemData(short memoryAddr,byte dataLength){
		this(memoryAddr,dataLength,null);
	}
	
	public MemData(short memoryAddr,byte dataLength,byte[] data){
		this.memoryAddr=memoryAddr;
		this.dataLength=dataLength;
		this.data=data;

	}
	
	@Override
	public IProtocol fromByte(byte[] data) {
		try {
			if (data == null || data.length < 3)
				return null;
			if (data.length != data[2] + 3)
				return null;
			memoryAddr = (short) (data[0] << 8 | data[1]);
			dataLength = data[2];
			this.data = new byte[dataLength];
			for (int i = 0; i < dataLength; i++) {
				this.data[i] = data[3 + i];
			}
		} catch (Exception e) {
			return null;
		}
		return this;
	}

	@Override
	public byte[] toByte() {
		byte[] bs = new byte[3 + this.dataLength];
		bs[0] = (byte) (memoryAddr >> 8 & 0x00FF);
		bs[1] = (byte) (memoryAddr & 0x00FF);
		bs[2] = dataLength;
		for (int i = 0; i < dataLength; i++) {
			bs[3 + i] = this.data[i];
		}
		return bs;
	}

	public short getMemoryAddr() {
		return memoryAddr;
	}

	public void setMemoryAddr(short memoryAddr) {
		this.memoryAddr = memoryAddr;
	}
	
	public void setMemoryAddr(byte hiByte,byte lowByte) {
		this.memoryAddr=(short)(hiByte<<8 | lowByte);
	}

	public byte getDataLength() {
		return dataLength;
	}

	public void setDataLength(byte dataLength) {
		this.dataLength = dataLength;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	static public List<MemData> split(byte[] data,boolean includeContent) throws Exception {
		List<MemData> result=new ArrayList<MemData>();
		
		if (data == null || data.length < 3){
			return result;
		}
		
		//������
		if(includeContent){
			MemData md=new MemData();
			byte[] bs1=Arrays.copyOfRange(data, 0, 3+data[2]);
			IProtocol memData=md.fromByte(bs1);
			if(memData!=null){
				result.add(md);
				byte[] bs2=Arrays.copyOfRange(data, bs1.length, data.length);
				result.addAll(split(bs2,includeContent));
			}else{
				throw new Exception("Parse Error！");
			}
		}else{
			MemData md=new MemData();
			byte[] bs1=Arrays.copyOfRange(data, 0, 3);
			md.setMemoryAddr(bs1[0],bs1[1]);
	        md.setDataLength(bs1[2]);
	        result.add(md);
	        byte[] bs2=Arrays.copyOfRange(data, 3, data.length);
	        result.addAll(split(bs2,includeContent));
		}
		return result;
	}
	
	static public byte[] combine(List<MemData> data,boolean includeContent) throws Exception {
		if(data==null || data.size()==0) {
			throw new Exception("Paramemter is null or size equals 0");
		}
		byte[] result=null;
		if(includeContent){
			for(MemData md:data){
				byte[] bs=md.toByte();
				result=CommonUtil.concat(result, bs);
			}
		}else{
			for(MemData md:data){
				byte[] bs=new byte[3];
				bs[0]=(byte)(md.getMemoryAddr()>>8 & 0x00FF);
				bs[1]=(byte)(md.getMemoryAddr() & 0x00FF);
				bs[2]=md.getDataLength();
				result=CommonUtil.concat(result, bs);
			}
		}
		return result;
	}


}
