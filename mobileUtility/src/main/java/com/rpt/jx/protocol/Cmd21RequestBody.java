package com.rpt.jx.protocol;

import java.util.Arrays;
import java.util.List;

public class Cmd21RequestBody implements IBody{
	private byte moduleAddr;
	private byte cmdTag;
	private List<MemData> data;

	public Cmd21RequestBody(){	
		this.cmdTag=0x21;
		this.moduleAddr=0x42;
	}
	
	public Cmd21RequestBody(byte moduleAddr){	
		this.cmdTag=0x21;
		this.moduleAddr=moduleAddr;
	}
	
	@Override
	public IBody fromByte(byte[] data) {
		if(data==null || data.length<=5) return null;
		try{
			this.moduleAddr=data[0];
			this.cmdTag=data[1];
			byte[] bs=Arrays.copyOfRange(data, 2, data.length);
			this.data=MemData.split(bs,false) ;
		}catch(Exception e){
			return null;
		}
		return this;
	}
	
	@Override
	public byte[] toByte() {
		try{
			byte[] bs1=new byte[2];
			bs1[0]=this.moduleAddr;
			bs1[1]=this.cmdTag;
			byte[] bs2=MemData.combine(data,false);
			return CommonUtil.concat(bs1, bs2);
		}catch(Exception e){
			return null;
		}
	}
	
	public byte getModuleAddr() {
		return moduleAddr;
	}
	public void setModuleAddr(byte moduleAddr) {
		this.moduleAddr = moduleAddr;
	}
	public byte getCmdTag() {
		return cmdTag;
	}
	public void setCmdTag(byte cmdTag) {
		this.cmdTag = cmdTag;
	}
	public List<MemData> getData() {
		return data;
	}
	public void setData(List<MemData> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Cmd21Request [moduleAddr=" + moduleAddr + ", cmdTag=" + cmdTag
				+ ", data=" + data + "]";
	}
	
//	public static void main(String[] args) throws Exception{
//		Cmd21RequestBody cmd=new Cmd21RequestBody();
//		//byte[] bs1= new byte[]{0x42,0x23,0x00,0x40,0x03,0x04,0x05,0x06,0x00,0x40,0x02,0x05,0x06,0x00,0x40,0x02,0x05,0x06,0x00,0x40,0x01,0x01};
//		byte[] bs1= new byte[]{0x42,0x23,0x00,0x40,0x03,0x00,0x40,0x02,0x00,0x40,0x02,0x00,0x40,0x01};
//		IProtocol body=cmd.fromByte(bs1);
//		System.out.println(body);
//		System.out.println(cmd);
//		byte[] bs2=cmd.toByte();
//
//		CommonUtil.println(bs1);
//		CommonUtil.println(bs2);
//
//	}
	
}
