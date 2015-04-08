package co.com.widetech.mamut.android.view;

import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import com.co.widetech.serial_port_core.models.DeviceStatus;
import com.co.widetech.serial_port_core.service.TransportDataService;
import com.co.widetech.serial_port_core.tools.SerialPortPreferences;
import utils.AlertBuilder;
import utils.SharedStatus;

/**
 * Created by wtjramirez on 3/30/15.
 */
public abstract class BinderServiceActivity extends ActionBarActivity {
    private static final String TAG = "BinderServiceActivity";
    SharedStatus sharedStatusApp;
    boolean mBound;
    boolean whaitAck;
    private TransportDataService mService;
    private DeviceStatus deviceStatus;
    private SharedPreferences sPrefInfoDevice;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected()");
            TransportDataService.LocalBinder binder = (TransportDataService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected()");
            mBound = false;
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        sPrefInfoDevice = sharedStatusApp.getInfoDevice();
        Boolean status = sPrefInfoDevice.getBoolean("valueInfoDevice", false);
        Log.d(TAG, "onPostCreate()");
        if (!status) {
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            AlertBuilder.buildAlertNotificatonNewConfiguration(this,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onPostCreate()");
                            if (input.getText().toString().equalsIgnoreCase("wt2015")) {
                                sharedStatusApp.setInfoDevice(true);
                                startActivity(new Intent(BinderServiceActivity.this,
                                        SerialPortPreferences.class));
                                new DeviceStatus(BinderServiceActivity.this).setStatusSelectSerialPort(true);
                            }
                        }
                    },
                    input
            ).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        deviceStatus = new DeviceStatus(getApplicationContext());
        sharedStatusApp = new SharedStatus(this);
        Intent intent = new Intent(this, TransportDataService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    protected abstract boolean isValid();

    protected final boolean sendData() {
        String data = "";
        if (isValid()) {
            data = buildData();
            return transportMessage(data);
        }
        Log.d(TAG, "Can't sendData() " + data);
        return false;
    }

    protected abstract String buildData();

    private final boolean transportMessage(String data) {
        Log.d(TAG, "transportMessage() data: " + data);
        if (mBound) {
            mService.transportMessage(data, whaitAck);
            return true;
        }
        return false;
    }

    protected void setWhaitAck(boolean whaitAck) {
        this.whaitAck = whaitAck;
    }
}
