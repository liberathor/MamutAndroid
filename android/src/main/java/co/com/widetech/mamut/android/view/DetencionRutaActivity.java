package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.utils.Config;
import co.com.widetech.mamut.android.utils.MessageBuilder;

public class DetencionRutaActivity extends BinderServiceActivity implements EnDetencionRutaFragment.OnFragmentInteractionListener {
    private static boolean isViajeVacio;
    private Fragment mFragment;
    private StatusDetencionRuta mStatusDetencionRuta = StatusDetencionRuta.SEND_DATA_DETENCION_RUTA;
    private String mStringMotivoDetencionRuta = "";

    public static boolean isViajeVacio() {
        return isViajeVacio;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detencion_ruta);
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
        getMenuInflater().inflate(R.menu.menu_detencion_ruta, menu);
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

    void addFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            mFragment = fragment;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        uri = Uri.parse(EnDetencionRutaFragment.class.getCanonicalName());
        if (uri.compareTo(uri) == 0) {
            sendData(StatusDetencionRuta.SEND_DATA_REINICIAR_VIAJE);
            finish();
        }
    }

    @Override
    protected boolean isValid() {
        boolean isValid = false;
        switch (mStatusDetencionRuta) {
            case SEND_DATA_PAUSA_ACTIVA:
                isValid = true;
                break;
            case SEND_DATA_PERNOCTACION:
                isValid = true;
                break;
            case SEND_DATA_ALIMENTACION:
                isValid = true;
                break;
            case SEND_DATA_OTRO_MOTIVO:
                if (!mStringMotivoDetencionRuta.isEmpty()) {
                    isValid = true;
                } else {
                    ((OtroMotivoDetencionFragment) mFragment).setErrorsFields("Por favor ingrese el motivo");
                }
                break;
            case SEND_DATA_REINICIAR_VIAJE:
                isValid = true;
            default:
                break;
        }
        return isValid;
    }

    @Override
    protected String buildData() {
        String data = null;
        if (isViajeVacio) {
            switch (mStatusDetencionRuta) {
                case SEND_DATA_PAUSA_ACTIVA:
                    data = new MessageBuilder(this).buildMessageDetencionEnRutaMotivoPausa(Config.valuesDetencionRutaViajeVacio.TYPE_ACTION_PAUSA_ACTIVA);
                    break;
                case SEND_DATA_PERNOCTACION:
                    data = new MessageBuilder(this).buildMessageDetencionEnRutaMotivoPausa(Config.valuesDetencionRutaViajeVacio.TYPE_ACTION_PERNOCTACION);
                    break;
                case SEND_DATA_ALIMENTACION:
                    data = new MessageBuilder(this).buildMessageDetencionEnRutaMotivoPausa(Config.valuesDetencionRutaViajeVacio.TYPE_ACTION_ALIMENTACION);
                    break;
                case SEND_DATA_OTRO_MOTIVO:
                    data = new MessageBuilder(this).buildMessageDetencionEnRutaOtroMotivoViajeVacio(mStringMotivoDetencionRuta);
                    break;
                case SEND_DATA_REINICIAR_VIAJE:
                    data = new MessageBuilder(this).buildMessageReinicarViajeVacio();
            }
        } else {
            switch (mStatusDetencionRuta) {
                case SEND_DATA_PAUSA_ACTIVA:
                    data = new MessageBuilder(this).buildMessageDetencionEnRutaMotivoPausa(Config.valuesDetencionRuta.TYPE_ACTION_PAUSA_ACTIVA);
                    break;
                case SEND_DATA_PERNOCTACION:
                    data = new MessageBuilder(this).buildMessageDetencionEnRutaMotivoPausa(Config.valuesDetencionRuta.TYPE_ACTION_PERNOCTACION);
                    break;
                case SEND_DATA_ALIMENTACION:
                    data = new MessageBuilder(this).buildMessageDetencionEnRutaMotivoPausa(Config.valuesDetencionRuta.TYPE_ACTION_ALIMENTACION);
                    break;
                case SEND_DATA_OTRO_MOTIVO:
                    data = new MessageBuilder(this).buildMessageDetencionEnRutaOtroMotivo(mStringMotivoDetencionRuta);
                    break;
                case SEND_DATA_REINICIAR_VIAJE:
                    data = new MessageBuilder(this).buildMessageReinicarViaje();
            }
        }
        return data;
    }

    public void sendData(StatusDetencionRuta status) {
        mStatusDetencionRuta = status;
        sendData(true);
    }

    public boolean sendDataOtroMotivo(String motivo) {
        mStringMotivoDetencionRuta = motivo;
        mStatusDetencionRuta = StatusDetencionRuta.SEND_DATA_OTRO_MOTIVO;
        return sendData(true);
    }

    enum StatusDetencionRuta {
        SEND_DATA_DETENCION_RUTA,
        SEND_DATA_PAUSA_ACTIVA,
        SEND_DATA_PERNOCTACION,
        SEND_DATA_ALIMENTACION,
        SEND_DATA_OTRO_MOTIVO,
        SEND_DATA_REINICIAR_VIAJE
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        Button mButtonPausaActiva;
        Button mButtonPernoctacion;
        Button mButtonAlimentacion;
        Button mButtonOtroMotivo;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detencion_ruta, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mButtonPausaActiva = (Button) activity.findViewById(R.id.ButtonPausaActiva);
            mButtonPernoctacion = (Button) activity.findViewById(R.id.ButtonPernoctacion);
            mButtonAlimentacion = (Button) activity.findViewById(R.id.ButtonAlimentacion);
            mButtonOtroMotivo = (Button) activity.findViewById(R.id.ButtonOtro);
            mButtonPausaActiva.setOnClickListener(this);
            mButtonPernoctacion.setOnClickListener(this);
            mButtonAlimentacion.setOnClickListener(this);
            mButtonAlimentacion.setOnClickListener(this);
            mButtonOtroMotivo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            DetencionRutaActivity parentActivity = ((DetencionRutaActivity) getActivity());
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            switch (id) {
                case R.id.ButtonPausaActiva:
                    parentActivity.sendData(StatusDetencionRuta.SEND_DATA_PAUSA_ACTIVA);
                    fragment = new EnDetencionRutaFragment();
                    bundle.putString(getString(R.string.fragment_title), getString(R.string.title_pausa_activa));
                    break;
                case R.id.ButtonPernoctacion:
                    parentActivity.sendData(StatusDetencionRuta.SEND_DATA_PERNOCTACION);
                    fragment = new EnDetencionRutaFragment();
                    bundle.putString(getString(R.string.fragment_title), getString(R.string.title_pernoctacion));
                    break;
                case R.id.ButtonAlimentacion:
                    parentActivity.sendData(StatusDetencionRuta.SEND_DATA_ALIMENTACION);
                    fragment = new EnDetencionRutaFragment();
                    bundle.putString(getString(R.string.fragment_title), getString(R.string.title_alimentacion));
                    break;
                case R.id.ButtonOtro:
                    fragment = new OtroMotivoDetencionFragment();
                    bundle.putString(getString(R.string.fragment_title), getString(R.string.title_otro_motivo));
                    break;
                default:
                    break;
            }
            fragment.setArguments(bundle);
            ((DetencionRutaActivity) getActivity()).addFragment(fragment);
        }
    }
}
