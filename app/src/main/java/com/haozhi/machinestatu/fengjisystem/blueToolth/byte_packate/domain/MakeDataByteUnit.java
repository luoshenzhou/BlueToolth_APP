package com.haozhi.machinestatu.fengjisystem.blueToolth.byte_packate.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shenzhu on 2018/1/7.
 * 对要发送的蓝牙数据组包
 */
public class MakeDataByteUnit {
    Byte[] base=new Byte[]{0x01,0x01,0x00,0x00,0x00,0x00,(byte)0xFF,0x7F,0x00,(byte)0x80,0x01};
    public byte[] makeReadPake(){
        List<Byte> list=new ArrayList<>();
        List<Byte> list1 = Arrays.asList(base);
        list.addAll(list1);
        list.add((byte) 0x02);//读取
        list.add((byte) 0xFF);
        list.add(长度);
        list.add(功能id);
        list.add(值);
        list.add(校验位);
        之后进行转义；
        list.set(0, 开始标志);
        list.add(结束标志);
        return
    }
    public byte[] makeSettingPake(){
        List<Byte> list=new ArrayList<>();
        List<Byte> list1 = Arrays.asList(base);
        list.addAll(list1);
        list.add((byte) 0x03);//设置
        list.add((byte) 0xFF);
        list.add(长度);
        list.add(功能id);
        list.add(值);
        list.add(校验位);
        之后进行转义；
        list.set(0,开始标志);
        list.add(结束标志);
        return
    }
    public byte[] makeAlarmPake(){
        List<Byte> list=new ArrayList<>();
        List<Byte> list1 = Arrays.asList(base);
        list.addAll(list1);
        list.add((byte) 0x01);//报警
        list.add((byte) 0xFF);
        list.add(长度);
        list.add(功能id);
        list.add(值);
        list.add(校验位);----未完成
        之后进行转义；
        list.set(0,开始标志);
        list.add(结束标志);
        return
    }

}
