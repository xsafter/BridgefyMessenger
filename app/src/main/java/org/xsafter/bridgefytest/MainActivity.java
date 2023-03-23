package org.xsafter.bridgefytest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgefy.sdk.client.Bridgefy;
import com.bridgefy.sdk.client.BridgefyClient;
import com.bridgefy.sdk.client.Device;
import com.bridgefy.sdk.client.Message;
import com.bridgefy.sdk.client.MessageListener;
import com.bridgefy.sdk.client.RegistrationListener;
import com.bridgefy.sdk.client.StateListener;

import org.xsafter.bridgefytest.model.Peer;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String API_KEY = BuildConfig.BRIDGEFY_KEY;

        Log.d("Bridgefy", "API_KEY: " + API_KEY);

        // Always use the Application context to avoid leaks
        Bridgefy.initialize(getApplicationContext(), API_KEY, new RegistrationListener() {
            @Override
            public void onRegistrationSuccessful(BridgefyClient bridgefyClient) {
                // Bridgefy is ready to start
                Bridgefy.start(messageListener, stateListener);
                Log.e("Bridgefy", "Bridgefy is ready to start");
            }

            @Override
            public void onRegistrationFailed(int errorCode, String message) {
                // Something went wrong: handle error code, maybe print the message
                Log.e("Bridgefy", "onRegistrationFailed: " + message);
            }
        });
    }

    private MessageListener messageListener = new MessageListener() {
        @Override
        public void onMessageReceived(Message message) {
            //
            if (message.getContent().get("device_name") != null) {
                Peer peer = new Peer(message.getSenderId(),
                        (String) message.getContent().get("device_name"));
                peer.setNearby(true);
                peer.setDeviceType(extractType(message));
                //peersAdapter.addPeer(peer);
                Log.d("Peer", "Peer introduced itself: " + peer.getDeviceName());
            } else {
                String incomingMessage = (String) message.getContent().get("text");
                Log.d("Message", "Incoming private message: " + incomingMessage);
                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(
                        new Intent(message.getSenderId())
                                .putExtra("message", incomingMessage));
            }
        }

        @Override
        public void onBroadcastMessageReceived(Message message) {
            // we should not expect to have connected previously to the device that originated
            // the incoming broadcast message, so device information is included in this packet
            String incomingMsg = (String) message.getContent().get("text");
            String deviceName  = (String) message.getContent().get("device_name");
            Peer.DeviceType deviceType = extractType(message);

            Log.d("Broadcast", "Incoming broadcast message: " + incomingMsg);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(
                    new Intent("Broadcast")
                            .putExtra("peerName", deviceName)
                            .putExtra("deviceType", deviceType)
                            .putExtra("message",  incomingMsg));
        }
    };

    StateListener stateListener = new StateListener() {

        @Override
        public void onDeviceDetected(Device device) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("device_name", Build.MANUFACTURER + " " + Build.MODEL);
            map.put("device_type", Peer.DeviceType.ANDROID.ordinal());
            device.sendMessage(map);
        }

        @Override
        public void onDeviceUnavailable(Device device) {

        }

        @Override
        public void onStartError(String message, int errorCode) {
            if (errorCode == StateListener.INSUFFICIENT_PERMISSIONS) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }
    };


    public boolean isThingsDevice(Context context) {
        final PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature("android.hardware.type.embedded");
    }

    private Peer.DeviceType extractType(Message message) {
        int eventOrdinal;
        Object eventObj = message.getContent().get("device_type");
        if (eventObj instanceof Double) {
            eventOrdinal = ((Double) eventObj).intValue();
        } else {
            eventOrdinal = (Integer) eventObj;
        }
        return Peer.DeviceType.values()[eventOrdinal];
    }
}