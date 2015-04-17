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

public class InfoViajeVacioActivity extends BinderServiceActivity {
    private static boolean isViajeVacio;
    private PlaceholderFragment mFragment;
    private StatusInfoViaje mStatusInfoViaje = StatusInfoViaje.SEND_DATA_VIAJE_VACIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viaje);
        if (savedInstanceState == null) {
            mFragment = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
        getExtras();
        sendData(true);
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isViajeVacio = extras.getBoolean("EXTRA_VIAJE_VACIO", false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_viaje, menu);
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
        PlaceholderFragment infoViajeFragment = mFragment;
        switch (mStatusInfoViaje) {
            case SEND_DATA_VIAJE_VACIO:
                isValid = true;
                break;
            case SEND_DATA_INFORMACION_VIAJE:
                if (!infoViajeFragment.getMessageDestino().isEmpty() && !infoViajeFragment.getMessageServicio().isEmpty() && !infoViajeFragment.getMessageTrailer().isEmpty()) {
                    isValid = true;
                } else {
                    String errorDestino = null;
                    String errorServicio = null;
                    String errorTrailer = null;
                    if (infoViajeFragment.getMessageDestino().isEmpty()) {
                        errorDestino = "Por favor ingrese el destino";
                    }
                    if (infoViajeFragment.getMessageServicio().isEmpty()) {
                        errorServicio = "Por favor ingrese el servicio";
                    }
                    if (infoViajeFragment.getMessageTrailer().isEmpty()) {
                        errorTrailer = "Por favor ingrese el trailer";
                    }
                    infoViajeFragment.setMessagesValidationErrors(errorDestino, errorServicio, errorTrailer);
                }
                break;
            case SEND_DATA_INFORMACION_VIAJE_VACIO:
                if (!infoViajeFragment.getMessageDestino().isEmpty() && !infoViajeFragment.getMessageServicio().isEmpty() && !infoViajeFragment.getMessageTrailer().isEmpty()) {
                    isValid = true;
                } else {
                    String errorDestino = null;
                    String errorServicio = null;
                    String errorTrailer = null;
                    if (infoViajeFragment.getMessageDestino().isEmpty()) {
                        errorDestino = "Por favor ingrese el destino";
                    }
                    if (infoViajeFragment.getMessageServicio().isEmpty()) {
                        errorServicio = "Por favor ingrese el servicio";
                    }
                    if (infoViajeFragment.getMessageTrailer().isEmpty()) {
                        errorTrailer = "Por favor ingrese el trailer";
                    }
                    infoViajeFragment.setMessagesValidationErrors(errorDestino, errorServicio, errorTrailer);
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
        String ciudad = "";
        String servicio = "";
        String trailer = "";
        switch (mStatusInfoViaje) {
            case SEND_DATA_VIAJE_VACIO:
                data = new MessageBuilder(this).buildMessageMainButton(Config.buttonStrings.TYPE_BUTTON_VIAJE_VACIO);
                break;
            case SEND_DATA_INFORMACION_VIAJE:
                ciudad = mFragment.getMessageDestino();
                servicio = mFragment.getMessageServicio();
                trailer = mFragment.getMessageTrailer();
                data = new MessageBuilder(this).buildMessageViajeVacioInfoViaje(ciudad, servicio, trailer);
                break;
            case SEND_DATA_INFORMACION_VIAJE_VACIO:
                ciudad = mFragment.getMessageDestino();
                servicio = mFragment.getMessageServicio();
                trailer = mFragment.getMessageTrailer();
                data = new MessageBuilder(this).buildMessageViajeVacioInformacionDeViaje(ciudad, servicio, trailer);
            default:
                break;
        }
        return data;
    }

    enum StatusInfoViaje {
        SEND_DATA_VIAJE_VACIO,
        SEND_DATA_INFORMACION_VIAJE,
        SEND_DATA_INFORMACION_VIAJE_VACIO
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        Button mButtonEnviarEstadoViaje;
        Button mButtonChat;
        Button mButtonOpciones;
        EditText mEditTextDestino;
        EditText mEditTextServicio;
        EditText mEditTextTrailer;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_info_viaje, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mButtonEnviarEstadoViaje = (Button) activity.findViewById(R.id.ButtonEnviarInfoViaje);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonOpciones = (Button) activity.findViewById(R.id.ButtonOptions);
            mButtonEnviarEstadoViaje.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
            mButtonOpciones.setOnClickListener(this);
            mEditTextDestino = (EditText) activity.findViewById(R.id.editTextDestino);
            mEditTextServicio = (EditText) activity.findViewById(R.id.editTextServicio);
            mEditTextTrailer = (EditText) activity.findViewById(R.id.editTextTrailer);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            InfoViajeVacioActivity parentFragment = ((InfoViajeVacioActivity) getActivity());
            Class activity = null;
            switch (id) {
                case R.id.ButtonEnviarInfoViaje:
                    if (InfoViajeVacioActivity.isViajeVacio) {
                        parentFragment.mStatusInfoViaje = StatusInfoViaje.SEND_DATA_INFORMACION_VIAJE_VACIO;
                    } else {
                        parentFragment.mStatusInfoViaje = StatusInfoViaje.SEND_DATA_INFORMACION_VIAJE;
                    }
                    if (parentFragment.sendData(true)) {
                        setMessagesValidationErrors(null, null, null);
                        activity = EstadoViajeActivity.class;
                    }
                    break;
                case R.id.ButtonChat:
                    activity = ChatActivity.class;
                    break;
                case R.id.ButtonOpciones:
                    break;
                default:
                    break;
            }
            if (activity != null) {
                Intent intent = new Intent(getActivity(), activity);
                intent.putExtra("EXTRA_VIAJE_VACIO", isViajeVacio);
                getActivity().startActivity(intent);
            }
        }

        public String getMessageDestino() {
            return mEditTextDestino.getText().toString();
        }

        public String getMessageServicio() {
            return mEditTextServicio.getText().toString();
        }

        public String getMessageTrailer() {
            return mEditTextTrailer.getText().toString();
        }

        public void setMessagesValidationErrors(String destinoError, String servicioError, String trailerError) {
            mEditTextDestino.setError(destinoError);
            mEditTextServicio.setError(servicioError);
            mEditTextTrailer.setError(trailerError);
        }
    }
}
