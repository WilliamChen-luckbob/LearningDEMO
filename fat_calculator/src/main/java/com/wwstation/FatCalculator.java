package com.wwstation;

import sun.plugin2.message.ShowStatusMessage;

import java.math.BigDecimal;

/**
 * @author william
 * @description
 * @Date: 2021-08-29 23:35
 */
public class FatCalculator {
    /**
     * 你的年龄
     */
    private static final Double AGE = 29.0;
    private static final Double WEIGHT = 92.0;
    private static  final Double HEIGHT=1.84;
    private static final String GENDER="男";
    private static final PA PA= com.wwstation.PA.适中运动量;

    private static final String LOG="你的每日消耗量应为%s大卡,此数据不是基础代谢！";

    public static void main(String[] args) {
    if (GENDER.equals("男")){
        System.out.println(male());
    }else {
        System.out.println(female());
    }
    }

    private static String male() {
        Double value = 662 - (9.53 * AGE) + PA.getMale() * (15.91 * WEIGHT + 539.6 * HEIGHT);
        return String.format(LOG,value.toString());
    }

    private static String female() {
        Double value = 354 - (6.91 * AGE) + PA.getFemale() * (9.36 * WEIGHT + 726 * HEIGHT);
        return String.format(LOG,value.toString());
    }
}
