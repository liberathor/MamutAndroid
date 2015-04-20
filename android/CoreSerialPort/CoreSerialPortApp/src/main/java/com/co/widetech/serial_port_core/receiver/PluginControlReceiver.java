package com.co.widetech.serial_port_core.receiver;

import com.co.widetech.serial_port_core.tools.StatusChargin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PluginControlReceiver extends BroadcastReceiver {
	private StatusChargin mStatusChargin = StatusChargin.getInstance();

	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
			System.out.println("Cable de poder conectado");
			this.mStatusChargin.setStatusBattery(true);
			System.out.println("Status del singleton: "
					+ mStatusChargin.getStatusBattery());
		} else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
			System.out.println("Cable de porder desconectado");
			this.mStatusChargin.setStatusBattery(false);
			System.out.println("Status del singleton: "
					+ mStatusChargin.getStatusBattery());
		}
	}
}
