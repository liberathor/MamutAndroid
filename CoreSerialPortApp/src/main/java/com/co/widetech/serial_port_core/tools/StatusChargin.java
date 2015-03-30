package com.co.widetech.serial_port_core.tools;

public class StatusChargin {
    private static StatusChargin instance = new StatusChargin();

    private boolean statusCharginBattery = true;

    private StatusChargin() {/* pass */
    }

    public static StatusChargin getInstance() {
        return instance;
    }

    public boolean getStatusBattery() {
        return this.statusCharginBattery;
    }

    public void setStatusBattery(boolean status) {
        this.statusCharginBattery = status;
    }

}
