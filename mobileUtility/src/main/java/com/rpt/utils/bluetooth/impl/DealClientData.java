package com.rpt.utils.bluetooth.impl;


import com.rpt.utils.IDataReceiveListener;

public class DealClientData implements IDataReceiveListener {

	@Override
	public void onRecieveData(Object source, byte[] data) {
		String str= new String(data);
		System.out.println("ClientData:"+str);
	}

}
