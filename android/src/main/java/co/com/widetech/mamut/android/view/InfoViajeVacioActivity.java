package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;

public class InfoViajeVacioActivity extends BinderServiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viaje);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        Button mButtonEnviarEstadoViaje;
        Button mButtonChat;
        Button mButtonOpciones;

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
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Class activity = null;
            switch (id) {
                case R.id.ButtonEnviarInfoViaje:
                    activity = EstadoViajeActivity.class;
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
                getActivity().startActivity(new Intent(getActivity(), activity));
            }
        }
    }
}
