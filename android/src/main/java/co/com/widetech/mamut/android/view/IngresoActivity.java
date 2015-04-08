package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import co.com.widetech.mamut.android.R;
import com.co.widetech.serial_port_core.models.DeviceStatus;
import utils.AlertBuilder;
import utils.MessageBuilder;


public class IngresoActivity extends BinderServiceActivity {
    private static final String TAG = "IngresoActivity";
    private PlaceholderFragment mFragment;

    private BroadcastReceiver receiverLoginActivity = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                String nameUser = intent.getStringExtra("Name");
                String codeUser = intent.getStringExtra("Code");
                if (true) {
                    sharedStatusApp.setsPrefNameUser(nameUser);
                    if (codeUser.equalsIgnoreCase("1")) {
                        new DeviceStatus(IngresoActivity.this).setStatusLogin(true);
                        mFragment.mProgressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(IngresoActivity.this, MainActivity.class));
                        IngresoActivity.this.finish();
                    } else {
                        AlertBuilder.buildGenericAlert(IngresoActivity.this, "Error", "Contrasena incorrecta", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "error");
            }
        }
    };

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        protected ProgressBar mProgressBar;
        private Button mButton;
        private EditText mTextView;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Activity activity = getActivity();
            mButton = (Button) activity.findViewById(R.id.buttonIngreso);
            mTextView = (EditText) activity.findViewById(R.id.editTextCodigoIngreso);
            mProgressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
            mButton.setOnClickListener(this);
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
            IngresoActivity activity = (IngresoActivity) getActivity();
            try {
                activity.setWhaitAck(true);
                if (activity.sendData()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            } catch (IllegalStateException e) {
                AlertBuilder.buildGenericAlert(
                        activity,
                        "Configure el Puerto Serial",
                        "Comuniquese con el Administrador para la configuracon",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTextView.setText("");
                            }
                        }).show();
                e.printStackTrace();
            }
        }

        public String getCodigoAcceso() {
            return mTextView.getText().toString();
        }
    }
}
