package com.rpt.jx.protocol;

import java.util.Arrays;
import java.util.List;

public class Protocol implements IProtocol{
	private byte startTag;
	private IBody body;
	private byte[] endTag;
	
	public Protocol(){
		this.endTag=new byte[]{0x0D,0x0A};
	}
	
	public static Protocol get21Request(byte module,List<MemData> items){
		Cmd21RequestBody body=new Cmd21RequestBody(module);
		body.setData(items);
		Protocol result=new Protocol();
		result.setStartTag((byte)':');
		result.setBody(body);
		return result;
	}
	
	public static Protocol get21Response(List<MemData> items){
		Cmd21ResponseBody body=new Cmd21ResponseBody();
		body.setData(items);
		Protocol result=new Protocol();
		result.setStartTag((byte)'>');
		result.setBody(body);
		return result;
	}
	
	public static Protocol get23Request(byte module,List<MemData> items){
		Cmd23RequestBody body=new Cmd23RequestBody(module);
		body.setData(items);
		Protocol result=new Protocol();
		result.setStartTag((byte)':');
		result.setBody(body);
		return result;
	}
	
	public static Protocol getCmdResponse(){
		CmdResponseBody body=new CmdResponseBody();
		Protocol result=new Protocol();
		result.setStartTag((byte)'>');
		result.setBody(body);
		return result;
	}	
	
	public static Protocol get06Request(byte module){
		Cmd06RequestBody body=new Cmd06RequestBody(module);
		Protocol result=new Protocol();
		result.setStartTag((byte)':');
		result.setBody(body);
		return result;
	}	
	
	public byte getStartTag() {
		return startTag;
	}
	
	public void setStartTag(byte startTag) {
		this.startTag = startTag;
	}
	
	public IBody getBody() {
		return body;
	}
	public void setBody(IBody body) {
		this.body = body;
	}
	public byte[] getEndTag() {
		return endTag;
	}
	
	@Override
	public IProtocol fromByte(byte[] data){
		if(data==null || data.length<3) return null;
		this.startTag=data[0];
		this.endTag[0]=data[data.length-2];
		this.endTag[1]=data[data.length-1];
		byte[] bytes= Arrays.copyOfRange(data,1, data.length-2);
		bytes=CommonUtil.decodeBytes(bytes);
		bytes =CommonUtil.concat(new byte[]{this.startTag},bytes);
		bytes =CommonUtil.concat(bytes,this.endTag);
		data=bytes;
		//头尾标志判断
		if(!(this.startTag==':' || this.startTag=='>')  ||
				!(this.endTag[0]==0x0D || this.endTag[1]==0x0A)){
			return null;
		}
		
		//判断具体的指令
		
		if(startTag==':'){//请求指令
			byte cmd=data[2];
			switch(cmd){
				case IProtocol.CMD_21:{
					this.body=new Cmd21RequestBody();
					break;
				}
				case IProtocol.CMD_23:{
					this.body=new Cmd23RequestBody();
					break;
				}
				case IProtocol.CMD_06:{
					this.body=new Cmd06RequestBody();
					break;
				}
				default: return null;
			}
		}else{  //回应指令
			byte cmd=data[1];
			if(cmd==0){
				if(data.length==4 ){
					this.body=new CmdResponseBody();
				}else{
					this.body=new Cmd21ResponseBody();
				}
			}else{
				return null;
			}
			
		}
		byte[] bs=Arrays.copyOfRange(data, 1, data.length-2);
		body.fromByte(bs);
		return this;
	}
	
	@Override
	public byte[] toByte(){
		byte[] bs0=body.toByte();
		byte[] bs=CommonUtil.encodeBytes(bs0);
		byte[] bs1=new byte[]{this.startTag};
		byte[] bs2=this.endTag;
		byte[] result=CommonUtil.concat(bs1, bs);
		result=CommonUtil.concat(result, bs2);
		return result;
	}
	
}
