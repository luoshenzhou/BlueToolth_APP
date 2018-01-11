package com.rpt.utils.bluetooth;

import com.rpt.utils.IDataReceiveListener;


/**
 * 一个蓝牙设备只允许建立一个连接
 * @author Acer
 *
 */
public interface IBltCommServer {
	/**
	 * 接受蓝牙设备的连接
	 * @return 
	 */
	public boolean accept() ;
	/**
	 * 关闭设备
	 * @return
	 */
	public boolean close() ;
	
	/**
	 * 向连接的蓝牙设备发送数据
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
