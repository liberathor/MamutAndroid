package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.utils.Config;
import co.com.widetech.mamut.android.utils.MessageBuilder;


public class InicioTurnoActivity extends BinderServiceActivity implements EnTurnoFragment.OnFragmentInteractionListener {
    public static final String SCHEME = ContentResolver.SCHEME_CONTENT + "://";
    public static final String AUTHORITY = "co.com.widetech.mamut.android.turnos";
    private static final int INICIAR_TURNO = 0;
    private static final int FINALIZAR_TURNO = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static String BASE_URI = SCHEME + AUTHORITY;

    static {
        sUriMatcher.addURI(AUTHORITY, "iniciar_turno", INICIAR_TURNO);
        sUriMatcher.addURI(AUTHORITY, "finalizar_turno", FINALIZAR_TURNO);
    }

    private StateInicioTurno mStateInicioTurno = StateInicioTurno.SEND_DATA_STATE_PROYECTOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_turno);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InicioTurnoActivity.this.startActivity(new Intent(InicioTurnoActivity.this, SolicitudTanqueoActivity.class));
            }
        });
        sendData(true);
    }

    void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio_turno, menu);
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
        int match = sUriMatcher.match(uri);
        switch (match) {
            case INICIAR_TURNO:
                break;
            case FINALIZAR_TURNO:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean isValid() {
        return true;
    }

    @Override
    protected String buildData() {
        String data = null;
        switch (mStateInicioTurno) {
            case SEND_DATA_STATE_PROYECTOS:
                data = new MessageBuilder(this).buildMessageMainButton(Config.buttonStrings.TYPE_BUTTON_PROYECTOS);
                break;
            case SEND_DATA_STATE_INICIAR_TURNO:
                data = new MessageBuilder(this).buildMessageInicializarTurno();
                break;
            case SEND_DATA_STATE_FINALIZAR_TURNO:
                data = new MessageBuilder(this).buildMessageFinalizarTurno();
                break;
            default:
                break;
        }
        return data;
    }

    public boolean sendData(StateInicioTurno state) {
        mStateInicioTurno = state;
        return sendData(true);
    }

    enum StateInicioTurno {
        SEND_DATA_STATE_PROYECTOS,
        SEND_DATA_STATE_INICIAR_TURNO,
        SEND_DATA_STATE_FINALIZAR_TURNO
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        Button mButtonInicarTurno;
        Button mButtonOpciones;
        Button mButtonChat;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_inicio_turno, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mButtonInicarTurno = (Button) activity.findViewById(R.id.ButtonIniciarTurno);
            mButtonOpciones = (Button) activity.findViewById(R.id.ButtonOpciones);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonInicarTurno.setOnClickListener(this);
            mButtonOpciones.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            InicioTurnoActivity parentActivity = ((InicioTurnoActivity) getActivity());
            Fragment fragment = null;
            String tag = null;
            switch (id) {
                case R.id.ButtonIniciarTurno:
                    if (parentActivity.sendData(StateInicioTurno.SEND_DATA_STATE_INICIAR_TURNO)) {
                        fragment = new EnTurnoFragment();
                        tag = EnTurnoFragment.TAG;
                    }
                    break;
                case R.id.ButtonOpciones:
                    break;
                case R.id.ButtonChat:
                    getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
                    break;
                default:
                    break;
            }
            if (fragment != null) {
                ((InicioTurnoActivity) getActivity()).replaceFragment(fragment, tag);
            }
        }
    }
}
