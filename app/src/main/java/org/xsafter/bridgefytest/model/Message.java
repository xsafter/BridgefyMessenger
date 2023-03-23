package org.xsafter.bridgefytest.model;

import com.google.gson.Gson;


public class Message implements DataMessage {
    private String text;
    private int direction;
    private String deviceName;

    public Message(String text) {
        this.text = text;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getText() {
        return text;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public static Message create(String json) {
        return new Gson().fromJson(json, Message.class);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
