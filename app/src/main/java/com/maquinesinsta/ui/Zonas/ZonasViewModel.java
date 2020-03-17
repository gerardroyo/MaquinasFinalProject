package com.maquinesinsta.ui.Zonas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ZonasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ZonasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ZONAS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}