package com.rpt.jx.protocol;

import java.io.InputStream;
import java.util.Arrays;
import org.apache.http.util.EncodingUtils;

import android.content.Context;


public class CommonUtil {
	static public byte[] intToByte(int value,int length){
		byte[] result=new byte[0];
		switch(length){
			case 1:{
				result=new byte[1];
				result[0]=(byte)(value & 0x00FF);
				break;
			}
			case 2:{
				result=new byte[2];
				result[0]=(byte)(value & 0x00FF);
				result[1]=(byte)(value>>8 & 0x00FF);
				break;
			}
			case 4:{
				result=new byte[4];
				result[0]=(byte)(value & 0x00FF);
				result[1]=(byte)(value>>8 & 0x00FF);
				result[2]=(byte)(value >>16 & 0x00FF);
				result[3]=(byte)(value>>24 & 0x00FF);
				break;
			} 
		}
		return result;
	}
		
	static public int byteToInt(byte[] value){
		if(value==null || value.length==0) return 0;
		int result;
		switch(value.length){
			case 1:{
				result=value[0] ;
				break;
				
			}
			case 2:{
				result=value[1];
				result=result<<8 | (0x00FF &  value[0]);
				break;
			}
			case 4:{
				result=value[3];
				result=result<<8 | (0x00FF &  value[2]);
				result=result<<8 | (0x00FF &  value[1]);
				result=result<<8 | (0x00FF &  value[0]);
				break;
			}
			default: result= 0;
		}
		return result;
	}
	
	public static byte[] floatToByte(float f) {  
	    // 把float转换为byte[]  
	    int fbit = Float.floatToIntBits(f);  
	      
	    byte[] b = new byte[4];    
	    for (int i = 0; i < 4; i++) {    
	        b[i] = (byte) (fbit >> (24 - i * 8));    
	    }   
	      
	    // 翻转数组  
	    int len = b.length;  
	    // 建立一个与源数组元素类型相同的数组  
	    byte[] dest = new byte[len];  
	    // 为了防止修改源数组，将源数组拷贝一份副本  
	    System.arraycopy(b, 0, dest, 0, len);  
	    byte temp;  
	    // 将顺位第i个与倒数第i个交换  
	    for (int i = 0; i < len / 2; ++i) {  
	        temp = dest[i];  
	        dest[i] = dest[len - i - 1];  
	        dest[len - i - 1] = temp;  
	    }   
	    return dest;   
	}  
	
	
	/** 
	 * 字节转换为浮点 
	 *  
	 * @param b 字节（至少4个字节） 
	 * @return
	 */  
	public static float byteToFloat(byte[] b) {    
	    int l;                                             
	    l = b[0];                                  
	    l &= 0xff;                                         
	    l |= ((long) b[1] << 8);                   
	    l &= 0xffff;                                       
	    l |= ((long) b[2] << 16);                  
	    l &= 0xffffff;                                     
	    l |= ((long) b[3] << 24);                  
	    return Float.intBitsToFloat(l);                    
	}  
	
			
	//打印数组
	static public void println(byte[] data) {
		StringBuffer sb = new StringBuffer();
		if(data==null || data.length==0) {
			sb.append("null");
		}else{
			for (int i = 0; i < data.length; i++) {
				sb.append(String.format("%02x ", data[i]));
			}
		}
		System.out.println(sb);
	}

	//格式化数组
	static public String format(byte[] data) {
		StringBuffer sb = new StringBuffer();
		if(data==null || data.length==0) {
			sb.append("null");
		}else{
			for (int i = 0; i < data.length; i++) {
				sb.append(String.format("%02x ", data[i]));
			}
		}
		return sb.toString();
	}
	
	//对字节数组进行编码(把字节数组转换为Ascii,然后再转换为字节数组)
	static public byte[] encodeBytes(byte[] data) {
		StringBuffer sb = new StringBuffer();
		if(data==null || data.length==0) {
			return null;
		}else{
			for (int i = 0; i < data.length; i++) {
				sb.append(String.format("%02X", data[i]));
			}
		}
		String tempStr=sb.toString();
		byte[] result=tempStr.getBytes();
		return result;
	}
	
	//对字节数组进行解码
	static public byte[] decodeBytes(byte[] data) {
		String tempStr="";
		if(data==null || data.length==0) {
			return null;
		}else{
			tempStr=new String(data);
		}
		byte[] result=new byte[tempStr.length()/2];
		for(int i=0;i<result.length;i++){
			String str=tempStr.substring(i*2,i*2+2);
			 result[i]= (byte)Short.parseShort(str,16);
		}
		return result;
	}
	
	//合并两个字节数组
	static public  byte[] concat(byte[] first, byte[] second) {
		if(first==null || first.length==0) return second;
		if(second==null || second.length==0) return first;
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	

	static public String[] readFile(Context context,String fileName) {
	   	  String res = ""; 
	   	  String[] content = null;
	         try { 
	        	 InputStream in = context.getApplicationContext().getResources().getAssets().open(fileName); 
	             int length = in.available(); 
	             byte[] buffer = new byte[length]; 
	             in.read(buffer); 
	             res = EncodingUtils.getString(buffer, "UTF-8"); 
	             content = res.split("\n");
	             in.close();
	         } catch (Exception e) { 
	             e.printStackTrace(); 
	         } 
	         return content; 
	}	
	
}
