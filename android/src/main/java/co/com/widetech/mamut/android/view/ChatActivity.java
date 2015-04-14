package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import co.com.widetech.mamut.android.R;
import co.com.widetech.mamut.android.adapter.ChatAdapter;
import co.com.widetech.mamut.android.utils.Config;
import co.com.widetech.mamut.android.utils.MessageBuilder;
import com.co.widetech.serial_port_core.models.Chat;
import com.co.widetech.serial_port_core.service.TransportDataService;
import com.co.widetech.serial_port_core.tools.WideTechTools;

import java.util.Vector;

public class ChatActivity extends BinderServiceActivity {
    private Vector<Chat> messages;
    private int sizechat;
    private StatusChatActivity mStatusChatActivity = StatusChatActivity.SEND_CHAT_BUTTON;
    private Fragment mFragment;

    public Vector<Chat> getMessages() {
        return messages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (savedInstanceState == null) {
            mFragment = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
        sendData(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        messages = TransportDataService.getQueueChat();
        TransportDataService.setOnlineChat(true);
        new Thread(new UpdateChat()).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TransportDataService.setOnlineChat(false);
    }

    @Override
    public void finish() {
        super.finish();
        TransportDataService.setOnlineChat(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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
        switch (mStatusChatActivity) {
            case SEND_CHAT_BUTTON:
                isValid = true;
                break;
            case SEND_CHAT_MESSAGE:
                PlaceholderFragment fragment = ((PlaceholderFragment) mFragment);
                String message = fragment.getMessageToSend();
                if (!message.isEmpty()) {
                    isValid = true;
                } else {
                    fragment.setValidationError("Ingrese un mensaje");
                }
                break;
            case SEND_OPTIONS:
                isValid = false;
                break;
            default:
                break;
        }
        return isValid;
    }

    @Override
    protected String buildData() {
        PlaceholderFragment fragment = ((PlaceholderFragment) mFragment);
        String data = null;
        switch (mStatusChatActivity) {
            case SEND_CHAT_BUTTON:
                data = new MessageBuilder(this).buildMessageMainButton(Config.buttonStrings.TYPE_MESSAGE_CHAT);
                break;
            case SEND_CHAT_MESSAGE:
                String messasge = fragment.getMessageToSend();
                data = new co.com.widetech.mamut.android.utils.MessageBuilder(this).buildMessageToChat(messasge);
                break;
            case SEND_OPTIONS:
                break;
            default:
                break;
        }
        return data;
    }

    public boolean sendMessage() {
        mStatusChatActivity = StatusChatActivity.SEND_CHAT_MESSAGE;
        return sendData(true);
    }

    enum StatusChatActivity {
        SEND_CHAT_BUTTON,
        SEND_CHAT_MESSAGE,
        SEND_OPTIONS,
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private Button mSendButton;
        private Button mBackButton;
        private Button mOptionsButton;
        private EditText mEditTextMessage;
        private ListView mListViewChats;

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity activity = getActivity();
            mSendButton = (Button) activity.findViewById(R.id.ButtonEnviar);
            mBackButton = (Button) activity.findViewById(R.id.ButtonVolver);
            mOptionsButton = (Button) activity.findViewById(R.id.ButtonOpciones);
            mEditTextMessage = (EditText) activity.findViewById(R.id.editTextMessage);
            mListViewChats = (ListView) activity.findViewById(R.id.ChatlistView);
            mSendButton.setOnClickListener(this);
            mBackButton.setOnClickListener(this);
            mOptionsButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            ChatActivity parentActivity = ((ChatActivity) getActivity());
            switch (id) {
                case R.id.ButtonEnviar:
                    if (parentActivity.sendMessage()) {
                        setValidationError(null);
                        Chat chat = new Chat(getMessageToSend(), WideTechTools.getHourPhone(), "Conductor");
                        parentActivity.messages.add(chat);
                        updateMessagesChat();
                        mEditTextMessage.setText("");
                    }
                    break;
                case R.id.ButtonVolver:
                    parentActivity.finish();
                    break;
                case R.id.ButtonOpciones:
                    break;
                default:
                    break;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
            return rootView;
        }

        public String getMessageToSend() {
            return mEditTextMessage.getText().toString();
        }

        public void setValidationError(String messageError) {
            mEditTextMessage.setError(messageError);
        }

        private void updateMessagesChat() {
            ChatActivity parentActivity = ((ChatActivity) getActivity());
            if (parentActivity != null) {
                mListViewChats.setAdapter(new ChatAdapter(parentActivity.getMessages(), parentActivity));
                mListViewChats.setFocusable(true);
                mListViewChats.setSelection(parentActivity.sizechat);
            }
        }
    }

    class UpdateChat implements Runnable {
        private boolean service = true;

        public void run() {
            while (service) {
                messages = TransportDataService.getQueueChat();
                if (messages.size() > sizechat) {
                    sizechat = messages.size();
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ((PlaceholderFragment) mFragment).updateMessagesChat();
                            }
                        });
                    } catch (Exception ex) {
                        String e = ex.getMessage();
                        System.err.println(e);
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
