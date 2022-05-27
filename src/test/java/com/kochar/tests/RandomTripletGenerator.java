/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kochar.tests;

import com.kochar.msg.TripletBean;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author PBal
 */
public class RandomTripletGenerator {
    
    private static final String NUMS = "0123456789";
    private static final String ALPHAS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    RandomTripletGenerator() {
        
    }
    
    public static int getRandomSmallInt() {
        return new Random().nextInt(255);
    }

    public static int getRandomInt() {
        return new Random().nextInt();
    }
    
    public static long getRandomLong() {
        return new Random().nextLong();
    }
    
    public static String getRandomNumStr(int length) {
         String generatedString = RandomStringUtils.random(length, NUMS);
         return generatedString;
    }
    
    public static String getRandomAlphaStr(int length) {
         String generatedString = RandomStringUtils.random(length, ALPHAS);
         return generatedString;
    }

    public static String getRandomAlphaNumStr(int length) {
         String generatedString = RandomStringUtils.random(length, ALPHAS + NUMS);
         return generatedString;
    }
    
    public static TripletBean getRandomOTATriplet() {
        String mobile = getRandomNumStr(10);
        String imsi = getRandomNumStr(18);
        String imei = getRandomNumStr(14);
        String oper = getRandomAlphaStr(10);
        TripletBean rVal = new TripletBean(mobile,imei,imsi,oper);
        rVal.setStatus(getRandomSmallInt());
        rVal.setServiceId(getRandomSmallInt());
        rVal.setTimeStamp(System.currentTimeMillis());
        return rVal;
    }
    
}
