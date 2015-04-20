package com.co.widetech.serial_port_core.reporter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;

import android.content.Context;
import android.content.SharedPreferences;
import android.serialport.SerialPort;

import com.co.widetech.serial_port_core.R;
import com.co.widetech.serial_port_core.models.Ack;
import com.co.widetech.serial_port_core.models.DeviceStatus;
import com.co.widetech.serial_port_core.tools.StackOfMessages;

public class CommunicationService implements Runnable {

	private boolean service;
	public CommunicationClient comClient;
	private Context context;
	private boolean connected = false;
	boolean error = false;

	private static CommunicationService instance;
	private StackOfMessages mStack = StackOfMessages.getInstance();

	protected SerialPort mSerialPort;
	private SerialPort mSPort = null;
	public static OutputStream mOutputStream;
	private InputStream mInputStream;
	private String unity;
	private int baudrate;
	private String path;
	private String device;
	private String baudrates;
	private String unityType;
	private String data;
	private DeviceStatus deviceStatus;
	private SharedPreferences sp;

	public void setContext(Context context) {
		this.context = context;
	}

	public void setComClient(CommunicationClient comClient) {
		this.comClient = comClient;
	}

	public static CommunicationService getInstance() {
		if (instance == null) {
			instance = new CommunicationService();
		}
		return instance;
	}

	public void run() {
		this.service = true;
		while (this.service) {
			try {
				deviceStatus = new DeviceStatus(context);
				SharedPreferences statusDevice = deviceStatus
						.getStatusSelectSerialPort();
				Boolean status = statusDevice.getBoolean(
						"valueSelectSerialPort", false);
				if (status) {
					mSerialPort = getSerialPort();
					mOutputStream = mSerialPort.getOutputStream();
					mInputStream = mSerialPort.getInputStream();
					if (!connected) {
						comClient.sendMessage();
						connected = true;
					}
				}

				while (!error) {
					try {
						data = "";
						byte[] buffer = new byte[1];
						if (mInputStream == null)
							return;
						do {
							buffer[0] = (byte) mInputStream.read();
							data += new String(buffer);
						} while (buffer[0] != '<');
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
					System.out.println("WT mensaje que ingresa: " + data);
					if (!data.equalsIgnoreCase("") || !(data == null)
							|| data.charAt(0) == '>'
							|| !data.equalsIgnoreCase("OK"))
						if (comClient != null)
							comClient.incomingMessage(data);
				}
			} catch (UnknownHostException u) {
				try {
					Thread.sleep(27000);
				} catch (Exception e) {
				}
			} catch (IOException ex) {
				try {
					Thread.sleep(27000);
				} catch (Exception e) {

				}
			} catch (SecurityException s) {
			}
		}
		if (comClient != null) {
		}
	}

	public SerialPort getSerialPort() throws SecurityException, IOException,
			InvalidParameterException {

		if (mSPort == null) {
			/* Read serial port parameters */
			updateManagerDevice();
			/* Check parameters */
			if ((path.length() == 0) || (baudrate == -1)) {
				throw new InvalidParameterException();
			}
			/* Open the serial port */
			mSPort = new SerialPort(new File(path), baudrate);
			System.out.println(mSPort);
		}
		return mSPort;
	}

	private void updateManagerDevice() {

		device = context.getResources().getString(R.string.option_device);
		baudrates = context.getResources().getString(R.string.option_baudrate);
		unityType = context.getResources()
				.getString(R.string.option_unity_type);

		sp = deviceStatus.getsPreferenceManager();
		path = sp.getString(device, "");
		baudrate = Integer.decode(sp.getString(baudrates, ""));
		unity = sp.getString(unityType, "");
		System.out.println(path + "\n" + baudrate + "\n" + unity);
	}

	public void convertMessage(String Message, boolean ack) {
		updateManagerDevice();
		if (unity.equalsIgnoreCase("Cellocator")) {
			int sizeSend = Message.length() + 6;
			byte[] bytesToSend = new byte[sizeSend];
			bytesToSend[0] = 77;// M
			bytesToSend[1] = 50;// 2
			bytesToSend[2] = 67;// C
			bytesToSend[3] = 8;
			bytesToSend[4] = (byte) Message.length();
			int size = Message.length();
			int byteNext = 5;
			int checksum = 0;
			for (int i = 0; i < size; i++) {
				String str = Integer.toHexString(Message.charAt(i));
				bytesToSend[i + byteNext] = hexStringToByte(str);
				checksum += hexStringToByte(str);
			}
			bytesToSend[sizeSend - 1] = (byte) (checksum + 8 + Message.length());
			System.out.println(bytesToSend);

			if (ack) { // tiene ack
				try {
					mStack.addMessageToQueue(new Ack(mStack.getCounter(),
							bytesToSend));
					System.out.println(R.string.message_collage
							+ mStack.getCounter());
                    if(mStack.sizeOfQueue() == 1){ // si el tamaño del ack es 1
                        if (comClient != null)
                            comClient.informShipping();
                    }

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				sendMessageByte(bytesToSend);
			}
		} else {
			if (ack) {
				try {
					mStack.addMessageToQueue(new Ack(mStack.getCounter(),
							Message.toString()));
					System.out.println(R.string.message_collage
							+ mStack.getCounter());
                    if(mStack.sizeOfQueue() == 1){
                        if (comClient != null)
                            comClient.informShipping();
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				sendMessage(Message.toString());
			}
		}
	}

	private static byte hexStringToByte(String s) {
		byte data = 0;
		try {
			data = (byte) ((Character.digit(s.charAt(0), 16) << 4) + Character
					.digit(s.charAt(1), 16));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public void sendMessage(String message) {
		try {
			if (!(mOutputStream == null)) {
				mOutputStream.write(new String(message).getBytes());
				// mOutputStream.write('\r');
				mOutputStream.flush();
			}
		} catch (SecurityException e) {
			System.out.println(R.string.error_security);
		} catch (IOException e) {
			System.out.println(R.string.error_unknown);
		} catch (InvalidParameterException e) {
			System.out.println(R.string.error_configuration);
		}
	}

	public void sendMessageByte(byte[] data) {
		try {
			if (!(mOutputStream == null)) {
				mOutputStream.write(data);
				// mOutputStream.write('\r');
				mOutputStream.flush();
			}
		} catch (SecurityException e) {
			System.out.println(R.string.error_security);
		} catch (IOException e) {
			System.out.println(R.string.error_unknown);
		} catch (InvalidParameterException e) {
			System.out.println(R.string.error_configuration);
		}
	}

	public void closeSerialPort() {
		if (mSPort != null) {
			mSPort.close();
			mSPort = null;
			this.mSerialPort = null;
		}
	}
}