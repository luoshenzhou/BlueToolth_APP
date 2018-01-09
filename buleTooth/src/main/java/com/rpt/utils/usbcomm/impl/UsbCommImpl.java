package com.rpt.utils.usbcomm.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.D2xxManager.D2xxException;
import com.ftdi.j2xx.FT_Device;
import com.rpt.utils.IDataReceiveListener;
import com.rpt.utils.usbcomm.IUsbComm;

public class UsbCommImpl implements IUsbComm {

	// USB设备管理器
	private D2xxManager usbManage;

	// USB串口设备
	private FT_Device usbDev;

	private Context context;
	
	private List<IDataReceiveListener> listeners=new ArrayList<IDataReceiveListener>();

	private boolean readThreadEnabled=false;
	
	private ReadThread readThread=null;
	public UsbCommImpl() {

	}

	public UsbCommImpl(Context context) {
		this.context = context;
	}

	@Override
	public boolean open() {
		
		//如果设备管理器未打开，则打开设备管理器
		if(usbManage==null){
			try {
				usbManage=D2xxManager.getInstance(context);
			} catch (D2xxException e) {
				usbManage=null;
				return false;
			}
		}

		// 设备已打开
		if (usbDev != null && usbDev.isOpen()) {
			return true;
		}

		// 打开设备
		int devCount = usbManage.createDeviceInfoList(context);
		if (devCount > 0) {
			usbDev = usbManage.openByIndex(context, 0);
		}else{
			return false;
		}
				
		//设置默认参数
		setConfig(9600,(byte)8,(byte)1,(byte)0,(byte)0);

		// 打开读线程 
		readThreadEnabled=true;
		readThread=new ReadThread();
		readThread.start();
		
		return true;
	}

	@Override
	public boolean close() {
		readThreadEnabled=false;
		try {
			if (usbDev != null) {
				if (true == usbDev.isOpen()) {
					usbDev.close();
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	@Override
	public boolean isConnected() {
		if(usbDev==null || usbDev.isOpen()==false){
			return false;
		}
		return true;
	}

	@Override
	public boolean write(byte[] data) {
		if(data==null || data.length==0){
			return false;
		}else{
			if(isConnected()){
				int count=usbDev.write(data);
				if(count==data.length){
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public void registerDataRecieveListener(IDataReceiveListener listener) {
		if(listener!=null){
			this.listeners.add(listener);
		}
	}

	@Override
	public void unregisterDataRecieveListener(IDataReceiveListener listener) {
		this.listeners.remove(listener);
	}
	
	//分发数据
	public void dispatch(byte[] usbData) {
		Log.i("USB LOG", "Recieve Data:"+fromat(usbData));
		for(IDataReceiveListener listener : listeners){
			listener.onRecieveData(this, usbData);
		}
	}

	//默认配置 baud=9600,dataBits=8,stopBits=1,parity=0,flowControl=0
	public void setConfig(int baud, byte dataBits, byte stopBits, byte parity, byte flowControl)
	{
		usbDev.setBitMode((byte) 0, D2xxManager.FT_BITMODE_RESET);

		usbDev.setBaudRate(baud);

		switch (dataBits)
		{
		case 7:
			dataBits = D2xxManager.FT_DATA_BITS_7;
			break;
		case 8:
			dataBits = D2xxManager.FT_DATA_BITS_8;
			break;
		default:
			dataBits = D2xxManager.FT_DATA_BITS_8;
			break;
		}

		switch (stopBits)
		{
		case 1:
			stopBits = D2xxManager.FT_STOP_BITS_1;
			break;
		case 2:
			stopBits = D2xxManager.FT_STOP_BITS_2;
			break;
		default:
			stopBits = D2xxManager.FT_STOP_BITS_1;
			break;
		}

		switch (parity)
		{
		case 0:
			parity = D2xxManager.FT_PARITY_NONE;
			break;
		case 1:
			parity = D2xxManager.FT_PARITY_ODD;
			break;
		case 2:
			parity = D2xxManager.FT_PARITY_EVEN;
			break;
		case 3:
			parity = D2xxManager.FT_PARITY_MARK;
			break;
		case 4:
			parity = D2xxManager.FT_PARITY_SPACE;
			break;
		default:
			parity = D2xxManager.FT_PARITY_NONE;
			break;
		}

		usbDev.setDataCharacteristics(dataBits, stopBits, parity);

		short flowCtrlSetting;
		switch (flowControl)
		{
		case 0:
			flowCtrlSetting = D2xxManager.FT_FLOW_NONE;
			break;
		case 1:
			flowCtrlSetting = D2xxManager.FT_FLOW_RTS_CTS;
			break;
		case 2:
			flowCtrlSetting = D2xxManager.FT_FLOW_DTR_DSR;
			break;
		case 3:
			flowCtrlSetting = D2xxManager.FT_FLOW_XON_XOFF;
			break;
		default:
			flowCtrlSetting = D2xxManager.FT_FLOW_NONE;
			break;
		}
		
	    byte XON = 0x11;    /* Resume transmission */
	    byte XOFF = 0x13;    /* Pause transmission */
		usbDev.setFlowControl(flowCtrlSetting, XON, XOFF);
	}

	private String hexToAscii(String s) {
		int n = s.length();
		StringBuilder sb = new StringBuilder(n / 2);
		for (int i = 0; i < n; i += 2) {
			char a = s.charAt(i);
			char b = s.charAt(i + 1);
			sb.append((char) ((hexToInt(a) << 4) | hexToInt(b)));
		}
		return sb.toString();
	}

	private String fromat(byte[] data){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<data.length;i++){
			sb.append(String.format("%02x ", data[i]));
		}
		return sb.toString();
	}
	
	private int hexToInt(char ch) {
		if ('a' <= ch && ch <= 'f') {
			return ch - 'a' + 10;
		}
		if ('A' <= ch && ch <= 'F') {
			return ch - 'A' + 10;
		}
		if ('0' <= ch && ch <= '9') {
			return ch - '0';
		}
		throw new IllegalArgumentException(String.valueOf(ch));
	}
	
	
	class ReadThread extends Thread{
		@Override
		public void run(){
			while (true == readThreadEnabled) 
			{
				try 
				{
					Thread.sleep(30);
				}
				catch (Exception e) {
					
				}			
				int readCount = usbDev.getQueueStatus();
				if (readCount > 0){
					byte[] usbData=new byte[readCount];
					usbDev.read(usbData, readCount);			
					dispatch(usbData);
				}
			}
		}
	}



	
}
