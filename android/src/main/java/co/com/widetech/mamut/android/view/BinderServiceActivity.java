package co.com.widetech.mamut.android.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.co.widetech.serial_port_core.service.TransportDataService;

/**
 * Created by wtjramirez on 3/30/15.
 */
public abstract class BinderServiceActivity extends ActionBarActivity {
    private static final String TAG = "BinderServiceActivity";
    TransportDataService mService;
    boolean mBound;
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
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
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
}
