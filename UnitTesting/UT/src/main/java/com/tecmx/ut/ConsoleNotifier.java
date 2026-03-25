package com.tecmx.ut;

public class ConsoleNotifier implements Notifier {

    @Override
    public void send(String recipient, String message) {
        System.out.println("Notification for " + recipient + ": " + message);
    }
}
