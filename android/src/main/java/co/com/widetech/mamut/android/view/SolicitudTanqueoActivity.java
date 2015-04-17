package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.utils.Config;
import co.com.widetech.mamut.android.utils.MessageBuilder;

public class SolicitudTanqueoActivity extends BinderServiceActivity {
    private Fragment mFragment;
    private StatusSolicitudTanqueo mStatusSolicitudTanqueo = StatusSolicitudTanqueo.SEND_DATA_START_SOLICITUD_TANQUEO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanqueo);
        if (savedInstanceState == null) {
            mFragment = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
        sendData(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tanqueo, menu);
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
    protected boolean isValid() {
        boolean isValid = false;
        switch (mStatusSolicitudTanqueo) {
            case SEND_DATA_START_SOLICITUD_TANQUEO:
                isValid = true;
                break;
            case SEND_DATA_ENVIAR_TANQUEO:
                PlaceholderFragment fragment = ((PlaceholderFragment) mFragment);
                if (!fragment.getNombreEds().isEmpty() && !fragment.getCantidadGalones().isEmpty()) {
                    isValid = true;
                } else {
                    String errorNombresEds = null;
                    String errorCantidadGalones = null;
                    if (fragment.getNombreEds().isEmpty()) {
                        errorNombresEds = "Ingrese un nombre valido";
                    }
                    if (fragment.getCantidadGalones().isEmpty()) {
                        errorCantidadGalones = "Ingrese una cantidad";
                    }
                    fragment.setValidationErrors(errorNombresEds, errorCantidadGalones);
                }
                break;
            default:
                break;
        }
        return isValid;
    }

    @Override
    protected String buildData() {
        String data = null;
        PlaceholderFragment fragment = ((PlaceholderFragment) mFragment);

        switch (mStatusSolicitudTanqueo) {
            case SEND_DATA_START_SOLICITUD_TANQUEO:
                data = new MessageBuilder(this).buildMessageMainButton(Config.buttonStrings.TYPE_BUTTON_TANQUEO);
                break;
            case SEND_DATA_ENVIAR_TANQUEO:
                data = new MessageBuilder(this).buildMessageTanqueo(fragment.getNombreEds(), Integer.parseInt(fragment.getCantidadGalones()));
                break;
            default:
                break;
        }
        return data;
    }

    enum StatusSolicitudTanqueo {
        SEND_DATA_START_SOLICITUD_TANQUEO,
        SEND_DATA_ENVIAR_TANQUEO
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private Button mButtonEnviarTanqueo;
        private Button mButtonChat;
        private EditText mEditTextNombreEds;
        private EditText mEditTextCantidadGalones;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_solicitud_tanqueo, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mButtonEnviarTanqueo = (Button) activity.findViewById(R.id.ButtonEnviarTanqueo);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonEnviarTanqueo.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
            mEditTextNombreEds = (EditText) activity.findViewById(R.id.editTextNombreEds);
            mEditTextCantidadGalones = (EditText) activity.findViewById(R.id.editTextCantidadGalones);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            SolicitudTanqueoActivity parentActivity = ((SolicitudTanqueoActivity) getActivity());
            Class activity = null;
            Intent intent = null;
            switch (id) {
                case R.id.ButtonEnviarTanqueo:
                    parentActivity.mStatusSolicitudTanqueo = StatusSolicitudTanqueo.SEND_DATA_ENVIAR_TANQUEO;
                    if (parentActivity.sendData(true)) {
                        activity = MainActivity.class;
                        intent = new Intent(getActivity(), activity);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                    break;
                case R.id.ButtonChat:
                    activity = ChatActivity.class;
                    intent = new Intent(getActivity(), activity);
                    break;
                default:
                    break;
            }
            if (activity != null) {
                getActivity().startActivity(intent);
            }
        }

        public String getNombreEds() {
            return mEditTextNombreEds.getText().toString();
        }

        public String getCantidadGalones() {
            return mEditTextCantidadGalones.getText().toString();
        }

        public void setValidationErrors(String errorNombresEds, String errorCantidadGalones) {
            mEditTextNombreEds.setError(errorNombresEds);
            mEditTextCantidadGalones.setError(errorCantidadGalones);
        }
    }
}
