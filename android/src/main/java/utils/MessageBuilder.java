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

    public String buildMessageToLogin() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_MESSAGE_LOGIN);
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
        sb.append(Config.buttonStrings.TYPE_MESSAGE_CHAT);
        sb.append(message);
        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(unity, mContext));

        String newMessage = sb.toString();
        Log.d(TAG, "buildMessageChat(): " + newMessage);
        return newMessage;
    }

    public String buildMessageMainButton(String typeButton) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(typeButton);
        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageInformacionViaje(String ciudad, String carga) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);
        sb.append(Config.valuesOperacionNal.TYPE_ACTION_INICIE_VIAJE);
        sb.append(Config.SEPARATOR);

        sb.append(ciudad);
        sb.append(Config.SEPARATOR);
        sb.append(carga);
        sb.append(Config.SEPARATOR);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageLlegueACargar() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);
        sb.append(Config.valuesOperacionNal.TYPE_ACTION_INICIE_VIAJE);
        sb.append(Config.SEPARATOR);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageInicieMiViaje() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);
        sb.append(Config.valuesOperacionNal.TYPE_ACTION_INICIE_VIAJE);
        sb.append(Config.SEPARATOR);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageDatosDeViaje(String manifiesto, String trailer) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);
        sb.append(Config.valuesOperacionNal.TYPE_ACTION_INICIE_VIAJE);
        sb.append(Config.SEPARATOR);

        sb.append(manifiesto);
        sb.append(Config.SEPARATOR);
        sb.append(trailer);
        sb.append(Config.SEPARATOR);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
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
