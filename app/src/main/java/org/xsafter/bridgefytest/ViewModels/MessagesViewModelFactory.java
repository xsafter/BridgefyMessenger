package org.xsafter.bridgefytest.ViewModels;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.xsafter.bridgefytest.repositories.MessagesRepository;

import io.reactivex.annotations.NonNull;

public class MessagesViewModelFactory implements ViewModelProvider.Factory{
    private final Application application;

    public MessagesViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MessagesViewModel.class)) {
            return (T) new MessagesViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
