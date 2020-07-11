package com.example.travel.ui.cities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CitiesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CitiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is city fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}