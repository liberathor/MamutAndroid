package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;

public class FinalizarViajeActivity extends BinderServiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_viaje);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finalizar_viaje, menu);
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
        private Button mButtonFinalizarViaje;
        private Button mButtonChat;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_finalizar_viaje, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mButtonFinalizarViaje = (Button) activity.findViewById(R.id.ButtonFinalizarViaje);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonFinalizarViaje.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Class activity = null;
            Intent intent = null;
            switch (id) {
                case R.id.ButtonFinalizarViaje:
                    activity = MainActivity.class;
                    intent = new Intent(getActivity(), activity);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
                case R.id.ButtonChat:
                    activity = ChatActivity.class;
                    intent = new Intent(getActivity(), activity);
                    break;
                default:
                    break;
            }
            if (activity != null && intent != null) {
                getActivity().startActivity(intent);
            }
        }
    }
}

