package com.maquinesinsta.ui.TiposMaquinas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TMaquinasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TMaquinasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is TIPOS MÁQUINAS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}