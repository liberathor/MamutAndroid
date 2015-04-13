package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;
import utils.MessageBuilder;

public class InicioViajeActivity extends BinderServiceActivity implements InfoInicioViajeFragment.OnFragmentInteractionListener {
    private static final String LOG = "InicioViajeActivity";
    private Fragment mFragment;
    private StatusActionToSend statusActionToSend = StatusActionToSend.SEND_DATA_INICIE_VIAJE;
    private String mNumeroManifiesto;
    private String mNumeroTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_viaje);
        if (mFragment == null) {
            mFragment = new PlaceholderFragment();
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
        sendData(true);
    }

    private void addFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            mFragment = fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio_viaje, menu);
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected boolean isValid() {
        boolean isValid = false;
        switch (statusActionToSend) {
            case SEND_DATA_INICIE_VIAJE:
                isValid = true;
                break;
            case SEND_DATA_REPORTAR_DATOS_VIAJE:
                if (!mNumeroTrailer.isEmpty() && !mNumeroManifiesto.isEmpty()) {
                    isValid = true;
                    break;
                } else {
                    String errorManifiesto = null;
                    String errorTrailer = null;
                    if (mNumeroManifiesto.isEmpty()) {
                        errorManifiesto = "Ingrese el numero de manifiesto";
                    }
                    if (mNumeroTrailer.isEmpty()) {
                        errorTrailer = "Ingrese el numero de trailer";
                    }
                    ((InfoInicioViajeFragment) mFragment).setValidationErrors(errorManifiesto, errorTrailer);
                }
                break;
            default:
                break;
        }
        return isValid;
    }

    @Override
    protected String buildData() {
        String data = "";
        switch (statusActionToSend) {
            case SEND_DATA_INICIE_VIAJE:
                data = new MessageBuilder(this).buildMessageInicieMiViaje();
                break;
            case SEND_DATA_REPORTAR_DATOS_VIAJE:
                data = new MessageBuilder(this).buildMessageDatosDeViaje(mNumeroManifiesto, mNumeroTrailer);
                break;
            default:
                break;
        }
        return data;
    }

    public boolean sendData(String manifiesto, String trailer) {
        mNumeroManifiesto = manifiesto;
        mNumeroTrailer = trailer;
        statusActionToSend = StatusActionToSend.SEND_DATA_REPORTAR_DATOS_VIAJE;
        return sendData(true);
    }

    enum StatusActionToSend {
        SEND_DATA_INICIE_VIAJE,
        SEND_DATA_REPORTAR_DATOS_VIAJE
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        Button mButtonInicioViaje;
        Button mButtonChat;
        Button mButtonOpciones;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Activity activity = getActivity();
            mButtonInicioViaje = (Button) activity.findViewById(R.id.ButtonInicioViaje);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonOpciones = (Button) activity.findViewById(R.id.ButtonOpciones);
            mButtonInicioViaje.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
            mButtonOpciones.setOnClickListener(this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_inicio_viaje, container, false);
            return rootView;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Fragment fragment = null;
            switch (id) {
                case R.id.ButtonInicioViaje:
                    fragment = new InfoInicioViajeFragment();
                    break;
                case R.id.ButtonChat:
                    getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
                    break;
                case R.id.ButtonOpciones:
                    break;
                default:
                    break;
            }
            ((InicioViajeActivity) getActivity()).addFragment(fragment);
        }
    }
}
