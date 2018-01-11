package com.haozhi.machinestatu.fengjisystem.blueToolth.byte_packate.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenzhu on 2018/1/7.
 * 数据转义
 *
 * 转义规则：用0x5E，0x5D来代替0x5E；用0x5E，0x7D来代替0x7E
 *
 * 发送数据：
 * 由于在组包的时候，将不是开始、结束标志位的数据，但是和开始、结束标志位相同的数据要进行转义，要加
 *
 * 接收数据：
 * 由于在解包的时候，将不是开始、结束标志位的数据，但是和开始、结束标志位相同的数据要进行转义，要减
 */
public class DateByteChang {
    //开始、结束标志（转义标志）
    private static byte start_end_target=0x7E;

    //转义标志0
    private static byte change_target=0x5E;

    //转义标志1
    private static byte change_target1=0x5D;

    //转义标志2
    private static byte change_target2=0x7D;



    /**
     * 接收到蓝牙数据包，要将在数据部分包含转义标志的转义标志剔除（如果有）
     * @param byteData 接收到的蓝牙的原始数据
     * @return 剔除转义标志的真实数据包
     */
    public static Byte[] changeRecetiveByteData(byte[] byteData){
        boolean completeData = isCompleteData(byteData);
        if (!completeData){
            //如果是完整的数据包，就检查是否有需要转义的数据
            //如果不是完整的数据包，就报错
            throw  new RuntimeException("不完整数据包");
        }

        /**
         * 如果有转义的数据，就根据转义规则剔除转义的标志，然后返回
         * 开头和结尾标志位不用检查
         */
        List<Byte> list=new ArrayList<Byte>();
        for(int i=1;i<byteData.length-1;i++){
            if (byteData[i]==change_target){
                if (i!=byteData.length-2){
                    if (byteData[i+1]==change_target1){
                        //如果等于0x5E,0x5D就要0x5E
                        list.add(change_target);
                        i=i+2;
                    }if (byteData[i+1]==change_target2){
                        //如果等于0x5E,0x7D就换成0x7E
                        list.add(start_end_target);
                        i=i+2;
                    } else {
                        continue;
                    }
                }else {
                    throw new RuntimeException("数据包异常,有可能是转义数据，但已经到最后一位了");
                }
            }else {
                list.add(byteData[i]);
            }
        }

        //检查完后加上开始和结束的标志
        list.add(0,start_end_target);
        list.add(start_end_target);

        Byte[] bytes = listToByteArray(list);

        return bytes;


    }

    /**
     * 在想蓝牙硬件发送数据包前，要将在数据部分包含和某些标志冲突的数据进行转义标志（如果有），
     * 转义后在发送
     * @param byteData 发送前的真实蓝牙数据包
     * @return 如有要转义的数据，加上转义标志的数据包
     */
    public static Byte[] parseSendByteData(byte[] byteData){
        //通过开始、结束的标志和其所在的位置，先判断是否为一个完整的数据包
        boolean completeData = isCompleteData(byteData);
        if (!completeData){
            //如果是完整的数据包，就检查是否有需要转义的数据
            //如果不是完整的数据包，就报错
            throw  new RuntimeException("不完整数据包");
        }
        /**
         * 如果有转义的数据，就根据转义规则加上转义的标志，然后返回
         * 开头和结尾标志位不用检查
         */
        List<Byte> list=new ArrayList<Byte>();
        for(int i=1;i<byteData.length-1;i++){
            if (byteData[i]==change_target){
                list.add(change_target);
                list.add(change_target1);
            }else if (byteData[i]==start_end_target){
                list.add(change_target);
                list.add(change_target2);
            }else {
                list.add(byteData[i]);
            }
        }

        //检查完后加上开始和结束的标志
        list.add(0,start_end_target);
        list.add(start_end_target);

        Byte[] bytes = listToByteArray(list);

        return bytes;
    }


    /**
     * 是否为完整的数据包
     * （0x7E）:结束标志的内容与起始标志相同,长度为1字节
     * @return 判断的结果  true:完整  false:不完整
     */
    public static boolean isCompleteData(byte[] byteData){

        //获取第一个字节和最后一个字节的数据
        byte startTarget = byteData[0];
        byte endTarget = byteData[byteData.length-1];

        //判断两者是否等于0x7E
        if (startTarget==start_end_target&&endTarget==start_end_target){
            return true;
        }
        return false;
    }

    public static Byte[] listToByteArray(List<Byte> list){
        int size = list.size();
        Byte[] bytes = (Byte[]) list.toArray(new Byte[size]);
        return bytes;
    }
}
