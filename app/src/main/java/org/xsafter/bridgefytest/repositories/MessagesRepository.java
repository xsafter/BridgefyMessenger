package org.xsafter.bridgefytest.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.xsafter.bridgefytest.db.MessageDao;
import org.xsafter.bridgefytest.db.MessagesDatabase;
import org.xsafter.bridgefytest.db.PeerDao;
import org.xsafter.bridgefytest.model.Message;
import org.xsafter.bridgefytest.model.Peer;

import java.util.List;

public class MessagesRepository {
    private static final String DATABASE_NAME = "messages_db";
    private static MessagesRepository instance;

    MessageDao messageDao;
    PeerDao peerDao;

    MessagesDatabase messagesDatabase;

    private LiveData<List<Message>> allMessages;

    public MessagesRepository(Application application) {
        messagesDatabase = MessagesDatabase.getInstance(application);
        messageDao = messagesDatabase.messageDao();
        peerDao = messagesDatabase.peerDeviceDao();
    }

    public LiveData<List<Message>> getMessagesForUser(String userId) {
        return messageDao.getMessagesForUser(userId);
    }

    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }

    public void insertMessage(Message message) {
        new InsertMessageAsyncTask(messageDao).execute(message);
    }

    public void insertMessageForUser(Peer peer, Message message) {
        new InsertMessageForUserAsyncTask(messageDao, peer).execute(message);
    }

    public void insertPeer(Peer peer) {
        new InsertPeerAsyncTask(peerDao).execute(peer);
    }

    private static class InsertMessageAsyncTask extends AsyncTask<Message, Void, Void> {
        private final MessageDao messageDao;

        InsertMessageAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messageDao.insert(messages[0]);
            return null;
        }
    }

    private static class InsertMessageForUserAsyncTask extends AsyncTask<Message, Void, Void> {
        private final MessageDao messageDao;
        private final Peer peer;

        InsertMessageForUserAsyncTask(MessageDao messageDao, Peer peer) {
            this.messageDao = messageDao;
            this.peer = peer;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messageDao.insertMessageForUser(peer, messages[0]);
            return null;
        }
    }

    private static class InsertPeerAsyncTask extends AsyncTask<Peer, Void, Void> {
        private final PeerDao peerDao;

        private InsertPeerAsyncTask(PeerDao peerDao) {
            this.peerDao = peerDao;
        }

        @Override
        protected Void doInBackground(Peer... peers) {
            peerDao.insert(peers[0]);
            return null;
        }
    }
}
