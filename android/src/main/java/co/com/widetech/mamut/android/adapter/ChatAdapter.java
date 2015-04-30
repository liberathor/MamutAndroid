package co.com.widetech.mamut.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.co.widetech.serial_port_core.models.Chat;

import java.util.Vector;

import co.com.widetech.mamut.android.R;

/*TODO
*
* Adapter Messenger Chat
* Array Messages
* BaseAdapter para menejar bases de datos
*
*
 */


public class ChatAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Vector<Chat> messages;

    public ChatAdapter(Vector<Chat> messages, Context context) {
        this.messages = messages;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public int getCount() {
        return this.messages.size();
    }

    public Object getItem(int position) {
        return this.messages.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Chat c = this.messages.get(position);
        String name = c.getUser();
        String message = c.getMessage();

        int index = message.indexOf(",");
        message = message.substring(index + 1, message.length());

        String hour = c.getDate();

        View vi = convertView;

        if (convertView == null)
            if (name.equalsIgnoreCase("Central"))
                vi = inflater.inflate(R.layout.message_row_pcc, null);
            else
                vi = inflater.inflate(R.layout.message_row, null);

        TextView textName = (TextView) vi.findViewById(R.id.NameUser);
        TextView textMessage = (TextView) vi.findViewById(R.id.MessageUser);
        TextView textHour = (TextView) vi.findViewById(R.id.HourMessage);

        textName.setText(name + ":");
        textMessage.setText(message);
        textHour.setText(hour);

        return vi;
    }

}
