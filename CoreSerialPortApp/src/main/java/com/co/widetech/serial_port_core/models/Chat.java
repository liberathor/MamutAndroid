package com.co.widetech.serial_port_core.models;

public class Chat {
    private String message;
    private String date;
    private String user;

    public Chat(String message, String date, String user) {
        this.message = message;
        this.date = date;
        this.user = user;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}