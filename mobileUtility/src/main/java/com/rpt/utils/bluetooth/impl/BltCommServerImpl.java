package com.rpt.utils.bluetooth.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.rpt.utils.IDataReceiveListener;
import com.rpt.utils.bluetooth.BltConstant;
import com.rpt.utils.bluetooth.IBltCommServer;

public class BltCommServerImpl extends Thread implements IBltCommServer {
	private List<IDataReceiveListener> listeners=new ArrayList<IDataReceiveListener>();
	private final BluetoothServerSocket bltServerSocket;
	private BluetoothSocket bltSocket;
	private BltSocketReadThread bltSocketReadThread;
	
	public BltCommServerImpl(){
		BluetoothServerSocket tmp = null; // 使用一个临时对象代替
		try {
			BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
			tmp = adapter.listenUsingRfcommWithServiceRecord(BltConstant.BLT_NAME, BltConstant.BLT_UUID); // 服务仅监听
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		bltServerSocket = tmp;
	}
	
	@Override
	public void run() {
		BluetoothSocket socket = null;
		while (true) { // 保持连接直到异常发生或套接字返回
			try {
				socket = bltServerSocket.accept(); // 如果一个连接同意
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			if (socket != null) {
				// 管理一个已经连接的RFCOMM通道在单独的线程。
				BltSocketReadThread readThread=new BltSocketReadThread(socket);
				readThread.start();
				bltSocketReadThread=readThread;
				try {
					bltServerSocket.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
		}
	}


	@Override
	public boolean write(byte[] data) {
		if(this.bltSocket!=null){
			try{
				this.bltSocket.getOutputStream().write(data);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return false;
			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	synchronized public void registerDataRecieveListener(IDataReceiveListener listener) {
		this.listeners.add(listener);
	}

	@Override
	synchronized public void unregisterDataRecieveListener(IDataReceiveListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public boolean accept(){
		try{
			super.start();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean close() {
		try{
			super.stop();
		}catch(Exception e){
			return false;
		}
		return true;
	}

	
	//socket管理类
	class BltSocketReadThread extends Thread{
		private BluetoothSocket bltSocket;
		private boolean stopTag=false;
		
		public BltSocketReadThread(BluetoothSocket socket){
			this.stopTag=false;
			this.bltSocket=socket;
		}
		
		public boolean isConnected() {
			return bltSocket!=null && bltSocket.isConnected();
		}

		public boolean close(){
			this.stopTag=true;
			try {
				this.bltSocket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return false;
			} finally {
				this.bltSocket=null;
			}
			return true;
		}

		@Override
		public void run() {
			while(!stopTag){
				try{
					if(bltSocket!=null){
						byte[] buffer=new byte[1024];
						InputStream is=bltSocket.getInputStream();
						int len=is.read(buffer);
						byte[] bs=Arrays.copyOf(buffer, len);
						dispacthData(bs);
					}else{
						Thread.sleep(1000);
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}

		private void dispacthData(byte[] data) {
			synchronized(BltCommServerImpl.this){
				for(IDataReceiveListener listener:listeners){
					listener.onRecieveData(bltSocket, data);
				}
			}	
		}
	}
}
