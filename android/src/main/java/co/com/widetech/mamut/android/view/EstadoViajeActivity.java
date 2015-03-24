package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;


public class EstadoViajeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_viaje);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_estado_viaje, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private Button mButtonDetencionRuta;
        private Button mButtonLlegueDescargar;
        private Button mButtonChat;
        private Button mButtonOptions;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Activity activity = getActivity();
            mButtonDetencionRuta = (Button) activity.findViewById(R.id.ButtonDetencionRuta);
            mButtonLlegueDescargar = (Button) activity.findViewById(R.id.ButtonLlegadaDescargar);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonOptions = (Button) activity.findViewById(R.id.ButtonOptions);
            mButtonDetencionRuta.setOnClickListener(this);
            mButtonLlegueDescargar.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
            mButtonOptions.setOnClickListener(this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Class activity = null;
            switch (id) {
                case R.id.ButtonDetencionRuta:
                    activity = DetencionRutaActivity.class;
                    break;
                case R.id.ButtonLlegadaDescargar:
                    activity = FinalizarViajeActivity.class;
                    break;
                case R.id.ButtonChat:
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_estado_viaje, container, false);
            return rootView;
        }
    }
}
