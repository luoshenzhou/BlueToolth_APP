package com.rpt.utils.bluetooth.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.rpt.utils.IDataReceiveListener;
import com.rpt.utils.bluetooth.BltConstant;
import com.rpt.utils.bluetooth.IBltCommClient;

public class BltCommClientImpl implements IBltCommClient {
    private List<IDataReceiveListener> listeners = new ArrayList<IDataReceiveListener>();
    private BluetoothSocket bltSocket = null;   //蓝牙套接字
    private BltSocketReadThread socketReadThread = null;  //套接字读线程

    @Override
    public Set<BluetoothDevice> searchDevices() throws Exception {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.getBondedDevices();
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        return devices;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean open(BluetoothDevice device) throws Exception {
        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(BltConstant.BLT_UUID);
//		StringBuffer sb=new StringBuffer();
//		sb.append("连通状态："+socket.isConnected());
//
//		ParcelUuid[] uuids = socket.getRemoteDevice().getUuids();
//		for(ParcelUuid uuid :uuids){
//		 sb.append("uuid="+uuid.getUuid().toString()+"\r\n");
//		}
//		System.out.println(sb.toString());

        if (!socket.isConnected()) {
            socket.connect();
        }

        if (socket != null) {
            manager(socket);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isConnected() {
        return this.bltSocket != null && this.bltSocket.isConnected();
    }

    @Override
    public boolean close() {
        try {
            socketReadThread.setStopTag(true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private final static byte[] hex = "0123456789ABCDEF".getBytes();

    // 从字节数组到十六进制字符串转换
    public static String Bytes2HexString(byte[] b) {
        byte[] buff = new byte[2 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[b[i] & 0x0f];
        }
        return new String(buff);
    }

    @Override
    public boolean write(byte[] data) {
        if (bltSocket != null) {
            try {
                OutputStream os = bltSocket.getOutputStream();
                os.write(data);
                Log.e("==发送数据===", Bytes2HexString(data));
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
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
    public BluetoothDevice getBltDeviceByName(String deviceName) {
        try {
            Set<BluetoothDevice> devices = searchDevices();
            Iterator<BluetoothDevice> itr = devices.iterator();
            while (itr.hasNext()) {
                BluetoothDevice device = itr.next();
                if (device.getName().equals(deviceName)) {
                    return device;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public BluetoothDevice getBltDeviceByClass(String deviceClass) {
        try {
            Set<BluetoothDevice> devices = searchDevices();
            Iterator<BluetoothDevice> itr = devices.iterator();
            while (itr.hasNext()) {
                BluetoothDevice device = itr.next();
                if (device.getBluetoothClass().toString().equals(deviceClass)) {
                    return device;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }


    //管理socket
    private void manager(BluetoothSocket socket) {
        this.bltSocket = socket;
        // 1.创建线程对象，对socket进行管理，主要用于监听收到的数据，通知监听器，进行数据处理
        this.socketReadThread = new BltSocketReadThread();
        // 2.启动线程
        this.socketReadThread.start();
    }

    //分发数据
    synchronized private void dispacthData(byte[] data) {
        for (IDataReceiveListener listener : listeners) {
            listener.onRecieveData(bltSocket, data);
        }
    }

    //socket管理类
    class BltSocketReadThread extends Thread {
        private boolean stopTag = false;

        public boolean isStopTag() {
            return stopTag;
        }

        public void setStopTag(boolean stopTag) {
            this.stopTag = stopTag;
            try {
                bltSocket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                bltSocket = null;
            }
        }

        @Override
        public void run() {
            stopTag = false;
            while (!stopTag) {
                try {
                    if (bltSocket != null) {
                        byte[] buffer = new byte[1024];
                        InputStream is = bltSocket.getInputStream();
                        int len = is.read(buffer);
                        byte[] bs = Arrays.copyOf(buffer, len);
                        dispacthData(bs);
                    } else {
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
