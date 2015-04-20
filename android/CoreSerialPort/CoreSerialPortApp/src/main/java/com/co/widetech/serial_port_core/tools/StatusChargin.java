package com.co.widetech.serial_port_core.tools;

public class StatusChargin {
	private static StatusChargin instance = new StatusChargin();

	private boolean statusCharginBattery = true;

	public static StatusChargin getInstance() {
		return instance;
	}

	private StatusChargin() {/* pass */
	}

	public void setStatusBattery(boolean status) {
		this.statusCharginBattery = status;
	}

	public boolean getStatusBattery() {
		return this.statusCharginBattery;
	}

}
