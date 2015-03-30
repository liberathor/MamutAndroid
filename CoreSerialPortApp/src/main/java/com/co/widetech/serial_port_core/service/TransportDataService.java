package com.co.widetech.serial_port_core.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import com.co.widetech.serial_port_core.R;
import com.co.widetech.serial_port_core.models.Chat;
import com.co.widetech.serial_port_core.models.DeviceStatus;
import com.co.widetech.serial_port_core.reporter.CommunicationClient;
import com.co.widetech.serial_port_core.reporter.CommunicationService;
import com.co.widetech.serial_port_core.tools.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class TransportDataService extends Service implements
        CommunicationClient {

    private static final long UPDATE_INTERVAL_CHARGIN = 300000;
    private static final String INTENT_NOTIFICATION_SCREEN_CHAT = "intent.notification.screen.chat";
    private static Vector<Chat> queueChat = new Vector<Chat>();
    private static boolean onLineChat = false;
    private final IBinder mBinder = new LocalBinder();
    int baudrate;
    DeviceStatus deviceStatus;
    SharedPreferences sp;
    private CommunicationService commService;
    private Timer timerMessages = new Timer();
    private Timer timerChargin = new Timer();
    private MediaPlayer mp;
    private Vibrator vibrator;
    private StackOfMessages mStack = StackOfMessages.getInstance();
    private StatusChargin mStatusChargin = StatusChargin.getInstance();
    private boolean statusAcces = false;
    private String FinishConter;

    public static Vector<Chat> getQueueChat() {
        return queueChat;
    }

    public static void setQueueChat(Chat qChat) {
        queueChat.add(qChat);
    }

    public static boolean getOnlineChat() {
        return onLineChat;
    }

    public static void setOnlineChat(boolean status) {
        onLineChat = status;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void onCreate() {
        super.onCreate();

        createCommunicationService();

        deviceStatus = new DeviceStatus(getApplicationContext());
        sp = deviceStatus.getsPreferenceManager();

        try {
            FileWriter fw = new FileWriter("/dev/enable_external_tty");

            fw.write("1", 0, 1);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCommunicationService() {
        commService = CommunicationService.getInstance();
        commService.setContext(TransportDataService.this
                .getApplicationContext());
        commService.setComClient(TransportDataService.this);
        Thread thread = new Thread(commService);
        thread.start();
    }

    private void verifyStatusChargin() {
        this.timerChargin.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("valor de el Verificador: "
                        + TransportDataService.this.mStatusChargin
                        .getStatusBattery());

                if (!(TransportDataService.this.mStatusChargin
                        .getStatusBattery())) {
                    Intent dialogIntent = new Intent(getBaseContext(),
                            AlertDialogActivity.class);
                    dialogIntent.putExtra("title", "Alerta");
                    dialogIntent.putExtra("message",
                            "Cable de poder desconectado");
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TransportDataService.this.getApplication().startActivity(
                            dialogIntent);
                }
            }

        }, 0, UPDATE_INTERVAL_CHARGIN);
        Log.i(getClass().getSimpleName(), "Verificacion de carga iniciado");
    }

    public void onDestroy() {
        Log.d("servicio_transferData", "destruido");
        super.onDestroy();

        if (this.timerChargin != null) {
            this.timerChargin.cancel();
            Log.i(getClass().getSimpleName(), "Verificador de carga detenido");
        }

        commService.closeSerialPort();

        if (this.timerMessages != null) {
            this.timerMessages.cancel();
        }
        try {
            FileWriter fw = new FileWriter("/dev/enable_external_tty");

            fw.write("0", 0, 1);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transportMessage(String Message, boolean ack) {
        commService.convertMessage(Message, ack);
    }

    private String typeUnity() {
        String unityType = getResources().getString(R.string.option_unity_type);
        String unity = sp.getString(unityType, "");
        return unity;
    }

    private String baudrates() {
        String baudrate = getResources().getString(R.string.option_baudrate);
        String baudrates = sp.getString(baudrate, "");
        return baudrates;
    }

    private void sendMessageOfPlattform() {
        this.timerMessages.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("envio de datos del ack");
                System.out.println("Tipo de Unidad: " + typeUnity());
                System.out.println("Velocidad de Conexion: " + baudrates());
                System.out.println("Tiempo de Reporte: "
                        + (timeReporter() / 1000) + " Seg");
                if (mStack.sizeOfQueue() != 0) {
                    sendNewMessage();
                }
            }
        }, 0, timeReporter());
        // }, 0, UPDATE_INTERVAL_MESSAGES);
    }

    private long timeReporter() {
        String time = getResources().getString(R.string.option_time_report);
        String reporter = sp.getString(time, "");
        long timeReport = Long.parseLong(reporter);
        long tr = timeReport * 1000;
        return tr;
    }

    private void sendNewMessage() {
        if (mStack.sizeOfQueue() != 0) {
            System.out.println("id del mensaje enviado: "
                    + mStack.getMessageToQueue(0).getId());
            System.out.println("total de mensajes encolados: "
                    + mStack.sizeOfQueue());

            if (typeUnity().equalsIgnoreCase("Cellocator")) {
                System.out.println("mensaje a enviar: "
                        + new String(mStack.getMessageToQueue(0).getData()));
                commService.sendMessageByte(mStack.getMessageToQueue(0)
                        .getData());
            } else {
                System.out.println("mensaje a enviar: "
                        + new String(mStack.getMessageToQueue(0).getMessage()
                        .toString()));
                commService.sendMessage(mStack.getMessageToQueue(0)
                        .getMessage().toString());
            }
        }
    }

    private void onDataReceived(String data) {
        System.out.println("WT Data: " + data.toString());

        // resive RID cuando el usuario se loguea
        // >RID,nombre conductor<
        if (data.toString().contains("RID,")) {
            System.out.println("WT RID " + data.toString());
            try {
                String[] DataSplit = data.split(",");
                String codeDriver = DataSplit[1];
                String NameDriver = DataSplit[2];
                final String code = codeDriver
                        .substring(0, codeDriver.length());
                final String name = NameDriver.substring(0,
                        NameDriver.length() - 1);

                mBroadcastMessenger(name, code);
            } catch (Exception e) {
            }
        }

        // / recibe el mensaje con el comando para el chat >CH,mensaje<<
        if (data.toString().contains("CH,")) {
            try {
                Chat c = new Chat(data.substring(4, data.length() - 1),
                        WideTechTools.getHourPhone(), "Central");
                setQueueChat(c);
                startOrUpdateChat();
            } catch (Exception e) {
            }
        }

        // INGRESA EL ACK DE CONFIRMACION
        else if (data.toString().contains("ACK,")) {
            try {
                if (data.toString()
                        .contains(
                                String.valueOf(this.mStack.getMessageToQueue(0)
                                        .getId()))) {
                    System.out.println("WT Id del mensaje a remover: "
                            + (this.mStack.getMessageToQueue(0).getId()));
                    this.mStack.removeMessageToQueue(this.mStack
                            .getMessageToQueue(0));
                    System.out.println("Total de mensajes despues: "
                            + this.mStack.sizeOfQueue());
                    try {
                        sendNewMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                System.out.println("error ack " + e.getMessage());
            }
        }
    }

    private void mBroadcastMessenger(String user, String code) {
        Intent intent = new Intent("LoginSystem");
        intent.putExtra("Code", code);
        intent.putExtra("Name", user);
        sendBroadcast(intent);
    }

    public boolean getStatusAccess() {
        return this.statusAcces;
    }

    public void setStatusAccess(boolean status) {
        this.statusAcces = status;
    }

    public String getFinishConter() {
        return FinishConter;
    }

    public void setFinishConter(String finishConter) {
        FinishConter = finishConter;
    }

    private void startOrUpdateChat() {
        if (!getOnlineChat()) {
            setOnlineChat(true);
            Intent intent = new Intent(INTENT_NOTIFICATION_SCREEN_CHAT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);

            mp = MediaPlayer.create(TransportDataService.this, R.raw.send);
            mp.start();
            vibrator.vibrate(3000);
        } else {
            mp = MediaPlayer.create(TransportDataService.this, R.raw.send);
            mp.start();
            vibrator.vibrate(2000);
        }
    }

    @Override
    public void incomingMessage(String message) {
        onDataReceived(message);
    }

    @Override
    public void sendMessage() {
        sendMessageOfPlattform();
        verifyStatusChargin();

        StringBuilder sb = new StringBuilder();
        sb.append("MDT");
        sb.append(Utils.StartApplication(typeUnity()));
        sb.append(String.valueOf("S1"));

        transportMessage(sb.toString(), false);
    }

    public class LocalBinder extends Binder {
        public TransportDataService getService() {
            return TransportDataService.this;
        }
    }
}
