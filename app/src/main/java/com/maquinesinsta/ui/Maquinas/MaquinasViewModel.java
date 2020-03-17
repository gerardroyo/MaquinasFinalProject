package com.maquinesinsta.ui.Maquinas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MaquinasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MaquinasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MÁQUINAS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}