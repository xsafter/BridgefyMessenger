package org.xsafter.bridgefytest.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.xsafter.bridgefytest.model.Message;
import org.xsafter.bridgefytest.model.Peer;
import org.xsafter.bridgefytest.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel extends AndroidViewModel {
    private MessagesRepository messagesRepository;
    private final LiveData<List<Message>> messages;

    public MessagesViewModel(Application application) {
        super(application);
        messagesRepository = new MessagesRepository(application);
        messages = messagesRepository.getAllMessages();
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public void insertMessageForUser(Peer peer, Message message) {
        messagesRepository.insertMessageForUser(peer, message);
    }
}
