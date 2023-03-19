package org.xsafter.bridgefytest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bridgefy.sdk.client.Bridgefy;
import com.bridgefy.sdk.client.BridgefyClient;
import com.bridgefy.sdk.client.Device;
import com.bridgefy.sdk.client.Message;
import com.bridgefy.sdk.client.MessageListener;
import com.bridgefy.sdk.client.RegistrationListener;
import com.bridgefy.sdk.client.StateListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String API_KEY = BuildConfig.BRIDGEFY_KEY;

        // Always use the Application context to avoid leaks
        Bridgefy.initialize(getApplicationContext(), API_KEY, new RegistrationListener() {
            @Override
            public void onRegistrationSuccessful(BridgefyClient bridgefyClient) {
                // Bridgefy is ready to start
                Bridgefy.start(messageListener, stateListener);
            }

            @Override
            public void onRegistrationFailed(int errorCode, String message) {
                // Something went wrong: handle error code, maybe print the message
            }
        });
    }

    private MessageListener messageListener = new MessageListener() {
        @Override
        public void onMessageReceived(Message message) {
            // Handle message
        }

        @Override
        public void onBroadcastMessageReceived(Message message) {
            // Handle message
        }
    };

    StateListener stateListener = new StateListener() {

        @Override
        public void onDeviceDetected(Device device) {
            
        }

        @Override
        public void onDeviceUnavailable(Device device) {

        }
    };
}