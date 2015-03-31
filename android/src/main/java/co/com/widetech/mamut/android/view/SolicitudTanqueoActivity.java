package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;

public class SolicitudTanqueoActivity extends BinderServiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanqueo);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tanqueo, menu);
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
        Button mButtonEnviarTanqueo;
        Button mButtonChat;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_solicitud_tanqueo, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mButtonEnviarTanqueo = (Button) activity.findViewById(R.id.ButtonEnviarTanqueo);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonEnviarTanqueo.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Class activity = null;
            Intent intent = null;
            switch (id) {
                case R.id.ButtonEnviarTanqueo:
                    activity = MainActivity.class;
                    intent = new Intent(getActivity(), activity);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                case R.id.ButtonChat:
                    break;
                default:
                    break;
            }
            if (activity != null) {
                getActivity().startActivity(intent);
            }
        }
    }
}
