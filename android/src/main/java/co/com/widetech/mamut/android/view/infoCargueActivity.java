package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.utils.MessageBuilder;

public class infoCargueActivity extends BinderServiceActivity {
    private infoCargueActivity.PlaceholderFragment mFragment;
    private EstatusActionsToSend statusToSend = EstatusActionsToSend.SEND_OPERACION_NAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cargue);
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
        getMenuInflater().inflate(R.menu.menu_info_cargue, menu);
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
        switch (statusToSend) {
            case SEND_OPERACION_NAL:
                return false;
            case SEND_INFO_VIAJE:
                String ciudadDestino = mFragment.getCiudadDestino();
                String carga = mFragment.getmCarga();
                String errorCarga = null;
                String errorDestino = null;
                if (!ciudadDestino.isEmpty() && !carga.isEmpty()) {
                    return true;
                } else {
                    if (ciudadDestino.isEmpty()) {
                        errorDestino = "Por favor llene el destino";
                    }
                    if (carga.isEmpty()) {
                        errorCarga = "Por favor llene la carga";
                    }
                    mFragment.setError(errorCarga, errorDestino);
                    return false;
                }
        }
        return false;
    }

    @Override
    protected String buildData() {
        String data = null;
        switch (statusToSend) {
            case SEND_OPERACION_NAL:
                data = new MessageBuilder(this).buildMessageLlegueACargar();
                break;
            case SEND_INFO_VIAJE:
                data = new MessageBuilder(this).buildMessageInformacionViaje(mFragment.getCiudadDestino(), mFragment.getmCarga());
                break;
        }
        return data;
    }

    public enum EstatusActionsToSend {
        SEND_OPERACION_NAL,
        SEND_INFO_VIAJE
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private Button mButtonChat;
        private Button mButtonFinalizarViaje;
        private Button mButtonOpciones;
        private EditText mEditTextCiudadDestino;
        private EditText mEditTextCarga;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Activity activity = getActivity();
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonFinalizarViaje = (Button) activity.findViewById(R.id.ButtonOptions);
            mButtonOpciones = (Button) activity.findViewById(R.id.ButtonOpciones);
            mEditTextCiudadDestino = (EditText) activity.findViewById(R.id.editTextCiudad);
            mEditTextCarga = (EditText) activity.findViewById(R.id.editTextCarga);
            mButtonChat.setOnClickListener(this);
            mButtonFinalizarViaje.setOnClickListener(this);
            mButtonOpciones.setOnClickListener(this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_info_cargue, container, false);
            return rootView;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Class activity = null;
            switch (id) {
                case R.id.ButtonChat:
                    activity = ChatActivity.class;
                    break;
                case R.id.ButtonOptions:
                    infoCargueActivity parentActivity = ((infoCargueActivity) getActivity());
                    parentActivity.statusToSend = EstatusActionsToSend.SEND_INFO_VIAJE;
                    if (parentActivity.sendData(true)) {
                        activity = InicioViajeActivity.class;
                    }
                    break;
                case R.id.ButtonOpciones:
                    break;
                default:
                    break;
            }
            if (activity != null) {
                getActivity().startActivity(new Intent(getActivity(), activity));
            }
        }

        public String getCiudadDestino() {
            return mEditTextCiudadDestino.getText().toString();
        }

        public String getmCarga() {
            return mEditTextCarga.getText().toString();
        }

        public void setError(String errorCarga, String errorCiudadDestino) {
            mEditTextCarga.setError(errorCarga);
            mEditTextCiudadDestino.setError(errorCiudadDestino);
        }
    }
}
