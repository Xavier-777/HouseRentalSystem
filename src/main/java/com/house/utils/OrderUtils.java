package com.house.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtils {

    /**
     * 生成订单号，由当前时间戳 + 4位随机数组成
     *
     * @return
     */
    public static String getOrderNumber() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssLL");
        String s1 = format.format(new Date());      //时间戳
        int random = (int) (Math.random() * (9999 - 1000 + 1) + 1000);
        String s2 = String.valueOf(random);         //4位随机数
        return s1 + s2;
    }
}
