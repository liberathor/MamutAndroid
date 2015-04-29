package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.co.widetech.serial_port_core.models.DeviceStatus;
import com.co.widetech.serial_port_core.service.TransportDataService;
import com.co.widetech.serial_port_core.tools.Utils;

import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.utils.AlertBuilder;
import co.com.widetech.mamut.android.utils.MessageBuilder;


public class IngresoActivity extends BinderServiceActivity {
    private static final String TAG = "IngresoActivity";
    private PlaceholderFragment mFragment;
    private Boolean mAccessTimeLogin;
    private BroadcastReceiver receiverLoginActivity = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                String nameUser = intent.getStringExtra("Name");
                String codeUser = intent.getStringExtra("Code");
                if (true) {
                    sharedStatusApp.setsPrefNameUser(nameUser);
                    Toast.makeText(IngresoActivity.this, nameUser, Toast.LENGTH_LONG).show();
                    if (codeUser.equalsIgnoreCase("1")) {
                        mAccessTimeLogin = false;
                        new DeviceStatus(IngresoActivity.this).setStatusLogin(true);
                        mFragment.mProgressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(IngresoActivity.this, MainActivity.class));
                        IngresoActivity.this.finish();
                    } else {
                        AlertBuilder.buildGenericAlert(IngresoActivity.this, "Error", "Contrasena incorrecta", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }, false);
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "error");
            }
        }
    };
    private MyCountSendStartApp counterStartApp;
    private ProgressDialog progressDialogLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_ingreso);
        if (savedInstanceState == null) {
            mFragment = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
        startService(new Intent(IngresoActivity.this, TransportDataService.class));
        resumeLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ingreso, menu);
        return true;
    }

    private void resumeLogin() {
        DeviceStatus deviceStatus = new DeviceStatus(getApplicationContext());
        if (deviceStatus.getStatusLogin().getBoolean("statusLogin", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isValid() {
        String data = mFragment.getCodigoAcceso();
        if (data != null) {
            if (data.length() > 0) {
                sharedStatusApp.setCodeUser(data);
                return true;
            }
        }
        return false;
    }

    @Override
    protected String buildData() {
        return new MessageBuilder(this).buildMessageToLogin();
    }

    @Override
    protected void onResume() {
        registerReceiver(receiverLoginActivity, new IntentFilter("LoginSystem"));
        super.onResume();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiverLoginActivity);
        super.onStop();
    }

    private long timeReporter() {
        MessageBuilder builder = new MessageBuilder(this);
        long timer = Utils.timerApplication(builder.typeUnity(this));
        long tr = timer * 1000;
        return tr;
    }

    MyCountSendStartApp getCounter() {
        return new MyCountSendStartApp(IngresoActivity.this.timeReporter(), 1000);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        protected ProgressBar mProgressBar;
        private Button mButton;
        private EditText mTextView;
        private ImageView mImageViewLogo;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Activity activity = getActivity();
            mButton = (Button) activity.findViewById(R.id.buttonIngreso);
            mTextView = (EditText) activity.findViewById(R.id.editTextCodigoIngreso);
            mProgressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
            mImageViewLogo = (ImageView) activity.findViewById(R.id.imageViewLogo);
            mButton.setOnClickListener(this);
            mImageViewLogo.setOnClickListener(this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ingreso, container, false);
            return rootView;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            IngresoActivity parentActivity = (IngresoActivity) getActivity();
            switch (id) {
                case R.id.buttonIngreso:
                    try {
                        parentActivity.mAccessTimeLogin = true;
                        if (parentActivity.sendData(false)) {
                            parentActivity.progressDialogLogin = ProgressDialog.show(parentActivity, "Por Favor Espere",
                                    "Validando el Acceso", true);
                            mProgressBar.setVisibility(View.VISIBLE);
                            parentActivity.counterStartApp = parentActivity.getCounter();
                            parentActivity.counterStartApp.start();
                        }
                    } catch (IllegalStateException e) {
                        AlertBuilder.buildGenericAlert(
                                parentActivity,
                                "Configure el Puerto Serial",
                                "Comuniquese con el Administrador para la configuracion",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        mTextView.setText("");
                                    }
                                }, false).show();
                        e.printStackTrace();
                    }
                    break;
                case R.id.imageViewLogo:
                    parentActivity.intentToEnterSettings();
                    break;
                default:
                    break;
            }
        }

        public void setProgress(Boolean isProgress) {
            mProgressBar.setVisibility(isProgress ? View.INVISIBLE : View.VISIBLE);
        }

        public String getCodigoAcceso() {
            return mTextView.getText().toString();
        }
    }

    private class MyCountSendStartApp extends CountDownTimer {

        private MyCountSendStartApp(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            if (mAccessTimeLogin) {
                mFragment.setProgress(false);
                progressDialogLogin.dismiss();
                Toast.makeText(IngresoActivity.this,
                        getResources().getString(R.string.message_login_time_out),
                        Toast.LENGTH_LONG).show();
                mAccessTimeLogin = false;
            }
        }
    }
}
