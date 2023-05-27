package com.jamillabltd.pushnotificationwithtoken;

public class NotificationData {
    private String title;
    private String message;

    public NotificationData(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}