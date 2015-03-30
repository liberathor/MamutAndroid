package com.co.widetech.serial_port_core.models;

public class Ack {
    private int id;
    private String message;
    private byte[] data;

    public Ack(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public Ack(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}