package com.rpt.jx.protocol;

public class Cmd06RequestBody implements IBody {
	private byte moduleAddr;
	private byte cmdTag;
	
	public Cmd06RequestBody(){	
		this.cmdTag=0x06;
		this.moduleAddr=0x42;
	}
	
	
	public Cmd06RequestBody(byte moduleAddr){	
		this.cmdTag=0x06;
		this.moduleAddr=moduleAddr;
	}
	
	@Override
	public IProtocol fromByte(byte[] data) {
		if(data==null || data.length!=2) return null;
		moduleAddr=data[0];
		cmdTag=data[1];
		return this;
	}

	@Override
	public byte[] toByte() {
		byte[] bs=new byte[]{moduleAddr,cmdTag};
		return bs;
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
	
//	public static void main(String[] args){
//		Cmd06RequestBody cmd=new Cmd06RequestBody();
//		byte[] bs= cmd.toByte();
//		PrintUtil.println(bs);
//		boolean result=cmd.fromByte(bs);
//		System.out.println(result);
//	}

}
