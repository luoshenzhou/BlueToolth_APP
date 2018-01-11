package com.rpt.utils.usb.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import com.rpt.utils.IDataReceiveListener;
import com.rpt.utils.usb.IUsbAccessory;

public class UsbAccessoryImpl implements IUsbAccessory{
	private UsbManager usbManager=null;
	private UsbInterface usbInterface=null;
	private UsbEndpoint intEndPoint=null;
	private UsbEndpoint outEndPoint=null;
	private UsbDeviceConnection connection=null;
	private List<IDataReceiveListener> listeners=new ArrayList<IDataReceiveListener>();
	private DataReadThread readThread=null;
	
	public UsbAccessoryImpl(UsbManager usbManager){
		this.usbManager=usbManager;
	}
	

	private void reinit(){
		this.usbInterface=null;
		this.intEndPoint=null;
		this.outEndPoint=null;
		this.connection=null;
	}
	
	@Override
	public boolean open(UsbAccessory device) {
	//		try{
	//		if(this.usbManager.hasPermission(device)){
	//			if(device.getInterfaceCount()>0){
	//				this.usbInterface=device.getInterface(0);
	//			}else{
	//				System.out.println("找不到接口！");
	//				reinit();
	//				return false;
	//			}
	//			this.connection=this.usbManager.openDevice(device);
	//			this.usbManager.openAccessory(accessory);
	//			if(connection.claimInterface(usbInterface,true)){
	//				this.intEndPoint=usbInterface.getEndpoint(0);
	//				this.outEndPoint=usbInterface.getEndpoint(1);	
	//				this.readThread=new DataReadThread();
	//				this.readThread.start();
	//				return true;
	//			}else{
	//				System.out.println("没有访问usb的权限！");
	//			}
	//		}else{
	//			System.out.println("没有访问usb的权限！");
	//		}
	//
	//	}catch(Exception e){
	//		System.out.println(e.getMessage());
	//	}
	//	reinit();
		return false;
	}

	@Override
	public boolean close() {
		try{
			if(this.readThread!=null){
				this.readThread.setStopTag(true);
				this.readThread.join(3000);
				this.readThread=null;
			}
		}catch(Exception e){
			System.out.println("Stopping thread raised execption:"+e.getMessage());
		}
	    try{
	        if(this.connection!=null){
	        	this.connection.close();
	        	return true;
	        }
	    }finally{
	    	reinit();
	    }
		return false;
	}

	@Override
	public boolean isConnected() {
		return (this.connection==null);
	}

	@Override
	public boolean write(byte[] data) {
		int res1=this.connection.bulkTransfer(outEndPoint, data, data.length, 3000);
		return (res1==data.length);
	}

	@Override
	synchronized public void registerDataRecieveListener(IDataReceiveListener listener) {
		this.listeners.add(listener);
	}

	@Override
	synchronized public void unregisterDataRecieveListener(IDataReceiveListener listener) {
		this.listeners.remove(listener);
	}

	synchronized private void dispatchData(byte[] data){
		for(IDataReceiveListener listener : listeners){
			listener.onRecieveData(this,data);
		}
	}
	
	class DataReadThread extends Thread{
		
		private boolean stopTag=false;
		public DataReadThread(){
			this.stopTag=false;
		}
		
		public boolean isStopTag() {
			return stopTag;
		}

		public void setStopTag(boolean stopTag) {
			this.stopTag = stopTag;
		}

		@Override
		public void run() {
			stopTag=false;
			while(!stopTag){
				try{
					if(connection!=null){
						byte[] revBuffer=new byte[1024];
						int res=connection.bulkTransfer(intEndPoint, revBuffer, 1024, 3000);
						if(res>0){
							dispatchData(Arrays.copyOf(revBuffer, res));
						}
					}else{
						Thread.sleep(1000);
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}		
	}
}
