package org.xsafter.bridgefytest.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(tableName = "messages")
public class Message implements DataMessage {

    @PrimaryKey
    @ColumnInfo(name = "uuid")
    @NonNull
    private String uuid;
    @ColumnInfo(name = "text")
    private String text;
    @ColumnInfo(name = "direction")
    private MessageType direction;
    @ColumnInfo(name = "timestamp")
    private long timestamp;
    @ColumnInfo(name = "device_name")
    private String deviceName;

    public Message(String text) {
        this.uuid = UUID.randomUUID().toString();
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
        this.text = text;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    public MessageType getDirection() {
        return direction;
    }

    public void setDirection(MessageType direction) {
        this.direction = direction;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
