package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;


public class MainActivity extends BinderServiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        return true;
    }

    @Override
    protected String buildData() {
        return null;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements Button.OnClickListener {
        private String TAG = "PlaceHolderFragment";
        private Button mButtonOpNal;
        private Button mButtonProyectos;
        private Button mButtonViajeVacio;
        private Button mButtonTanqueo;
        private Button mButtonChat;
        private Button mButtonMantenimiento;
        private Button mButtonSalida;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Activity activity = getActivity();
            mButtonOpNal = (Button) activity.findViewById(R.id.ButtonOpNal);
            mButtonProyectos = (Button) activity.findViewById(R.id.ButtonLlegadaDescargar);
            mButtonViajeVacio = (Button) activity.findViewById(R.id.ButtonViajeVacio);
            mButtonTanqueo = (Button) activity.findViewById(R.id.ButtonTanqueo);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonMantenimiento = (Button) activity.findViewById(R.id.ButtonMantenimiento);
            mButtonSalida = (Button) activity.findViewById(R.id.ButtonSalida);
            mButtonOpNal.setOnClickListener(this);
            mButtonProyectos.setOnClickListener(this);
            mButtonViajeVacio.setOnClickListener(this);
            mButtonTanqueo.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
            mButtonMantenimiento.setOnClickListener(this);
            mButtonSalida.setOnClickListener(this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Log.d(TAG, "onClick() id: " + id);
            Class activity = null;
            switch (id) {
                case R.id.ButtonOpNal:
                    activity = OperationActivity.class;
                    break;
                case R.id.ButtonLlegadaDescargar:
                    activity = InicioTurnoActivity.class;
                    break;
                case R.id.ButtonViajeVacio:
                    activity = InfoViajeVacioActivity.class;
                    break;
                case R.id.ButtonTanqueo:
                    activity = SolicitudTanqueoActivity.class;
                    break;
                case R.id.ButtonChat:
                    activity = ChatActivity.class;
                    break;
                case R.id.ButtonMantenimiento:
                    activity = OpcionesMantenimientoActivity.class;
                    break;
                case R.id.ButtonSalida:
                    getActivity().finish();
                    break;
                default:
                    break;
            }
            if (activity != null) {
                getActivity().startActivity(new Intent(getActivity(), activity));
            }
        }
    }
}
