package com.maquinesinsta.ui.TiposMaquinas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.maquinesinsta.AddUpdateTipos;
import com.maquinesinsta.DataBase.MaquinasDatasource;
import com.maquinesinsta.R;

import static android.app.Activity.RESULT_OK;

public class TMaquinasFragment extends Fragment{
    private static int ACTIVITY_TIPOS_ADD = 1;
    private static int ACTIVITY_TIPOS_UPDATE = 2;
    //private static int ACTIVITY_TIPOS_HISTORIAL = 3;
    private long idActual;

    private TMaquinasViewModel tMaquinasViewModel;
    private adapterTipos scTipos;

    public MaquinasDatasource bd;

    private static String[] from = new String[]{MaquinasDatasource.TIPOS_NOMBRE, MaquinasDatasource.TIPOS_COLOR};
    private static int[] to = new int[]{R.id.lblNombre, R.id.lblColor};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tMaquinasViewModel = ViewModelProviders.of(this).get(TMaquinasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tmaquinas, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);

        bd = new MaquinasDatasource(getActivity());
        loadTipos(root);

        Button btnDelete = (Button) root.findViewById(R.id.btnAddTipos);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTipo();
            }
        });

        return root;
    }

    public void addTipo() {
        Bundle bundle = new Bundle();
        bundle.putLong("id",-1);

        idActual = -1;

        Intent i = new Intent(getActivity(), AddUpdateTipos.class );
        i.putExtras(bundle);
        startActivityForResult(i,ACTIVITY_TIPOS_ADD);

    }

    public void loadTipos(View root) {

        Cursor cursorTipos = bd.returnAllTipos();

        // Now create a simple cursor adapter and set it to display
        scTipos = new adapterTipos(getActivity(), R.layout.row_tmaquinas, cursorTipos, from, to, 1);

        ListView lv = (ListView) root.findViewById(R.id.lvTipos);
        lv.setAdapter(scTipos);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        updateTipos(id);
                    }
                }
        );

    }

    public void updateTipos(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);

        idActual = id;

        Intent i = new Intent(getActivity(), AddUpdateTipos.class );
        i.putExtras(bundle);
        startActivityForResult(i,ACTIVITY_TIPOS_UPDATE);
    }

    public void refreshTipos() {

        Cursor cursorTipos = null;
        cursorTipos = bd.returnAllTipos();

        scTipos.changeCursor(cursorTipos);
        scTipos.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_TIPOS_ADD) {
            if (resultCode == RESULT_OK) {
                // Carreguem totes les tasques a lo bestia
                refreshTipos();
            }
        }

        if (requestCode == ACTIVITY_TIPOS_UPDATE) {
            if (resultCode == RESULT_OK) {
                refreshTipos();
            }
        }

        /*if (requestCode == ACTIVITY_TIPOS_HISTORIAL) {
            if (resultCode == RESULT_OK) {
                refreshTasks();
            }
        }*/

    }

}

class adapterTipos extends android.widget.SimpleCursorAdapter {

    Context contexto;

    public adapterTipos(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        contexto = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        return view;
    }

}