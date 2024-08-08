package com.example.myapplication.ui.ratio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RatioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RatioViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}