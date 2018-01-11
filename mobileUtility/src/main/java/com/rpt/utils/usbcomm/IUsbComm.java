package com.rpt.utils.usbcomm;

import com.rpt.utils.IDataReceiveListener;

public interface IUsbComm {
	
	/**
	 * 打开设备，建立连连接
	 * @param device TODO
	 * @return
	 * @throws Exception
	 */
	public boolean open();
	
	/**
	 * 关闭设备
	 * @return
	 */
	public boolean close();
	
	/**
	 * 判断是否连接
	 * @return
	 */
	public boolean isConnected();
	
	/**
	 * 向Usb设备发�?数据
	 * @param data：数�?	 * @return
	 */
	public boolean write(byte[] data);
	/**
	 * 注册数据监听者，监听收到的数数据
	 * @param listener
	 */
	public void registerDataRecieveListener(IDataReceiveListener listener);
	
	/**
	 * 注销数据监听�?	 * @param listener
	 */
	public void unregisterDataRecieveListener(IDataReceiveListener listener);

}
