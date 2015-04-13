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

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageReinicarViaje() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);
        sb.append(Config.valuesOperacionNal.TYPE_ACTION_DETENCION_EN_RUTA);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesDetencionRuta.TYPE_ACTION_REINICIAR_VIAJE);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageLlegueADescargar() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);
        sb.append(Config.valuesOperacionNal.TYPE_ACTION_DETENCION_EN_RUTA);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesOperacionNal.TYPE_ACTION_LLEGUE_DESCARGAR);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageFinalizacionViaje(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesOperacionNal.TYPE_ACTION_FINALIZACION_VIAJE);
        sb.append(Config.SEPARATOR);
        sb.append(message);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageInicializarTurno() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_PROYECTOS);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesProyectos.TYPE_ACTION_INICIAR_TURNO);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageViajeVacioInfoViaje(String cuidadDestino, String ordenDestino, String trailer) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_VIAJE_VACIO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesViajeVacio.TYPE_ACTION_INFORMACION_VIAJE);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageFinalizarTurno() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_PROYECTOS);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesProyectos.TYPE_ACTION_FINALIZAR_TURNO);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageViajeVacioInformacionDeViaje(String ciudad, String ordenServicio, String trailer) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_VIAJE_VACIO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesViajeVacio.TYPE_ACTION_INFORMACION_VIAJE);
        sb.append(Config.SEPARATOR);

        sb.append(ciudad);
        sb.append(Config.SEPARATOR);
        sb.append(ordenServicio);
        sb.append(Config.SEPARATOR);
        sb.append(trailer);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageMotivoDetencionRuta(Config.valuesDetencionRuta motivo) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);
        sb.append(Config.valuesOperacionNal.TYPE_ACTION_DETENCION_EN_RUTA);
        sb.append(Config.SEPARATOR);

        sb.append(motivo);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageDetencionEnRutaMotivoPausa(Config.valuesDetencionRuta codigoMotivo) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_VIAJE_VACIO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesViajeVacio.TYPE_ACTION_INFORMACION_VIAJE);
        sb.append(Config.SEPARATOR);

        sb.append(codigoMotivo);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageDetencionEnRutaOtroMotivo(String motivo) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_VIAJE_VACIO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesViajeVacio.TYPE_ACTION_INFORMACION_VIAJE);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesDetencionRuta.TYPE_ACTION_OTRO_MOTIVO);
        sb.append(Config.SEPARATOR);
        sb.append(motivo);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageDetencionEnRutaOperacionNal() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_OPERACION_NAL);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesOperacionNal.TYPE_ACTION_DETENCION_EN_RUTA);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageLlegueDescargarEnViajeVacio() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_VIAJE_VACIO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesViajeVacio.TYPE_ACTION_LLEGUE_DESCARGAR);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageFinalizacionDeViajeEnViajeVacio() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_VIAJE_VACIO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesViajeVacio.TYPE_ACTION_FINALIZACION_VIAJE);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageTanqueo(String eds, int galones) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_TANQUEO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesTanqueo.TYPE_ACTION_TANQUEO);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageSolicitudMantenimiento(String mensaje) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_MANTENIMIENTO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesMantenimiento.TYPE_ACTION_SOLICITUD_MANTENIMIENTO);
        sb.append(Config.SEPARATOR);
        sb.append(mensaje);

        sb.append(";");
        sb.append(String.valueOf(mStack.updateCounter()));
        sb.append(Utils.formatEndUnit(typeUnity(mContext), mContext));

        String newMessage = sb.toString();
        Log.d(TAG, newMessage);
        return newMessage;
    }

    public String buildMessageReportarEstadoMantenimiento(int estadoMantenimiento) {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.formatStartUnit(typeUnity(mContext), mContext));
        sb.append(Config.buttonStrings.TYPE_BUTTON_MANTENIMIENTO);
        sb.append(Config.SEPARATOR);

        sb.append(Config.valuesMantenimiento.TYPE_ACTION_SOLICITUD_MANTENIMIENTO);
        sb.append(Config.SEPARATOR);
        sb.append(estadoMantenimiento);

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
