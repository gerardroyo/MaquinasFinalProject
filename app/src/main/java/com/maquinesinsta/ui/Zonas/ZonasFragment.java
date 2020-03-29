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

import com.maquinesinsta.AddUpdateZonas;
import com.maquinesinsta.DataBase.MaquinasDatasource;
import com.maquinesinsta.R;
import com.maquinesinsta.ui.Zonas.ZonasViewModel;

import static android.app.Activity.RESULT_OK;

public class ZonasFragment extends Fragment {

    private ZonasViewModel zonasViewModel;
    private adapterZonas scZonas;

    public MaquinasDatasource bd;

    private static int ACTIVITY_ZONAS_ADD = 1;
    private static int ACTIVITY_ZONAS_UPDATE = 2;
    //private static int ACTIVITY_ZONAS_HISTORIAL = 3;
    private long idActual;

    private static String[] from = new String[]{MaquinasDatasource.TIPOS_NOMBRE};
    private static int[] to = new int[]{R.id.lblNombreZona};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        zonasViewModel = ViewModelProviders.of(this).get(ZonasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_zonas, container, false);
        //final TextView textView = root.findViewById(R.id.text_notification);

        bd = new MaquinasDatasource(getActivity());
        loadZona(root);

        Button btnDelete = (Button) root.findViewById(R.id.btnAddZona);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addZona();
            }
        });

        return root;
    }

    public void addZona() {
        Bundle bundle = new Bundle();
        bundle.putLong("id",-1);

        idActual = -1;

        Intent i = new Intent(getActivity(), AddUpdateZonas.class );
        i.putExtras(bundle);
        startActivityForResult(i,ACTIVITY_ZONAS_ADD);

    }

    public void loadZona(View root) {

        Cursor cursorZonas = bd.returnAllZonas();

        // Now create a simple cursor adapter and set it to display
        scZonas = new adapterZonas(getActivity(), R.layout.row_zonas, cursorZonas, from, to, 1);

        ListView lv = (ListView) root.findViewById(R.id.lvZonas);
        lv.setAdapter(scZonas);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        updateZona(id);
                    }
                }
        );
    }

    public void updateZona(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);

        idActual = id;

        Intent i = new Intent(getActivity(), AddUpdateZonas.class );
        i.putExtras(bundle);
        startActivityForResult(i,ACTIVITY_ZONAS_UPDATE);
    }

    public void refreshZona() {

        Cursor cursorTipos = null;
        cursorTipos = bd.returnAllZonas();

        scZonas.changeCursor(cursorTipos);
        scZonas.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_ZONAS_ADD) {
            if (resultCode == RESULT_OK) {
                // Carreguem totes les tasques a lo bestia
                refreshZona();
            }
        }

        if (requestCode == ACTIVITY_ZONAS_UPDATE) {
            if (resultCode == RESULT_OK) {
                refreshZona();
            }
        }

        /*if (requestCode == ACTIVITY_TIPOS_HISTORIAL) {
            if (resultCode == RESULT_OK) {
                refreshTasks();
            }
        }*/

    }

}

class adapterZonas extends android.widget.SimpleCursorAdapter {

    Context contexto;

    public adapterZonas(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        contexto = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        return view;
    }

}