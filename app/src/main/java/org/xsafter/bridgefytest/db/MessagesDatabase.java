package org.xsafter.bridgefytest.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.xsafter.bridgefytest.model.Message;

import org.xsafter.bridgefytest.model.Peer;

@Database(entities = {Peer.class, Message.class}, version = 1)
public abstract class MessagesDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "messages_db";
    private static MessagesDatabase instance;

    public static synchronized MessagesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MessagesDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract PeerDao peerDeviceDao();

    public abstract MessageDao messageDao();
}
