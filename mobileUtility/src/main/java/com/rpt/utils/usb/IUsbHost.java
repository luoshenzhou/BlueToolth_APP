package com.rpt.utils.usb;

import com.rpt.utils.IDataReceiveListener;

import android.content.Context;
import android.hardware.usb.UsbDevice;

public interface IUsbHost {
	public static final String ACTION_USB_PERMISSION = "com.jx.mobileutility.USB_PERMISSION";
	/**
	 * 根据vid和pid查询连接的USB设备
	 * @param vid 供应商标识Id
	 * @param pid 产品标识Id
	 * @return 
	 */
	public UsbDevice findDevice(int vid,int pid);
	
	public boolean getPermission(Context context,UsbDevice device);
	
	/**
	 * 打开设备，建立连接
	 * @param device TODO
	 * @return
	 * @throws Exception
	 */
	public boolean open(UsbDevice device);
	
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
