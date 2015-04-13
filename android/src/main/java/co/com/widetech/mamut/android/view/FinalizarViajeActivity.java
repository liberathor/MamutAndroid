package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import co.com.widetech.mamut.android.R;
import utils.MessageBuilder;

public class FinalizarViajeActivity extends BinderServiceActivity {
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_viaje);
        mFragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
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
        boolean isValid = false;
        PlaceholderFragment fragmentFinalizacion = ((PlaceholderFragment) mFragment);
        if (!fragmentFinalizacion.getMessageFinalizacionViaje().isEmpty()) {
            isValid = true;
        } else {
            fragmentFinalizacion.setErrorValidation("Ingrese un mensage");
        }
        return isValid;
    }

    @Override
    protected String buildData() {
        PlaceholderFragment fragmentFinalizacion = ((PlaceholderFragment) mFragment);
        return new MessageBuilder(this).buildMessageFinalizacionViaje(fragmentFinalizacion.getMessageFinalizacionViaje());
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private EditText mEditTextFinalizacionViaje;
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
            mEditTextFinalizacionViaje = (EditText) activity.findViewById(R.id.editTextFinalizacionViaje);
            mButtonFinalizarViaje = (Button) activity.findViewById(R.id.ButtonFinalizarViaje);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonFinalizarViaje.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            FinalizarViajeActivity parentActivity = ((FinalizarViajeActivity) getActivity());
            Class activity = null;
            Intent intent = null;
            switch (id) {
                case R.id.ButtonFinalizarViaje:
                    if (parentActivity.sendData(true)) {
                        activity = MainActivity.class;
                        intent = new Intent(getActivity(), activity);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
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

        public void setErrorValidation(String errorFinalizacion) {
            mEditTextFinalizacionViaje.setError(errorFinalizacion);
        }

        public String getMessageFinalizacionViaje() {
            return mEditTextFinalizacionViaje.getText().toString();
        }
    }
}

