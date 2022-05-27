/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kochar.msg; 

import com.kochar.common.LogItem;
import com.kochar.common.ServiceMessage;
import com.kochar.services.KSimpleLogger;
import java.util.logging.Level;

/**
 *
 * @author gundeep.kaur
 */
public class TripletBean { 
    
    public enum TCacheState {
        TNO_CACHE,
        TYES_CACHE
    }

    public static final String MSISDN_DEFAULT = "0000000000";
    public static final String IMEI_DEFAULT = "000000000000000";
    public static final String IMSI_DEFAULT = "000000000000000";
    public static final String OPERATOR_DEFAULT = "OPERATOR_DEFAULT";
    public static final String STRING_DEFAULT = "None";


    String msisdn, imei, imsi, settingType, actualSetting, brand, model, operatorName, smsText;
    int recid, status, actionCount, serviceId, smsType, otaDispatchCount, nonOtaDispatchCount;
    long timeStamp;
    TCacheState cState;
    int batchCount, selfCount;
  
    public TripletBean() 
    {
        msisdn = MSISDN_DEFAULT;
        msisdn = msisdn.intern();
        imei = IMEI_DEFAULT;
        imei = imei.intern();
        imsi = IMSI_DEFAULT;
        imsi = imsi.intern();
        status = 0;
        settingType = STRING_DEFAULT;
        settingType = settingType.intern();
        actualSetting = STRING_DEFAULT;
        actualSetting = actualSetting.intern();
        brand = STRING_DEFAULT;
        brand = brand.intern();
        model = STRING_DEFAULT;
        model = model.intern();
        recid = 0;
        timeStamp = System.currentTimeMillis();
        cState = TCacheState.TNO_CACHE;
        actionCount = 0;
        operatorName = OPERATOR_DEFAULT;
        operatorName = operatorName.intern();
        serviceId = 0;
        smsType = 0;
        smsText = STRING_DEFAULT;
        smsText = smsText.intern();
        otaDispatchCount = 0;
        nonOtaDispatchCount = 0;
        batchCount = 0;
        selfCount = 0;
    }

    public TripletBean(String m, String ie, String is) {
        msisdn = (m == null || m.isEmpty() ? MSISDN_DEFAULT : m);
        msisdn = msisdn.intern();
        imei = (ie == null || ie.isEmpty() ? IMEI_DEFAULT : ie);
        imei = imei.intern();
        imsi = (is == null || is.isEmpty() ? IMSI_DEFAULT : is);
        imsi = imsi.intern();
        status = 0;
        settingType = STRING_DEFAULT;
        settingType = settingType.intern();
        actualSetting = STRING_DEFAULT;
        actualSetting = actualSetting.intern();
        brand = STRING_DEFAULT;
        brand = brand.intern();
        model = STRING_DEFAULT;
        model = model.intern();
        recid = 0;
        timeStamp = System.currentTimeMillis();
        cState = TCacheState.TNO_CACHE;
        actionCount = 0;
        operatorName = OPERATOR_DEFAULT;
        operatorName = operatorName.intern();
        serviceId = 0;
        smsType = 0;
        smsText = STRING_DEFAULT;
        smsText = smsText.intern();
        otaDispatchCount = 0;
        nonOtaDispatchCount = 0;
        batchCount = 0;
        selfCount = 0;
    }
    
    public TripletBean(String m, String ie, String is, String op) {
        msisdn = (m == null || m.isEmpty() ? MSISDN_DEFAULT : m);
        msisdn = msisdn.intern();
        imei = (ie == null || ie.isEmpty() ? IMEI_DEFAULT : ie);
        imei = imei.intern();
        imsi = (is == null || is.isEmpty() ? IMSI_DEFAULT : is);
        imsi = imsi.intern();
        status = 0;
        settingType = STRING_DEFAULT;
        settingType = settingType.intern();
        actualSetting = STRING_DEFAULT;
        actualSetting = actualSetting.intern();
        brand = STRING_DEFAULT;
        brand = brand.intern();
        model = STRING_DEFAULT;
        model = model.intern();
        recid = 0;
        timeStamp = System.currentTimeMillis();
        cState = TCacheState.TNO_CACHE;
        actionCount = 0;
        operatorName = (op.isEmpty() ? OPERATOR_DEFAULT : op);
        operatorName = operatorName.intern();
        serviceId = 0;
        smsType = 0;
        smsText = STRING_DEFAULT;
        smsText = smsText.intern();
        otaDispatchCount = 0;
        nonOtaDispatchCount = 0;
        batchCount = 0;
        selfCount = 0;
    }
    
    public TripletBean(TripletBean obj){
        msisdn = obj.getMobile();
        msisdn = msisdn.intern();
        imei = obj.getImei();
        imei = imei.intern();
        imsi = obj.getImsi();
        imsi = imsi.intern();
        status = obj.getStatus();
        settingType =  obj.getSettingType(); 
        settingType = settingType.intern();
        actualSetting = obj.getActualSetting();
        actualSetting = actualSetting.intern();
        brand = obj.getBrand();
        brand = brand.intern();
        model = obj.getModel();
        model = model.intern();
        recid = obj.getRecId();
        timeStamp = obj.getTimeStamp();
        cState = obj.getCacheState();
        actionCount = obj.getActionCount();
        operatorName = obj.getOperatorName();
        operatorName = operatorName.intern();
        serviceId = obj.getServiceId();
        smsType = obj.getSmsType();
        smsText = obj.getSmsText();
        smsText = smsText.intern();
        otaDispatchCount = obj.getOtaDispatchCount();
        nonOtaDispatchCount = obj.getNonOtaDispatchCount();        
        batchCount = obj.getBatchCount();
        selfCount = obj.getSelfCount();
    }
    
    public void dtorTripletBean() {
        msisdn = null;
        imei = null;
        imsi = null;
        settingType =  null; 
        actualSetting = null;
        brand = null;
        model = null;
        operatorName = null;
        smsText = null;
    }
    
    public int getBatchCount() {
        return this.batchCount;
    }

    public void setBatchCount(int c) {
        this.batchCount = c;
    }

    public int getSelfCount() {
        return this.selfCount;
    }

    public void setSelfCount(int c) {
        this.selfCount = c;
    }
    
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
        this.operatorName = this.operatorName.intern();
    }

    public int getOtaDispatchCount() {
        return this.otaDispatchCount;
    }

    public void setOtaDispatchCount(int c) {
        this.otaDispatchCount = c;
    }
    
    public boolean getOtaDispatchTriesRemain()
    {
        return (this.otaDispatchCount < 10);
    }
    
    public int getNonOtaDispatchCount() {
        return this.nonOtaDispatchCount;
    }

    public void setNonOtaDispatchCount(int c) {
        this.nonOtaDispatchCount = c;
    }
    
    public boolean getNonOtaDispatchTriesRemain()
    {
        return (this.nonOtaDispatchCount < 10);
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getActualSetting() {
        return actualSetting;
    }

    public void setActualSetting(String s) {
        this.actualSetting = s;
        this.actualSetting = this.actualSetting.intern();
    }
    
    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int ac) {
        this.actionCount = ac;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String s) {
        this.settingType = s;
        this.settingType = this.settingType.intern();
    }
    
    public String getBrand() {
        return brand;
    }

    public void setBrand(String b) {
        this.brand = b;
        this.brand = this.brand.intern();
    }
    
    public TCacheState getCacheState()
    {
        return cState;
    }
    
    public void setCacheState(TCacheState s)
    {
        cState = s;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String m) {
        this.model = m;
        this.model = this.model.intern();
    }

    public String getMobile() {
        return msisdn;
    }

    public void setMobile(String mobile) {
        this.msisdn = mobile;
        this.msisdn = this.msisdn.intern();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int s) {
        this.status = s;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
        this.imei = this.imei.intern();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
        this.imsi = this.imsi.intern();
    }

    public int getRecId() {
        return recid;
    }

    public void setRecId(int id) {
        this.recid = id;
    }
    
    public int getSmsType() {
        return smsType;
    }

    public void setSmsType(int st) {
        this.smsType = st;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
        this.smsText = this.smsText.intern();
    }

    public TripletBean clone(){
        return new TripletBean(this);
    }
    
    public boolean isEmpty()
    {
       return ( this.imei.equals(IMEI_DEFAULT) && this.imsi.equals(IMSI_DEFAULT) && this.msisdn.equals(MSISDN_DEFAULT) );        
    }

    public boolean equals(Object o)
    {
        TripletBean oth = (TripletBean) o;
        return ( (this.imei.equals(oth.imei)) && (this.imsi.equals(oth.imsi)) && (this.msisdn.equals(oth.msisdn)) && (this.operatorName.equalsIgnoreCase(oth.operatorName)) );
    }
    
    public int hashCode()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.imei);
        sb.append(this.imsi);
        sb.append(this.msisdn);
        return sb.toString().hashCode();
    }
    
    public String toString() {
        return String.format("recid: %d, imei: %s, imsi: %s, msisdn: %s, settingtype: %s, actualsetting: %s, brand: %s, model: %s, status: %d, actionCount: %d , serviceId: %d, operatorName %s",
                recid, imei, imsi, msisdn, settingType, actualSetting, brand, model, status, actionCount, serviceId, operatorName);
    }
    
    public String toTriplet() {
        return String.format("recid: %d, imei: %s, imsi: %s, msisdn: %s, settingtype: %s, actualsetting: %s, brand: %s, model: %s, status: %d, actionCount: %d, serviceId: %d, operatorName %s",
                recid, imei, imsi, msisdn, settingType, actualSetting, brand, model, status, actionCount, serviceId, operatorName);
    }
    
    public String getPinFromImsi() {
        String functionReturnValue = "";
        try {
            String imsi = checkIMSI(this.imsi);
            //convert to hexstring
            if (imsi.length() % 2 > 0) {
                //odd number of digits
                imsi = "9" + imsi;
            } else {
                //even number of digits
                imsi = "1" + imsi + "F";
            }

            for (int x = 0; x <= imsi.length() - 1; x += 2) {
                functionReturnValue += "%" + imsi.substring(x + 1, x + 2) + imsi.substring(x, x + 1);
            }
        } catch (Exception ex) {
               KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "getKeyFromImsi()", ex.toString(), ex)));
        }
        return functionReturnValue;
    }
    
    //get rid of whitespace
    private String checkIMSI(String imsi) {
        String rVal;
        
        if(imsi.length() > 15) {
            rVal = imsi.trim().substring(0, 15);
        } else {
            rVal = imsi;
        }

        return rVal;
    }
    
    public String toMemento()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.imei);
        sb.append(this.imsi);
        sb.append(this.msisdn);
        return sb.toString();
    }
    
}
