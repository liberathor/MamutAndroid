package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;

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

    @Override
    protected boolean isValid() {
        return false;
    }

    @Override
    protected String buildData() {
        return null;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
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
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Fragment fragment = null;
            switch (id) {
                case R.id.ButtonSolicitudMantenimiento:
                    fragment = new InicioMantenimientoFragment();
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
    }
}
