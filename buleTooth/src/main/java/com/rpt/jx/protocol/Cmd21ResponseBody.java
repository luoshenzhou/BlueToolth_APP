package com.rpt.jx.protocol;

import java.util.Arrays;
import java.util.List;

public class Cmd21ResponseBody implements IBody{
	private byte answerTag;
	private List<MemData> data;
	
	public Cmd21ResponseBody(){	
		this.answerTag=0x00;
	}
	
	@Override
	public IProtocol fromByte(byte[] data) {
		if(data==null || data.length<=4) return null;
		try{
			this.answerTag=data[0];
			byte[] bs=Arrays.copyOfRange(data, 1, data.length);
			this.data=MemData.split(bs,true) ;
		}catch(Exception e){
			return null;
		}
		return this;
	}
	
	@Override
	public byte[] toByte() {
		try{
			byte[] bs1=new byte[]{this.answerTag};
			byte[] bs2=MemData.combine(data,true);
			return CommonUtil.concat(bs1, bs2);
		}catch(Exception e){
			return null;
		}
	}
	
	public byte getCmdTag() {
		return answerTag;
	}
	public void setCmdTag(byte cmdTag) {
		this.answerTag = cmdTag;
	}
	public List<MemData> getData() {
		return data;
	}
	public void setData(List<MemData> data) {
		this.data = data;
	}
	

	public static void main(String[] args) throws Exception{
		Cmd21ResponseBody cmd=new Cmd21ResponseBody();
		byte[] bs1= new byte[]{0x00,0x00,0x40,0x03,0x04,0x05,0x06,0x00,0x40,0x02,0x05,0x06,0x00,0x40,0x02,0x05,0x06,0x00,0x40,0x01,0x01};
		IProtocol body=cmd.fromByte(bs1);
		System.out.println(body);
		System.out.println(cmd);
		byte[] bs2=cmd.toByte();

		CommonUtil.println(bs1);
		CommonUtil.println(bs2);

	}
}
