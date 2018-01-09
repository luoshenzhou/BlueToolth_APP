package com.rpt.utils.bluetooth;

import java.util.Set;

import com.rpt.utils.IDataReceiveListener;

import android.bluetooth.BluetoothDevice;

/**
 * 蓝牙通信客户端接口
 * @author Acer
 *
 */
public interface IBltCommClient {
	/**
	 * 搜索已经配对的蓝牙设备
	 * @return
	 * @throws Exception
	 */
	public Set<BluetoothDevice> searchDevices() throws Exception;
	
	/**
	 * 根据名称获取已经配对的蓝牙设备
	 * @param deviceName
	 * @return
	 */
	public BluetoothDevice getBltDeviceByName(String deviceName);
	
	/**
	 * 根据蓝牙设备分类获取已经配对的蓝牙设备
	 * @param deviceClass
	 * @return
	 */
	public BluetoothDevice getBltDeviceByClass(String deviceClass);
	
	/**
	 * 打开设备，建立连接
	 * @param device TODO
	 * @return
	 * @throws Exception
	 */
	public boolean open(BluetoothDevice device) throws Exception;
	
	/**
	 * 判断是否连接
	 * @return
	 */
	public boolean isConnected();
	
	/**
	 * 关闭设备
	 * @return
	 */
	public boolean close();
	
	/**
	 * 向蓝牙设备发送数据
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
