package org.xsafter.bridgefytest.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.xsafter.bridgefytest.model.Peer;

import java.util.List;

@Dao
public interface PeerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Peer peerDevice);

    @Query("SELECT * FROM peers")
    List<Peer> getAll();

    @Query("SELECT * FROM peers WHERE uuid = :deviceId")
    Peer getById(String deviceId);

    @Delete
    void delete(Peer peerDevice);
}