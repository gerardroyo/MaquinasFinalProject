package com.maquinesinsta.ui.Maquinas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.maquinesinsta.R;

public class MaquinasFragment extends Fragment {

    private MaquinasViewModel maquinasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        maquinasViewModel =
                ViewModelProviders.of(this).get(MaquinasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_maquinas, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        maquinasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}