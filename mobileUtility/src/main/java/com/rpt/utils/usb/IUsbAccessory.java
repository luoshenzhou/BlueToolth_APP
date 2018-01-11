package com.rpt.utils.usb;
import android.hardware.usb.UsbAccessory;

import com.rpt.utils.IDataReceiveListener;

public interface IUsbAccessory {

	

	/**
	 * 打开设备，建立连接
	 * @param device TODO
	 * @return
	 * @throws Exception
	 */
	public boolean open(UsbAccessory device);
	
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
	 * 向Usb设备发送数据
	 * @param data：数据
	 * @return
	 */
	public boolean write(byte[] data);
	/**
	 * 注册一个监听者，监听收到的数据
	 * @param listener
	 */
	public void registerDataRecieveListener(IDataReceiveListener listener);
	
	/**
	 * 注销一个监听者
	 * @param listener
	 */
	public void unregisterDataRecieveListener(IDataReceiveListener listener);

}