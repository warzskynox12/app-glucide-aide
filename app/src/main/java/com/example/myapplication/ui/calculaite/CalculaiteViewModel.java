package com.example.myapplication.ui.calculaite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculaiteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CalculaiteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}