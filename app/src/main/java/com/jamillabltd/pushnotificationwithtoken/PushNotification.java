package com.jamillabltd.pushnotificationwithtoken;

public class PushNotification {
    private NotificationData data;
    private String to;

    public PushNotification(NotificationData data, String to) {
        this.data = data;
        this.to = to;
    }

    public NotificationData getData() {
        return data;
    }

    public String getTo() {
        return to;
    }
}