package com.jx.mobileutility;

import java.util.ArrayList;
import java.util.List;


public class MockData {
	
	static public List<ParamDefine> getData(){
		List<ParamDefine> params=new ArrayList<ParamDefine>();
		ParamDefine param1=new ParamDefine();
		param1.setParamName("下行功放开关:");
		param1.setModule("30");
		param1.setMemAddr("0400");
		param1.setLength(2);
		param1.setComponentType("SWITCH");
		param1.setDefaultValue("1=开&0=关");
		param1.setValueType("int");
		
		ParamDefine param2=new ParamDefine();
		param2.setParamName("上行功放开关:");
		param2.setModule("30");
		param2.setMemAddr("0420");
		param2.setLength(2);
		param2.setComponentType("SWITCH");
		param2.setDefaultValue("1=开&0=关");
		param2.setValueType("int");
		
		ParamDefine param3=new ParamDefine();
		param3.setParamName("下行中心频率(MHz):");
		param3.setModule("30");
		param3.setMemAddr("0430");
		param3.setLength(4);
		param3.setComponentType("TEXT");
		param3.setDefaultValue("");
		param3.setValueType("float");
		
		ParamDefine param4=new ParamDefine();
		param4.setParamName("上行中心频率(MHz):");
		param4.setModule("30");
		param4.setMemAddr("0440");
		param4.setLength(4);
		param4.setComponentType("TEXT");
		param4.setDefaultValue("");
		param4.setValueType("float");
		
		ParamDefine param5=new ParamDefine();
		param5.setParamName("带宽(MHz):");
		param5.setModule("30");
		param5.setMemAddr("0450");
		param5.setLength(4);
		param5.setComponentType("TEXT");
		param5.setDefaultValue("");
		param5.setValueType("float");
		
		ParamDefine param6=new ParamDefine();
		param6.setParamName("下行衰减:");
		param6.setModule("30");
		param6.setMemAddr("0460");
		param6.setLength(2);
		param6.setComponentType("TEXT");
		param6.setDefaultValue("");
		param6.setValueType("int");
		
		ParamDefine param7=new ParamDefine();
		param7.setParamName("上行衰减:");
		param7.setModule("30");
		param7.setMemAddr("0470");
		param7.setLength(2);
		param7.setComponentType("TEXT");
		param7.setDefaultValue("0=正常&1=告警");
		param7.setValueType("int");
		
		ParamDefine param8=new ParamDefine();
		param8.setParamName("AGC  告  警:");
		param8.setModule("30");
		param8.setMemAddr("0480");
		param8.setLength(2);
		param8.setComponentType("ALARM");
		param8.setDefaultValue("0=正常&1=告警");
		param8.setValueType("int");
		
		ParamDefine param9=new ParamDefine();
		param9.setParamName("隔离度告警:");
		param9.setModule("30");
		param9.setMemAddr("0490");
		param9.setLength(2);
		param9.setComponentType("ALARM");
		param9.setDefaultValue("0=正常&1=告警");
		param9.setValueType("int");
		
		params.add(param1);
		params.add(param2);
		params.add(param3);
		params.add(param4);
		params.add(param5);
		params.add(param6);
		params.add(param7);
		params.add(param8);
		params.add(param9);
		
		return params;
	}
}
