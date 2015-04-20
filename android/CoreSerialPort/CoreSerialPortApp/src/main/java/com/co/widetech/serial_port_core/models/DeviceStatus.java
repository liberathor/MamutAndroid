package com.co.widetech.serial_port_core.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DeviceStatus {
	
	private Context context;
	private SharedPreferences sStatusSelectSerialPort;
	private SharedPreferences sPreferenceManager;
	private SharedPreferences sPrefStatusLogin;
	
	public DeviceStatus(Context context) {
		this.context = context;
		this.sStatusSelectSerialPort = context.getSharedPreferences("StatusSelectSerialPort",
				Context.MODE_PRIVATE);
		this.sStatusSelectSerialPort.getBoolean("valueSelectSerialPort", false);
		
		this.sPrefStatusLogin = context.getSharedPreferences("AccessLogin", Context.MODE_PRIVATE);
		this.sPrefStatusLogin.getBoolean("statusLogin", false);
		
		sPreferenceManager = PreferenceManager.getDefaultSharedPreferences(context);
		
	}

	
	public SharedPreferences getsPreferenceManager() {
		return sPreferenceManager;
	}

	public void setStatusSelectSerialPort(boolean value) {
		this.sStatusSelectSerialPort =context.getSharedPreferences("StatusSelectSerialPort",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sStatusSelectSerialPort.edit();
		editor.putBoolean("valueSelectSerialPort", value);
		editor.commit();
	}

	public SharedPreferences getStatusSelectSerialPort() {
		return sStatusSelectSerialPort;
	}

	public void setStatusLogin(boolean value) {
		this.sPrefStatusLogin =context.getSharedPreferences("AccessLogin",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sPrefStatusLogin.edit();
		editor.putBoolean("statusLogin", value);
		editor.commit();
	}

	public SharedPreferences getStatusLogin() {
		return sPrefStatusLogin;
	}
	
}
