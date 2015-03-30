package com.co.widetech.serial_port_core.tools;

import com.co.widetech.serial_port_core.models.Ack;

import java.util.ArrayList;


public class StackOfMessages {
    private static StackOfMessages instance = new StackOfMessages();
    private ArrayList<Ack> queue;
    private int counter = 0;

    private StackOfMessages() {
        this.queue = new ArrayList<Ack>();
    }

    public static StackOfMessages getInstance() {
        return instance;
    }

    public int getCounter() {
        return this.counter;
    }

    public int updateCounter() {
        this.counter = counter + 1;
        return this.counter;
    }

    public void clear() {
        this.queue.clear();
        this.counter = 0;
    }

    public int sizeOfQueue() {
        return this.queue.size();
    }

    public void addMessageToQueue(Ack obj) {
        this.queue.add(obj);
    }

    public Ack getMessageToQueue(int index) {
        return this.queue.get(index);
    }

    public boolean removeMessageToQueue(Ack obj) {
        return this.queue.remove(obj);
    }
}