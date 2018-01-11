package com.rpt.jx.protocol;

public class CmdResponseBody implements IBody{
	private byte answerCode;
	
	public CmdResponseBody(){	
		this.answerCode=0x00;
	}
		
	@Override
	public IProtocol fromByte(byte[] data) {
		if(data==null || data.length!=1) return null;
		answerCode=data[0];
		return this;
	}

	@Override
	public byte[] toByte() {
		byte[] bs=new byte[]{answerCode};
		return bs;
	}

	public byte getCmdTag() {
		return answerCode;
	}

	public void setCmdTag(byte cmdTag) {
		this.answerCode = cmdTag;
	}
	
//	public static void main(String[] args){
//		CmdResponseBody cmd=new CmdResponseBody();
//		byte[] bs= cmd.toByte();
//		CommonUtil.println(bs);
//		IProtocol result=cmd.fromByte(bs);
//		System.out.println(result);
//	}
}
