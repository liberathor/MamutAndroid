package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;

public class DetencionRutaActivity extends BinderServiceActivity implements EnDetencionRutaFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detencion_ruta);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        uri = Uri.parse(EnDetencionRutaFragment.class.getCanonicalName());
        if (uri.compareTo(uri) == 0) {
            finish();
        }
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
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            switch (id) {
                case R.id.ButtonPausaActiva:
                    fragment = new EnDetencionRutaFragment();
                    bundle.putString(getString(R.string.fragment_title), getString(R.string.title_pausa_activa));
                    break;
                case R.id.ButtonPernoctacion:
                    fragment = new EnDetencionRutaFragment();
                    bundle.putString(getString(R.string.fragment_title), getString(R.string.title_pernoctacion));
                    break;
                case R.id.ButtonAlimentacion:
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
