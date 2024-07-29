package com.example.myapplication.ui.historique;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

    public class HistoriqueViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HistoriqueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}