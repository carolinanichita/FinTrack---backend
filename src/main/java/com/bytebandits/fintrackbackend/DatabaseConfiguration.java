package com.bytebandits.fintrackbackend;

public class DatabaseConfiguration {
    public String getDatabasePassword() {
        String dbPassword = System.getenv("DB_PASSWORD");
        if (dbPassword == null) {
            throw new IllegalStateException("DB_PASSWORD environment variable not set");
        }
        return dbPassword;
    }
}

