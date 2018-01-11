package com.haozhi.machinestatu.fengjisystem.com.apujiang.zhibozan.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.haozhi.machinestatu.fengjisystem.R;
import com.jx.mobileutility.ParamAdapter;
import com.rpt.jx.protocol.Cmd21ResponseBody;
import com.rpt.jx.protocol.CmdResponseBody;
import com.rpt.jx.protocol.CommonUtil;
import com.rpt.jx.protocol.Protocol;
import com.rpt.utils.IDataReceiveListener;
import com.rpt.utils.bluetooth.impl.BltCommClientImpl;
import com.rpt.utils.usbcomm.impl.UsbCommImpl;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText et_account, et_pwd;
    TextView et_series, et_model, et_type, et_rate;
    Button btn_connect, btn_send;
    //蓝牙客户端
    private BltCommClientImpl bltClient = new BltCommClientImpl();

    private UsbCommImpl usbComm = null;

    private static final String ACTION_USB_PERMISSION = "com.jx.mobileutility.USB_PERMISSION";

    private byte[] tempData = null;

    ParamAdapter adapter = null;

    private int sendTag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //设置数据接收处理监听器
        bltClient.registerDataRecieveListener(datarecieveListener);
        usbComm = new UsbCommImpl(this);
        usbComm.registerDataRecieveListener(datarecieveListener);

        initView();
        bindEvent();
    }

    private void initView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_series = (TextView) findViewById(R.id.et_series);
        et_model = (TextView) findViewById(R.id.et_model);
        et_type = (TextView) findViewById(R.id.et_type);
        et_rate = (TextView) findViewById(R.id.et_rate);
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_send = (Button) findViewById(R.id.btn_send);
    }


    private void bindEvent() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *7e 01 01 01 00 00 00 ff 09 00 80 01 02 ff 05 09 00 01 01 cf 30 7e
                 */
                 bltClient.write(HexString2Bytes("7e010101000000ff0900800102ff0509000101cf307e"));
                //usbComm.write(HexString2Bytes("7e010101000000ff0900800102ff0509000101cf307e"));
            }
        });

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openBltDevice();
                //打开USB设备
//                boolean result = openUsbDevice();
//                if (result) {
//                    btn_connect.setText(R.string.close);
//                    Toast.makeText(LoginActivity.this, R.string.device_open_success, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(LoginActivity.this, R.string.device_open_fail, Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    //打开蓝牙设备
    private void openBltDevice() {
        String[] options = null;
        List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
        try {
            devices.addAll(bltClient.searchDevices());
            options = new String[devices.size()];
            for (int i = 0; i < options.length; i++) {
                options[i] = devices.get(i).getName();
            }
        } catch (Exception e) {

        }
        ButtonOnClick onClickListener = new ButtonOnClick(devices);

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle(R.string.select_bluetooth)
                .setIcon(android.R.drawable.ic_menu_info_details)
                .setSingleChoiceItems(options, 0, onClickListener)
                .setPositiveButton(R.string.ok, onClickListener)
                .show();
    }

    //蓝牙选择列表框
    private class ButtonOnClick implements DialogInterface.OnClickListener {
        private int index = 0;
        List<BluetoothDevice> devices;

        public ButtonOnClick(List<BluetoothDevice> devices) {
            this.devices = devices;

        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which >= 0) {
                index = which;
            } else {
                BluetoothDevice device = devices.get(index);
                try {
                    boolean result = bltClient.open(device);
                    if (result) {
                        btn_connect.setText(R.string.close);
                    }
                    Toast.makeText(LoginActivity.this, device.getName() + "，open successfully！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, device.getName() + "，open fail！" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    Handler handler = new Handler();
    //数据侦听者
    private IDataReceiveListener datarecieveListener = new IDataReceiveListener() {
        @Override
        public void onRecieveData(Object source, final byte[] data) {
            Log.e("====", "==onRecieveData===");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "==========", Toast.LENGTH_SHORT).show();
                }
            });
            if (data != null && data.length > 0) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, Bytes2HexString(data), Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("====", data.toString());

                if (data[0] == '>') {
                    tempData = data;
                } else {
                    tempData = CommonUtil.concat(tempData, data);
                }

                if (tempData != null && tempData.length > 3) {
                    int len = tempData.length;
                    if (tempData[len - 1] == 0x0A && tempData[len - 2] == 0x0D) {
                        Message message = new Message();
                        message.obj = tempData;

                        ////下面两行是模拟查询返回数据
                        //String str=">00006504A8480000005D045E4C000000790101002B01010021010100270103002201090028010400530101006D027800002301010024017F001F01000020010000290100002A0100\r\n";
                        //message.obj=str.getBytes();

                        dealBluetoothDataHandler.sendMessage(message);
                    }
                }
            } else {
                return;
            }
        }
    };
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

    //初始化化Handler,用于处理蓝牙设备发送过来的异步消息
    private Handler dealBluetoothDataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            byte[] data = (byte[]) msg.obj;

            Protocol cmdProtocol = new Protocol();
            cmdProtocol.fromByte(data);
            if (cmdProtocol.getBody() instanceof Cmd21ResponseBody) {
                Cmd21ResponseBody cmd21Response = (Cmd21ResponseBody) cmdProtocol.getBody();
                Toast.makeText(LoginActivity.this, R.string.read_ok, Toast.LENGTH_SHORT).show();
            } else if (cmdProtocol.getBody() instanceof CmdResponseBody) {

            }
            adapter.notifyDataSetChanged();

        }

    };

    //打开USB设备
    private boolean openUsbDevice() {
        return usbComm.open();
    }
}
