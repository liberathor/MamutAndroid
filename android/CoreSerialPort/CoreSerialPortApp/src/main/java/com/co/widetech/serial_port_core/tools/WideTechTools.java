package com.co.widetech.serial_port_core.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.Toast;

/*Utilerias de la Aplicacion*/

public class WideTechTools {
	/* Retorna la fecha Actual del Celular en formato 'yyyy-MM-dd' */
	public static String getDatePhone() {
		Calendar cal = new GregorianCalendar();
		Date date = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formatteDate = df.format(date);
		return formatteDate;
	}

	/* Retorna la fecha Actual del celular en formato 'HH:mm:ss' */
	public static String getHourPhone() {
		Date dt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formatteHour = df.format(dt.getTime());
		return formatteHour;
	}
	/*
	 * Retorna el IMEI del Dispositivo. Recibe como parametro el contexto de la
	 * actividad desde la cual es invocada
	 */
	public static String getImeiPhone(Context context) {
		TelephonyManager phoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return phoneManager.getDeviceId();
	}

	/*
	 * Retorna el Carrier que Utiliza el dispositivo. Recibe como parametro el
	 * contexto de al actividad desde la cual es invocada
	 */
	public static String getCarrierPhone(Context context) {
		TelephonyManager phoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return phoneManager.getNetworkOperatorName();
	}

	/* Retorna el marca del Dispositivo */
	public static String getMarkPhone() {
		return android.os.Build.MANUFACTURER;
	}

	/* Retorna el modelo del Dispositivo */
	public static String getModelPhone() {
		return android.os.Build.MODEL;
	}
	
	/* Verifica que un campo de tipo EditText no este vacio, retornando un booleano
	 * como variable de control y desplegando un mensaje de tipo Toast en Android*/
	public static boolean validateEmptyEditText(Context context, EditText text)
	{
		boolean valid = true;
    	
    	if(text.getText() == null || text.getText().toString().equalsIgnoreCase(""))
    		valid = false;
    	
    	if(!valid)
    		Toast.makeText(context,"Campo vacio", Toast.LENGTH_LONG).show();
    	
    	return valid;
	}
}
