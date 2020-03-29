package com.maquinesinsta;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class myDialogs {

    public static void snackBar(View view, String sentence){
        View parentLayout = view.findViewById(android.R.id.content);
        Snackbar snack = Snackbar.make(parentLayout, sentence, Snackbar.LENGTH_LONG);

        // Cambiamos el color de fondo del snackbar.
        View sbv = snack.getView();
        sbv.setBackgroundColor(Color.parseColor("#f6070a"));

        snack.show();
    }

}
