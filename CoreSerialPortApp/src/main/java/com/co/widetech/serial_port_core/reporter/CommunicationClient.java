package com.co.widetech.serial_port_core.reporter;

public interface CommunicationClient {

    void incomingMessage(String message);

    void sendMessage();
}
