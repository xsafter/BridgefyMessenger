package org.xsafter.bridgefytest;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xsafter.bridgefytest.db.MessageDao;
import org.xsafter.bridgefytest.db.MessagesDatabase;
import org.xsafter.bridgefytest.model.Message;

import java.util.List;
import java.util.UUID;

@RunWith(AndroidJUnit4.class)
public class MessageDaoTest {
    private MessagesDatabase messagesDatabase;
    private MessageDao messageDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        messagesDatabase = Room.inMemoryDatabaseBuilder(context, MessagesDatabase.class).build();
        messageDao = messagesDatabase.messageDao();
    }

    @After
    public void tearDown() {
        messagesDatabase.close();
    }

    @Test
    public void testInsertAndGetAll() throws InterruptedException {
        Message message = new Message("Hello, world!");

        messageDao.insert(message);
        LiveData<List<Message>> messagesLiveData = messageDao.getAll();


        List<Message> messages = LiveDataTestUtil.getValue(messagesLiveData);

        Log.i("Messages", String.valueOf(messages.size()));
        assertEquals(1, messages.size());
        Log.i("Messages", messages.get(0).getText());
        assertEquals("Hello, world!", messages.get(0).getText());
    }

    @Test
    public void testInsertAndGetByDeviceName() throws InterruptedException {
        Message message1 = new Message("Hello, device1!");
        message1.setDeviceName("device1");

        Message message2 = new Message("Hello, device2!");
        message2.setDeviceName("device2");

        messageDao.insert(message1);
        messageDao.insert(message2);

        LiveData<List<Message>> messagesLiveData = messageDao.getByDeviceName("device1");
        List<Message> messages = LiveDataTestUtil.getValue(messagesLiveData);

        Log.i("Messages", String.valueOf(messages.size()));
        assertEquals(1, messages.size());
        Log.i("Messages", messages.get(0).getText());
        assertEquals("Hello, device1!", messages.get(0).getText());
    }

    @Test
    public void testInsertAndGetMessagesForUser() throws InterruptedException {
        String userId = UUID.randomUUID().toString();

        Message message1 = new Message("Hello, user!");
        message1.setUuid(userId);

        Message message2 = new Message("How are you?");
        message2.setUuid(UUID.randomUUID().toString());

        messageDao.insert(message1);
        messageDao.insert(message2);

        LiveData<List<Message>> messagesLiveData = messageDao.getMessagesForUser(userId);
        List<Message> messages = LiveDataTestUtil.getValue(messagesLiveData);

        Log.i("Messages", String.valueOf(messages.size()));
        assertEquals(1, messages.size());
        Log.i("Messages", messages.get(0).getText());
        assertEquals("Hello, user!", messages.get(0).getText());
    }
}
