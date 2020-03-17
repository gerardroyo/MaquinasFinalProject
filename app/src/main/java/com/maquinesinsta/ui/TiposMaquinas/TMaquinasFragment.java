package com.maquinesinsta.ui.TiposMaquinas;

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

public class TMaquinasFragment extends Fragment {

    private TMaquinasViewModel tMaquinasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tMaquinasViewModel =
                ViewModelProviders.of(this).get(TMaquinasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tmaquinas, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        tMaquinasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}