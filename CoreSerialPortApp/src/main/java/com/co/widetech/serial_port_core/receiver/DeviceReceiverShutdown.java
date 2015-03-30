package com.co.widetech.serial_port_core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.co.widetech.serial_port_core.models.DeviceStatus;
import com.co.widetech.serial_port_core.reporter.CommunicationService;
import com.co.widetech.serial_port_core.tools.Utils;

public class DeviceReceiverShutdown extends BroadcastReceiver {

    SharedPreferences sp;
    String unity;
    Context context;
    private CommunicationService commService;
    private DeviceStatus deviceStatus;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        deviceStatus = new DeviceStatus(context);
        commService = CommunicationService.getInstance();
        commService.setContext(context);

        SharedPreferences sp = deviceStatus.getsPreferenceManager();
        unity = sp.getString("OptionUnityType", "");

        messageStatusDevice();
    }

    private void messageStatusDevice() {

        StringBuilder sb = new StringBuilder();
        sb.append("MDT");
        sb.append(Utils.StartApplication(unity));
        sb.append(String.valueOf("S0"));

        commService.convertMessage(sb.toString(), false);

        System.out.println(sb.toString());

    }
}