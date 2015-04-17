package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.utils.Config;
import co.com.widetech.mamut.android.utils.MessageBuilder;

public class OpcionesMantenimientoActivity extends BinderServiceActivity {
    private static boolean isMantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_mantenimiento);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        getExtras();
        sendData(true);
    }

    public void getExtras() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            isMantenimiento = extras.getBoolean("EXTRA_TANQUEO", false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solicitud_mantenimiento, menu);
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
        return new MessageBuilder(this).buildMessageMainButton(Config.buttonStrings.TYPE_BUTTON_MANTENIMIENTO);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        Button mButtonTanqueo;
        Button mButtonMantenimiento;
        Button mButtonChat;
        Button mButtonVolver;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_opciones_mantenimiento, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mButtonTanqueo = (Button) activity.findViewById(R.id.ButtonTanqueo);
            mButtonMantenimiento = (Button) activity.findViewById(R.id.ButtonMantenimiento);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonVolver = (Button) activity.findViewById(R.id.ButtonVolver);
            mButtonTanqueo.setOnClickListener(this);
            mButtonMantenimiento.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
            mButtonVolver.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Class activity = null;
            switch (id) {
                case R.id.ButtonTanqueo:
                    activity = SolicitudTanqueoActivity.class;
                    break;
                case R.id.ButtonMantenimiento:
                    activity = SolicitudMantenimientoActivity.class;
                    break;
                case R.id.ButtonChat:
                    activity = ChatActivity.class;
                    break;
                case R.id.ButtonVolver:
                    break;
                default:
                    break;
            }
            if (activity != null) {
                Intent intent = new Intent(getActivity(), activity);
                intent.putExtra("EXTRA_TANQUEO", isMantenimiento);
                getActivity().startActivity(intent);
            }
        }
    }
}
