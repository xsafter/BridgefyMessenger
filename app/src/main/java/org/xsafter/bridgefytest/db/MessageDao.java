package org.xsafter.bridgefytest.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.xsafter.bridgefytest.model.Message;
import org.xsafter.bridgefytest.model.Peer;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM messages")
    LiveData<List<Message>> getAll();

    @Insert
    void insert(Message message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessageForUser(Peer peer, Message message);

    @Query("SELECT * FROM messages WHERE device_name = :deviceName")
    LiveData<List<Message>> getByDeviceName(String deviceName);

    //get message for user's id
    @Query("SELECT * FROM messages WHERE uuid = :userId")
    LiveData<List<Message>> getMessagesForUser(String userId);

    @Delete
    void delete(Message message);

}