package com.jx.mobileutility;

import java.util.HashMap;
import java.util.Map;

public class ParamDefine {
	private String paramName;       //参量名称
	private String module;          //模组
	private short memAddr;         //内存地址 
	private int length;             //数据长度 
	private String componentType;   //组件类型
	private String defaultValue;    //缺省值
	private Map<Integer,String> kvMap;  //存放键值对，key实际值，value显示值
	private String value;           //存放当前值
	private String valueType;       //数值类型：int,float,string
	private boolean canWrite;       //可写标志
	private int scale;              //缩放比例
	
	public ParamDefine(){
		kvMap=new HashMap<Integer,String>();
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public short getMemAddr() {
		return memAddr;
	}
	
	public void setMemAddr(short memAddr) {
		this.memAddr = memAddr;
	}
	
	public void setMemAddr(String smemAddr){
		this.memAddr = Short.parseShort(smemAddr,16);;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		String[] keyValues=defaultValue.split("&");
		for(String keyValue :keyValues){
			try{
				String[] ss=keyValue.split("=");
				Integer key=Integer.parseInt(ss[0]);
				String value=ss[1];
				kvMap.put(key, value);
			}catch(Exception e){
				
			}
		}
	}

	public String get(int value){
		return kvMap.get(value);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void setValue(Integer value) {
		this.value = value.toString();
	}
	
	public void setValue(Float value) {
		this.value = value.toString();
	}
	
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public Map<Integer, String> getKvMap() {
		return kvMap;
	}

	public void setKvMap(Map<Integer, String> kvMap) {
		this.kvMap = kvMap;
	}

	public boolean isCanWrite() {
		return canWrite;
	}

	public void setCanWrite(boolean canWrite) {
		this.canWrite = canWrite;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String toString() {
		return "ParamDefine [paramName=" + paramName + ", module=" + module
				+ ", memAddr=" + memAddr + ", length=" + length
				+ ", componentType=" + componentType + ", defaultValue="
				+ defaultValue + ", kvMap=" + kvMap + ", value=" + value
				+ ", valueType=" + valueType + ", canWrite=" + canWrite
				+ ", scale=" + scale + "]";
	}


	
	
}
