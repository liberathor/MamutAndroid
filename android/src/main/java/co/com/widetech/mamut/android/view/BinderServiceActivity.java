package co.com.widetech.mamut.android.view;

import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import co.com.widetech.mamut.android.utils.AlertBuilder;
import co.com.widetech.mamut.android.utils.SharedStatus;
import com.co.widetech.serial_port_core.models.DeviceStatus;
import com.co.widetech.serial_port_core.service.TransportDataService;
import com.co.widetech.serial_port_core.tools.SerialPortPreferences;

import java.util.Calendar;

/**
 * Created by wtjramirez on 3/30/15.
 */
public abstract class BinderServiceActivity extends ActionBarActivity {
    private static final int MAX_INTENTS = 3;
    private static final int ELAPSED_SECONDS_TO_ENTER_SETTINGS = 1;
    private static final String TAG = "BinderServiceActivity";
    private static int intentToEnterSettings;
    private static long timeFirstIntentEnterSettings;
    SharedStatus sharedStatusApp;
    boolean mBound;
    boolean whaitAck;
    TransportDataService mService;
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
            enterToSettings();
        }
    }

    private void enterToSettings() {
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

    @Override
    protected void onResume() {
        if (mBound == false) {
            Log.d(TAG, "onResume()");
            Intent intent = new Intent(this, TransportDataService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
        super.onResume();
    }

    protected abstract boolean isValid();

    protected final boolean sendData(boolean whaitAck) {
        setWhaitAck(whaitAck);
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

    public void intentToEnterSettings() {
        int actualTime = Calendar.getInstance().get(Calendar.SECOND);
        long elapsedTimeSeconds = actualTime - timeFirstIntentEnterSettings;
        Log.d(TAG, "intentToEnterSettings() elapsedTime: " + elapsedTimeSeconds + " intent: " + intentToEnterSettings);
        if (elapsedTimeSeconds <= ELAPSED_SECONDS_TO_ENTER_SETTINGS && elapsedTimeSeconds >= 0) {
            if (intentToEnterSettings < MAX_INTENTS) {
                ++intentToEnterSettings;
            } else if (intentToEnterSettings == MAX_INTENTS) {
                enterToSettings();
            }
        } else {
            intentToEnterSettings = 0;
            timeFirstIntentEnterSettings = actualTime;
        }
    }

    public void sendFirstmessage() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (mService == null && !mBound) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "waiting ..... for sendFirstmessage()");
                }
                sendData(true);
            }
        }.start();
    }
}
