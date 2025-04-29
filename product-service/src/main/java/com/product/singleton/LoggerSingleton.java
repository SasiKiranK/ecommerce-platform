package com.product.singleton;

// Simple Logger Singleton
public class LoggerSingleton {

    // Static private instance (only one allowed)
    private static LoggerSingleton instance;

    // Private constructor - no outside instantiation
    private LoggerSingleton() {
        System.out.println("Logger initialized...");
    }

    // Global access point (lazy initialization)
    public static synchronized LoggerSingleton getInstance() {
        if (instance == null) {
            instance = new LoggerSingleton();
        }
        return instance;
    }

    // Simple log method
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
