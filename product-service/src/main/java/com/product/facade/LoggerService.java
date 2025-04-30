package com.product.facade;

public class LoggerService {
    public void log(String message) {
        System.out.println("📝 LOG: " + message);
    }
}

public class NotifierService {
    public void send(String message) {
        System.out.println("📧 NOTIFY: " + message);
    }
}