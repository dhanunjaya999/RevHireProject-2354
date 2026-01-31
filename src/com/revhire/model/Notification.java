package com.revhire.model;

import java.util.Date;

public class Notification {

    private String message;
    private Date date;

    public Notification(String message) {
        this.message = message;
        this.date = new Date();
    }

    public String getMessage() {
        return message;
    }
}

