package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import co.com.widetech.mamut.android.view.IngresoActivity;


public class DeviceReceiverPower extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent();
        serviceIntent
                .setAction("com.co.widetech.serial_port_core.service.TransportDataService");
        context.startService(serviceIntent);

//		context.startService(new Intent(context.getApplicationContext(), TransportData.class));

        Intent intent1 = new Intent(context, IngresoActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}