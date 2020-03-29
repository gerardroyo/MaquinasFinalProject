package com.maquinesinsta;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maquinesinsta.DataBase.MaquinasDatasource;

public class AddUpdateZonas extends AppCompatActivity {

    private long idZona;
    private MaquinasDatasource bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_zonas);

        Button btnOk = (Button) findViewById(R.id.btnOk);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnCancel = (Button) findViewById(R.id.btnCancelar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bd = new MaquinasDatasource(this);

        idZona = this.getIntent().getExtras().getLong("id");

        if (idZona != -1) {
            // Si estem modificant carreguem les dades en pantalla
            cargarDatos();
        }
        else {
            btnDelete.setVisibility(View.GONE);
            SpannableString s = new SpannableString("Añadir nueva Zona");
            s.setSpan(new TypefaceSpan("monospace"), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setTitle(s);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aceptarCambios();
            }
        });

        // Boton eliminar
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteZona();
            }
        });

        // Boton cancelar
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelarCambios();
            }
        });
    }

    private void aceptarCambios() {
        // Validem les dades
        TextView tv;

        // code ha d'estar informat
        tv = (TextView) findViewById(R.id.edtNombreZonas);
        String nombre = tv.getText().toString();
        if (nombre.trim().equals("")) {
            View parentLayout = findViewById(android.R.id.content);
            myDialogs.snackBar(parentLayout, "Todos los campos marcados con '*' son OBLIGATORIOS");
            return;
        }

        if (idZona == -1) {
            idZona = bd.addZona(nombre);
        } else {
            bd.updateZona(idZona, nombre);

            Intent mIntent = new Intent();
            mIntent.putExtra("id", idZona);
            setResult(RESULT_OK, mIntent);

            finish();
        }
    }

    private void cargarDatos() {

        // Demanem un cursor que retorna un sol registre amb les dades de la tasca
        Cursor datos = bd.oneRowZona(idZona);
        datos.moveToFirst();

        // Carreguem les dades en la interfície
        TextView tv;

        tv = (TextView) findViewById(R.id.edtNombreZonas);
        tv.setText(datos.getString(datos.getColumnIndex(MaquinasDatasource.ZONAS_NOMBRE)));

        SpannableString s = new SpannableString("Modificando: " + datos.getString(datos.getColumnIndex(MaquinasDatasource.ZONAS_NOMBRE)));
        s.setSpan(new TypefaceSpan("monospace"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);
    }

    private void cancelarCambios() {
        Intent mIntent = new Intent();
        mIntent.putExtra("id", idZona);
        setResult(RESULT_CANCELED, mIntent);

        finish();
    }

    private void deleteZona() {

        // Pedimos confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Desea eliminar la Zona?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.deleteZona(idZona);

                Intent mIntent = new Intent();
                mIntent.putExtra("id", -1);  // Devolvemos -1 indicant que s'ha eliminat
                setResult(RESULT_OK, mIntent);

                finish();
            }
        });

        builder.setNegativeButton("No", null);

        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
