package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import co.com.widetech.mamut.android.R;
import com.co.widetech.serial_port_core.models.DeviceStatus;
import utils.AlertBuilder;


public class IngresoActivity extends BinderServiceActivity {
    private static final String TAG = "IngresoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_ingreso);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ingreso, menu);
        return true;
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
    protected boolean isValid(String data) {
        if (data != null) {
            if (data.length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private Button mButton;
        private EditText mTextView;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Activity activity = getActivity();
            mButton = (Button) activity.findViewById(R.id.buttonIngreso);
            mTextView = (EditText) activity.findViewById(R.id.editTextCodigoIngreso);
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
                if (activity.sendData(mTextView.getText().toString())) {
                    new DeviceStatus(getActivity()).setStatusSelectSerialPort(true);
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
            } catch (IllegalStateException e) {
                AlertBuilder.buildAlertNotConfiguredDevice(
                        activity,
                        "Configure el Puerto Serial",
                        "Comuniquese con el Administrador para la configuracon",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO: clear data or do something to fix it
                                //mEditPass.setText("");
                            }
                        }).show();
                e.printStackTrace();
            }
        }
    }
}
