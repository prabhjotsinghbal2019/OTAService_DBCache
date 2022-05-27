/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kochar.common;

/**
 *
 * @author kochar-it-prabhjot
 */
public class TripletValidator {

	public TripletValidator(String imei, String imsi, String msisdn) {
		// TODO Auto-generated constructor stub
		this.imei = (imei == null ? "" : imei.trim());
		this.imsi = (imsi == null ? "" : imsi.trim());
		this.msisdn = (msisdn == null ? "" : msisdn.trim());
	}
	
	public boolean isValid()
	{
		statusCode = 200;
		message = "Ok";
		
		for (int i = 0; i < 3; i++) {
			switch (i) {

			case 0: // imei check
				isImeiValid();
				break;
			case 1: // imsi check
				isImsiValid();
				break;
			case 2: // msisdn check
				isMsisdnValid();	
				break;

			}
			
			if (statusCode != 200)
			{
				break;
			}
		}		
		
		return (statusCode == 200);
		
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public int getStatusCode()
	{
		return statusCode;
	}
	
	private void isImeiValid()
	{
		int length = this.imei.length();
		if (length < 14 || length > 16)
		{
		    statusCode = 1000;
		    message = "Imei Length Invalid.";
		    return;
		}
		
		if (this.imei.matches("^[0-9]{14,16}$") == false)
		{
                    statusCode = 1001;
                    message = "Imei contains Invalid Values.";
                    return;
		}
		
	}
	
	private void isImsiValid()
	{
		int length = this.imsi.length();
		if (length < 14 || length > 17) // imsi should be less or equal to 15
		{
		    statusCode = 1000;
		    message = "Imsi Length Invalid.";
                    return;
		}
		
		if (this.imsi.matches("^[0-9]{14,17}$") == false)
		{
			statusCode = 1001;
			message = "Imsi contains Invalid Values.";
			return;
		}
		
	}

	private void isMsisdnValid()
	{
		int length = this.msisdn.length();
		if (length < 7 || length > 15) // msisdn should be less or equal to 15
		{
		    statusCode = 1000;
		    message = "Msisdn Length Invalid.";
			return;
		}
		
		if (this.msisdn.matches("^[0-9]{7,15}$") == false)
		{
			statusCode = 1001;
			message = "Msisdn contains Invalid Values.";
			return;
		}

	}

	
	private String imei = "";
	private String imsi = ""; 
	private String msisdn = "";
	
	private int statusCode = 200;
	private String message = "Ok";

}
