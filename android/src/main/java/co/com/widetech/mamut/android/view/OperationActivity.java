package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.utils.Config;
import co.com.widetech.mamut.android.utils.MessageBuilder;


public class OperationActivity extends BinderServiceActivity {
    public static final String TAG = "OperationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        sendFirstmessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_operation, menu);
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
        return new MessageBuilder(this).buildMessageMainButton(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private Button mButtonMantenimiento;
        private Button mButtonChat;

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            Activity activity = getActivity();
            mButtonMantenimiento = (Button) activity.findViewById(R.id.ButtonOptions);
            mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
            mButtonMantenimiento.setOnClickListener(this);
            mButtonChat.setOnClickListener(this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_operation, container, false);
            return rootView;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Class activity = null;
            switch (id) {
                case R.id.ButtonOptions:
                    activity = infoCargueActivity.class;
                    break;
                case R.id.ButtonChat:
                    activity = ChatActivity.class;
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
