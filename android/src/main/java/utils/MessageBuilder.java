package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import co.com.widetech.mamut.android.R;
import com.co.widetech.serial_port_core.models.DeviceStatus;
import com.co.widetech.serial_port_core.tools.StackOfMessages;
import com.co.widetech.serial_port_core.tools.Utils;

/**
 * Created by wtjramirez on 4/7/15.
 */
public final class MessageBuilder {
    private static final String TAG = "MessageBuilder";
    private DeviceStatus mDeviceStatus;
    private SharedPreferences mPrefInfoDevice;
    private SharedStatus mSharedStatusApp;
    private Context mContext;
    private StackOfMessages mStack = StackOfMessages.getInstance();

    public MessageBuilder(Context context) {
        setmContext(context);
    }

    public void setmContext(Context context) {
        this.mContext = context;
        mSharedStatusApp = new SharedStatus(context);
        mPrefInfoDevice = mSharedStatusApp.getInfoDevice();
        mDeviceStatus = new DeviceStatus(mContext.getApplicationContext());
    }

    public String buildMessageNewTravel(String sCodeOrigen, String sCodeDestination, String tanker, String load, String quantity, String guideNumber, String expires) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.TYPE_MESSAGE_INIT_TRAVEL);
        sb.append("O" + sCodeOrigen);
        sb.append(",");
        sb.append("D" + sCodeDestination);
        sb.append(",");
        sb.append(tanker);
        sb.append(",");
        sb.append(load);
        sb.append(",");
        sb.append(quantity);
        sb.append(",");
        sb.append(guideNumber);
        sb.append(",");
        sb.append(expires);
        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String message = sb.toString();
        Log.d(TAG, "sendMessageTravel() message: " + message);
        return message;
    }

    public String buildMessageToDeviceStarted() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        stringBuilder.append(Config.TYPE_MESSAGE_LOGIN);
        SharedPreferences sPreferenceCode = mSharedStatusApp.getCodeUser();
        String code = sPreferenceCode.getString("codeUser", "");
        stringBuilder.append(code);
//		sb.append(";");
//		sb.append(String.valueOf(mStack.updateCounter()));
        stringBuilder.append(Utils.formatEndUnit(typeUnity(mContext), mContext));
        String dataToSend = stringBuilder.toString();
        Log.d(TAG, "buildMessageToDeviceStarted(): " + dataToSend);
        return dataToSend;
    }

    public String buildMessageToLogin() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.TYPE_MESSAGE_LOGIN);
        SharedPreferences sPreferenceCode = mSharedStatusApp.getCodeUser();
        String code = sPreferenceCode.getString("codeUser", "");
        sb.append(code);
//		sb.append(";");
//		sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String message = sb.toString();
        Log.d(TAG, "buildMessageToLogin(): " + message);
        return message;
    }

    public String buildMessageToChat(String message) {
        SharedPreferences sp = mDeviceStatus.getsPreferenceManager();
        String unityType = mContext.getResources().getString(
                R.string.option_unity_type);
        String unity = sp.getString(unityType, "");
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(unity, mContext));
        sb.append(Config.TYPE_MESSAGE_CHAT);
        sb.append(message);
        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(unity, mContext));

        String newMessage = sb.toString();
        Log.d(TAG, "buildMessageChat(): " + newMessage);
        return newMessage;
    }

    public String buildMessageToStartTravel(String sCodeOrigen, String sCodeDestination, String tanker, String load, String quantity, String guideNumber, String expires) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.TYPE_MESSAGE_INIT_TRAVEL);
        sb.append("O" + sCodeOrigen);
        sb.append(",");
        sb.append("D" + sCodeDestination);
        sb.append(",");
        sb.append(tanker);
        sb.append(",");
        sb.append(load);
        sb.append(",");
        sb.append(quantity);
        sb.append(",");
        sb.append(guideNumber);
        sb.append(",");
        sb.append(expires);
        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String message = sb.toString();
        Log.d(TAG, "buildMessageToStartTravel(): " + message);
        return message;
    }

    public String buildMessageToNotifyTravelStatus(String message, String travelStatus) {
        StringBuilder sb = new StringBuilder();

        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        //example type message: sb.append(Config.TYPE_MESSAGE_PAUSE_TRAVEL);
        sb.append(travelStatus);
        sb.append(message);
        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        System.out.println(message);
        return newMessage;
    }

    public String typeUnity(Context context) {
        SharedPreferences sp = mDeviceStatus.getsPreferenceManager();
        String unityType = context.getResources().getString(R.string.option_unity_type);
        String unity = sp.getString(unityType, "");
        if (unity.length() == 0) {
            throw new IllegalStateException("Type Unity no set");
        }
        return unity;
    }

    private String typeDevice(Context context) {
        SharedPreferences sp = mDeviceStatus.getsPreferenceManager();
        String deviceType = context.getResources().getString(R.string.option_device);
        String device = sp.getString(deviceType, "");
        if (device.length() == 0) {
            throw new IllegalStateException("Type Device no set");
        }
        return device;
    }
}
