package org.xsafter.bridgefytest.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

@Entity(tableName = "peers")
public class Peer {
    public enum DeviceType {
        UNDEFINED,
        IPHONE,
        ANDROID
    }

    @ColumnInfo(name = "device_name")
    private String deviceName;
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    @NonNull
    private String uuid;
    @ColumnInfo(name = "nearby")
    private boolean isNearby;
    @ColumnInfo(name = "device_type")
    private DeviceType deviceType;

    public Peer(String uuid, String deviceName) {
        this.uuid = uuid;
        this.deviceName = deviceName;
    }

    public void setNearby(boolean nearby) {
        isNearby = nearby;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isNearby() {
        return isNearby;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public static Peer fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Peer.class);
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
