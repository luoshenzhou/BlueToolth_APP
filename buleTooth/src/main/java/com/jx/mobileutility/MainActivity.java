package com.jx.mobileutility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;
import com.rpt.jx.protocol.Cmd21ResponseBody;
import com.rpt.jx.protocol.CmdResponseBody;
import com.rpt.jx.protocol.CommonUtil;
import com.rpt.jx.protocol.MemData;
import com.rpt.jx.protocol.Protocol;
import com.rpt.utils.IDataReceiveListener;
import com.rpt.utils.bluetooth.impl.BltCommClientImpl;
import com.rpt.utils.usbcomm.impl.UsbCommImpl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	@ViewInject(R.id.btnSetting)
	private Button btnSetting;
	
	@ViewInject(R.id.btnRead)
	private Button btnRead;
	
	@ViewInject(R.id.btnSave)
	private Button btnSave;
	
	@ViewInject(R.id.grpCommType)
	private RadioGroup grpCommType;
	
	@ViewInject(R.id.listParams)
	private ListView listParams;
	
	@ViewInject(R.id.txtLog)
	private TextView txtLog;
	
	@ViewInject(R.id.btnOpen)
	private Button btnOpen;
	
	//存放系统中定义的设备
	private Map<String,String> devMap;
	private List<String> devList=new ArrayList<String>();
	
	//存放系统中所有参数
	private List<ParamDefine> params;
	
	//最后一次点击的按钮操作（设置，读取，保存）
	private int lastOperate;

	//蓝牙客户端
	private BltCommClientImpl bltClient=new BltCommClientImpl();
	
	private UsbCommImpl usbComm=null;
	
	private static final String ACTION_USB_PERMISSION ="com.jx.mobileutility.USB_PERMISSION";

	private byte[] tempData=null;
	
	ParamAdapter adapter=null;
	
	private int sendTag=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		
		//初始化设备类型
		initDeviceList();
		
		//初始化参数列表
		initParamList();
		
		//设置数据接收处理监听器
		bltClient.registerDataRecieveListener(datarecieveListener);
		usbComm=new UsbCommImpl(this);
		usbComm.registerDataRecieveListener(datarecieveListener);
		
	}


	//获取缺省设备
	private String getDefaultDevice(){
		SharedPreferences preferences=getSharedPreferences("MobileUtility", Context.MODE_PRIVATE);
		String defaultDevice=preferences.getString("DefaultDevice", null);
		return defaultDevice;
	}

    //设置缺省设备
	private void setDefaultDevice(String device){
		SharedPreferences preferences=getSharedPreferences("MobileUtility", Context.MODE_PRIVATE);
		Editor editor=preferences.edit();
		editor.putString("DefaultDevice", device);
		editor.commit();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		int count=0;
		for(String dev:devList){
			menu.add(Menu.NONE,Menu.FIRST+count,count,dev);	
			count++;
		}
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId()==R.id.menuExit){
			finish();
		}else{
			String defaultDevice=item.getTitle().toString();
			setDefaultDevice(defaultDevice);
			String title=MainActivity.this.getString(R.string.app_name)+" - "+defaultDevice;
			title=defaultDevice;
			MainActivity.this.setTitle(title);
			initParamList();
		}
		return super.onMenuItemSelected(featureId, item);
	}
 
	@OnRadioGroupCheckedChange(R.id.grpCommType)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(R.id.rdoUsb==checkedId){
			if(this.getString(R.string.close).equals(btnOpen.getText())){
				bltClient.close();
				btnOpen.setText(R.string.open);
			}
		}else{
			if(this.getString(R.string.close).equals(btnOpen.getText())){
				usbComm.close();
				btnOpen.setText(R.string.open);
			}
		}
	}
	
	//设置按钮
	@OnClick(R.id.btnSetting)
	public void onClickBtnSetting(View view){
		this.lastOperate=R.id.btnSetting;
		try{
			List<MemData> items= getMemData(true);
			byte module=Byte.parseByte(params.get(0).getModule(),16);
			Protocol cmd23Request=Protocol.get23Request(module, items);
			byte[] cmdData= cmd23Request.toByte();
			txtLog.setText((new String(cmdData)));
			if(grpCommType.getCheckedRadioButtonId()==R.id.rdoBluetooth){
				bltClient.write(cmdData);
			}else if(grpCommType.getCheckedRadioButtonId()==R.id.rdoUsb){
				usbComm.write(cmdData);
			}
		}catch(Exception e){
			Toast.makeText(this, R.string.param_isnull, 1000).show();
		}
	}
	
	//查询按钮
	@OnClick(R.id.btnRead)
	public void onClickBtnRead(View view){
		this.lastOperate=R.id.btnRead;
		List<MemData> items= getMemData(false);
		byte module=Byte.parseByte(params.get(0).getModule(),16);
		Protocol cmd21Request=Protocol.get21Request(module, items);
		byte[] cmdData= cmd21Request.toByte();
		
		txtLog.setText((new String(cmdData)).toUpperCase());
		if(grpCommType.getCheckedRadioButtonId()==R.id.rdoBluetooth){
			bltClient.write(cmdData);
		}else if(grpCommType.getCheckedRadioButtonId()==R.id.rdoUsb){
			//下面两行是模拟USB查询
			//String str=":FF01002801"+(char)10;
			//cmdData=str.getBytes();
			
			usbComm.write(cmdData);
		}
	}
	
	//保存按钮
	@OnClick(R.id.btnSave)
	public void onClickBtnSave(View view){	
		this.lastOperate=R.id.btnSave;
		byte module=Byte.parseByte(params.get(0).getModule(),16);
		Protocol cmd06Request=Protocol.get06Request(module);
		byte[] cmdData= cmd06Request.toByte();

		txtLog.setText((new String(cmdData)).toUpperCase());
		if(grpCommType.getCheckedRadioButtonId()==R.id.rdoBluetooth){
			bltClient.write(cmdData);
		}else if(grpCommType.getCheckedRadioButtonId()==R.id.rdoUsb){
			usbComm.write(cmdData);
		}
		
//		String str=">00006504A8480000005D045E4C000000790101002B01010021010100270103002201090028010400530101006D027800002301010024017F001F01000020010000290100002A0100\r\n";
//		byte[] data=str.getBytes();
//		byte[] bs1=Arrays.copyOfRange(data, 0, 10);
//		byte[] bs2=Arrays.copyOfRange(data, 10, 20);
//		byte[] bs3=Arrays.copyOfRange(data, 20, 30);
//		byte[] bs4=Arrays.copyOfRange(data, 30, data.length);
//		
//		sendTag=sendTag % 4;
//		switch(sendTag){
//		   case 0:{
//			   datarecieveListener.onRecieveData(null, bs1); 
//			   break;
//		   }
//		   case 1:{
//			   datarecieveListener.onRecieveData(null, bs2); 
//			   break;
//		   }
//		   case 2:{
//			   datarecieveListener.onRecieveData(null, bs3); 
//			   break;
//		   }
//		   case 3:{
//			   datarecieveListener.onRecieveData(null, bs4); 
//			   break;
//		   }
//		}
//		sendTag++;
	}	
	
	//完成通信方式的打开与关闭
	@OnClick(R.id.btnOpen)
	public void onClickBtnOpen(View view){
		boolean isOpen=false;
		int idSelect=grpCommType.getCheckedRadioButtonId();
		if(idSelect<0){
			Toast.makeText(this, R.string.select_tip, 1000).show();
			return;
		}		
		isOpen= this.getString(R.string.open).equals(btnOpen.getText());
		if(isOpen){
			if(idSelect==R.id.rdoBluetooth){
				//打开蓝牙设备
				 openBltDevice();				 
			}else {
				//打开USB设备
				boolean result=openUsbDevice();
				if(result){
					btnOpen.setText(R.string.close);
					Toast.makeText(this, R.string.device_open_success, 1000).show();
				}else{
					Toast.makeText(this, R.string.device_open_fail, 1000).show();
				}
			}

		}else{
			if(idSelect==R.id.rdoBluetooth){
				//关闭蓝牙设备
				boolean result=bltClient.close();
				if(result){
					btnOpen.setText(R.string.open);
				}
			}else{
				//关闭USB设备
				usbComm.close();
				Toast.makeText(this, R.string.close, 1000).show();
			}
			btnOpen.setText(R.string.open);
		}

	}
	
	//初始化设备列表
	private void initDeviceList() {
		devMap=parseDevice();
	}
	
	
	//初始化参数列表
	private void initParamList() {
		try{
		//1.获取数据 
		List<ParamDefine> params = parseParam();
		this.params=params;
		
		//2.创建列表适配器
		adapter=new ParamAdapter(params,this);
		
		//3.加载列表适配器
		this.listParams.setAdapter(adapter);
		//4.事件绑定
		adapter.notifyDataSetChanged();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private Map<String,String> parseDevice(){
		Map<String,String> resultMap=new HashMap<String,String>();
		String[] ss=CommonUtil.readFile(this, "devices.txt");
		devList.clear();
		for(String str:ss){
			if(str.trim().startsWith("#")) {
				continue;
			}
			try{
				String[] arr=str.trim().split("=");
				if(arr.length!=2) continue;
				resultMap.put(arr[0], arr[1]);
				devList.add(arr[0]);
			}catch(Exception e){
				Log.wtf("Parse devices data", e);
			}
		}
		return resultMap;
	}

	//从配置文件中国解析参数
	private List<ParamDefine> parseParam() {
		String defaultDevice=getDefaultDevice();
		String defaultFile=null;
		if(defaultDevice!=null){
			defaultFile=devMap.get(defaultDevice);
		}
		
		if(defaultFile==null){
			defaultDevice=devList.get(0);
			setDefaultDevice(defaultDevice);
			defaultFile=devMap.get(defaultDevice);			
		}
		
		String title=MainActivity.this.getString(R.string.app_name)+" - "+defaultDevice;
		title=defaultDevice;
		MainActivity.this.setTitle(title);
		
		List<ParamDefine> params= new ArrayList<ParamDefine>();
		String[] ss = CommonUtil.readFile(this,defaultFile);
		for(String str:ss){
			if(str.trim().startsWith("#")) {
				continue;
			}
			try{
				String[] arr=str.split(",");
				if(arr.length<8) continue;
				ParamDefine param=new ParamDefine();
				param.setModule(arr[0]);
				param.setParamName(arr[1]);
				param.setComponentType(arr[2].toUpperCase());
				param.setMemAddr(arr[3]);
				param.setValueType(arr[4].toLowerCase());
				param.setLength(Integer.parseInt(arr[5].trim()));
				param.setScale(Integer.parseInt(arr[6].trim()));
				param.setCanWrite("W".equals(arr[7].trim().toUpperCase()));
				
				if(arr.length>=9){
					param.setDefaultValue(arr[8]);
				}
				params.add(param);
			}catch(Exception e){
				Log.wtf("Parse define data", e);
			}
		}
		return params;
	}

	
	//获取参数列表
	private List<MemData> getMemData(boolean includeContent){
		List<MemData> result=new ArrayList<MemData>();
		for(ParamDefine param:params){
			MemData item=new MemData();
			item.setMemoryAddr(param.getMemAddr());
			item.setDataLength((byte)param.getLength());
			if(includeContent){
				if(param.isCanWrite()){
					if("int".equals(param.getValueType())){
						int value= Integer.parseInt(param.getValue()) * param.getScale();
						item.setData(CommonUtil.intToByte(value, param.getLength()));
					}else if("float".equals(param.getValueType())){
						float value=Float.parseFloat(param.getValue()) * param.getScale();
						int tempValue=(int)value;
						byte[] bs=CommonUtil.intToByte(tempValue, param.getLength());
						item.setData(bs);
					}else if("string".equals(param.getValueType())){
						byte[] bs=param.getValue().getBytes();
						item.setData(bs);
					}
					result.add(item);
				}
			}else{
				result.add(item);
			}
			
		}
		return result;
	}
	
	//把内存数据写回params
	private void setMemData(List<MemData> datas){
		for(MemData item : datas){
			short memAddr=item.getMemoryAddr();
			for(ParamDefine param:params){
				if(memAddr==param.getMemAddr()){
					if("int".equals(param.getValueType())){
						int value=CommonUtil.byteToInt(item.getData())/param.getScale();
						param.setValue(value);
					}else if("float".equals(param.getValueType())){
						int tempValue=CommonUtil.byteToInt(item.getData());
						Float value=(float)tempValue / param.getScale();
						param.setValue(value);
					}
					continue;
				}
			}
		}
	}
	
	//打开USB设备
	private boolean openUsbDevice(){
		return usbComm.open();
	}
	
	//打开蓝牙设备
	private void openBltDevice(){
		String[] options=null;
		List<BluetoothDevice> devices=new ArrayList<BluetoothDevice>();
		try{
			devices.addAll( bltClient.searchDevices());
			options=new String[devices.size()];
			for(int i=0;i<options.length;i++){
				options[i]=devices.get(i).getName();
			}
		}catch(Exception e){
			
		}
		ButtonOnClick onClickListener=new ButtonOnClick(devices);

		new AlertDialog.Builder(MainActivity.this)  
		.setTitle(R.string.select_bluetooth)  
		.setIcon(android.R.drawable.ic_menu_info_details)    
		.setSingleChoiceItems(options, 0, onClickListener)  
		.setPositiveButton(R.string.ok,onClickListener)
		.show();  
	}

	//蓝牙选择列表框
	private class ButtonOnClick implements DialogInterface.OnClickListener{
		private int index=0;
		List<BluetoothDevice> devices;
		public ButtonOnClick(List<BluetoothDevice> devices){
			this.devices=devices;
			
		}
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(which>=0){
				index=which;
			}else{
				BluetoothDevice device=devices.get(index);
				try{
					boolean result = bltClient.open(device);
					if(result){
						btnOpen.setText(R.string.close);
					}
					Toast.makeText(MainActivity.this, device.getName()+"，open successfully！", 1000).show();
				}catch(Exception e){
					Toast.makeText(MainActivity.this, device.getName()+"，open fail！"+e.getMessage(), 1000).show();
				}
				
			}
			
		}
	}
	
	//初始化化Handler,用于处理蓝牙设备发送过来的异步消息
	private Handler dealBluetoothDataHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {		
			byte[] data=(byte[])msg.obj;
			txtLog.setText("Recieve:"+(new String(data)).toUpperCase());
			Protocol cmdProtocol=new Protocol();
			cmdProtocol.fromByte(data);
			if(cmdProtocol.getBody() instanceof Cmd21ResponseBody){
				Cmd21ResponseBody cmd21Response=(Cmd21ResponseBody)cmdProtocol.getBody();
				setMemData(cmd21Response.getData());		
				Toast.makeText(MainActivity.this, R.string.read_ok, 1000).show();
			}
			else if(cmdProtocol.getBody() instanceof CmdResponseBody){
				if(lastOperate==R.id.btnSetting){
					Toast.makeText(MainActivity.this, R.string.set_ok, 1000).show();
				}else if(lastOperate==R.id.btnSave) {
					Toast.makeText(MainActivity.this, R.string.save_ok, 1000).show();
				}
			}
			adapter.notifyDataSetChanged();
			
		}
		
	};
	
	//数据侦听者
	private IDataReceiveListener datarecieveListener= new IDataReceiveListener() {
		@Override
		public void onRecieveData(Object source, byte[] data) {
			if(data!=null && data.length>0){
				if(data[0]=='>'){
					tempData=data;
				}else{
					tempData=CommonUtil.concat(tempData, data);
				}
		
				if(tempData!=null && tempData.length>3){
					int len=tempData.length;
					if(tempData[len-1]==0x0A && tempData[len-2]==0x0D){
						Message message=new Message();
						message.obj=tempData;
						
						////下面两行是模拟查询返回数据
						//String str=">00006504A8480000005D045E4C000000790101002B01010021010100270103002201090028010400530101006D027800002301010024017F001F01000020010000290100002A0100\r\n";
						//message.obj=str.getBytes();
						
						dealBluetoothDataHandler.sendMessage(message);
					}
				}
			}else{
				return ;
			}
		}
	};
	
	//初始化化Handler,用于处理蓝牙设备发送过来的异步消息
	private Handler dealUsbDeviceChangedHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			String data=(String)msg.obj;
			txtLog.setText("Recieve:"+data);
			if(msg.arg1==1){ //插入Usb设备
				if(R.id.rdoUsb!=grpCommType.getCheckedRadioButtonId()){
					grpCommType.check(R.id.rdoUsb);
				}
			}else if(msg.arg1==-1){//移除Usb设备
				grpCommType.check(R.id.rdoBluetooth);
			}
		}
	};

}
