package com.rpt.jx.protocol;

public interface IProtocol {
	static final public byte CMD_21=0x21;
	static final public byte CMD_23=0x23;
	static final public byte CMD_06=0x06;
	static final public byte CMD_00=0x00;
	

	abstract public IProtocol fromByte(byte[] data);
	abstract public byte[] toByte();
}
