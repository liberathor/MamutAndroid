package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.utils.Config;
import co.com.widetech.mamut.android.utils.MessageBuilder;

public class SolicitudMantenimientoActivity extends BinderServiceActivity implements InicioMantenimientoFragment.OnFragmentInteractionListener {
    public static final String SCHEME = ContentResolver.SCHEME_CONTENT + "://";
    public static final String AUTHORITY = "co.com.widetech.mamut.android.mantenimiento";
    private static final int INICIAR_MANTENIMIENTO = 0;
    private static final int FINALIZAR_MANTENIMIENTO = 1;
    private static final int INGRESAR_GALONES = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static String BASE_URI = SCHEME + AUTHORITY;

    static {
        sUriMatcher.addURI(AUTHORITY, "iniciar_mantenimiento", INICIAR_MANTENIMIENTO);
        sUriMatcher.addURI(AUTHORITY, "finalizar_mantenimiento", FINALIZAR_MANTENIMIENTO);
        sUriMatcher.addURI(AUTHORITY, "ingresar_galones", INGRESAR_GALONES);
    }

    private Fragment mFragment;
    private StatusSolicitudMantenimiento mStatusSolicitudMantenimiento = StatusSolicitudMantenimiento.SEND_SOLICITAR_MANTENIMIENTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_mantenimiento);
        if (savedInstanceState == null) {
            mFragment = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solicito_mantenimiento, menu);
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

    private void addFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            mFragment = fragment;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        int match = sUriMatcher.match(uri);
        Fragment fragment = null;
        switch (match) {
            case INICIAR_MANTENIMIENTO:
                fragment = new InicioMantenimientoFragment();
                break;
            case FINALIZAR_MANTENIMIENTO:
                fragment = new FinalizarMantenimientoFragment();
                break;
            case INGRESAR_GALONES:
                fragment = new GalonesFragment();
            default:
                break;
        }

        if (fragment != null) {
            addFragment(fragment);
        }
    }

    public boolean sendData(StatusSolicitudMantenimiento status) {
        mStatusSolicitudMantenimiento = status;
        return sendData(true);
    }

    @Override
    protected boolean isValid() {
        boolean isValid = false;
        switch (mStatusSolicitudMantenimiento) {
            case SEND_SOLICITAR_MANTENIMIENTO:
                PlaceholderFragment fragment = ((PlaceholderFragment) mFragment);
                if (!fragment.getMantenimiento().isEmpty()) {
                    isValid = true;
                } else {
                    fragment.setmValidationErrors("Ingrese la descripcion del mantenimiento");
                }
                break;
            case SEND_INICIAR_MANTENIMIENTO:
                isValid = true;
                break;
            case SEND_FINALIZAR_MANTENIMIENTO:
                isValid = true;
                break;
            case SEND_ENVIAR_GALONES:
                GalonesFragment galonesFragment = ((GalonesFragment) mFragment);
                try {
                    String galonesString = galonesFragment.getGalones();
                    if (!galonesString.isEmpty()) {
                        int galones = Integer.parseInt(galonesString);
                        isValid = true;
                    } else {
                        galonesFragment.setValidationError("Ingrese un valor");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    galonesFragment.setValidationError("Ingrese un valor");
                }
            default:
                break;
        }
        return isValid;
    }

    @Override
    protected String buildData() {
        String data = null;
        switch (mStatusSolicitudMantenimiento) {
            case SEND_SOLICITAR_MANTENIMIENTO:
                PlaceholderFragment fragment = ((PlaceholderFragment) mFragment);
                data = new MessageBuilder(this).buildMessageSolicitudMantenimiento(fragment.getMantenimiento());
                break;
            case SEND_INICIAR_MANTENIMIENTO:
                data = new MessageBuilder(this).buildMessageReportarEstadoMantenimiento(Config.valuesMantenimiento.TYPE_ACTION_INICIO_MANTENIMIENTO);
                break;
            case SEND_FINALIZAR_MANTENIMIENTO:
                data = new MessageBuilder(this).buildMessageReportarEstadoMantenimiento(Config.valuesMantenimiento.TYPE_ACTION_FIN_MANTENIMIENTO);
                break;
            case SEND_ENVIAR_GALONES:
                GalonesFragment galonesFragment = ((GalonesFragment) mFragment);
                int galones = Integer.parseInt(galonesFragment.getGalones());
                data = new MessageBuilder(this).buildMessageReportarGalonesMantenimiento(galones);
            default:
                break;
        }
        return data;
    }

    enum StatusSolicitudMantenimiento {
        SEND_SOLICITAR_MANTENIMIENTO,
        SEND_INICIAR_MANTENIMIENTO,
        SEND_ENVIAR_GALONES,
        SEND_FINALIZAR_MANTENIMIENTO
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private EditText mEditTextMensajeMantenimiento;
        private Button mButtonSolicitudMantenimiento;
        private Button mButtonChat;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_solicitud_mantenimiento, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mButtonSolicitudMantenimiento = (Button) activity.findViewById(R.id.ButtonSolicitudMantenimiento);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonSolicitudMantenimiento.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
            mEditTextMensajeMantenimiento = (EditText) activity.findViewById(R.id.editTextMantenimiento);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            SolicitudMantenimientoActivity parentActivity = ((SolicitudMantenimientoActivity) getActivity());
            Fragment fragment = null;
            switch (id) {
                case R.id.ButtonSolicitudMantenimiento:
                    parentActivity.mStatusSolicitudMantenimiento = StatusSolicitudMantenimiento.SEND_SOLICITAR_MANTENIMIENTO;
                    if (parentActivity.sendData(true)) {
                        setmValidationErrors(null);
                        fragment = new InicioMantenimientoFragment();
                    }
                    break;
                case R.id.ButtonChat:
                    break;
                default:
                    break;
            }
            if (fragment != null) {
                ((SolicitudMantenimientoActivity) getActivity()).addFragment(fragment);
            }
        }

        public String getMantenimiento() {
            return mEditTextMensajeMantenimiento.getText().toString();
        }

        public void setmValidationErrors(String errorMensajeMantenimiento) {
            mEditTextMensajeMantenimiento.setError(errorMensajeMantenimiento);
        }
    }
}
