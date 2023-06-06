package com.example.navigationdrawer.ui.fragmento3;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.navigationdrawer.ArrayAdapterRestaurantes;
import com.example.navigationdrawer.Localidades;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.Restaurantes;
import com.example.navigationdrawer.TurismoGaliciaDB;

import java.util.ArrayList;
import java.util.List;


public class Fragmento3 extends Fragment {

    ListView listviewdatosRestaurantes;
    ArrayAdapterRestaurantes adapterRestaurantes;
    int localidadId;

    SQLiteDatabase db;
    SharedPreferences preferencias;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fragmento3, container, false);

        Bundle argumentos = getArguments();
        if (argumentos != null) {
            localidadId = argumentos.getInt("id_localidad");
        }

        TurismoGaliciaDB dbHelper = new TurismoGaliciaDB(getActivity());
        db = dbHelper.abreBD();

        List<Restaurantes> restaurantes = new ArrayList<>();
        preferencias=getActivity().getSharedPreferences("preferencias.xml",MODE_PRIVATE);

        localidadId = preferencias.getInt("id_localidad", -1);

        if (localidadId != -1) {
            restaurantes = get_restaurantes();


        } else {
            restaurantes = getTodosLosRestaurantes();
        }

        adapterRestaurantes = new ArrayAdapterRestaurantes(getActivity(),R.layout.item_listview_datos_restaurantes,restaurantes);
        listviewdatosRestaurantes = root.findViewById(R.id.listviewDatosRestaurantes);
        listviewdatosRestaurantes.setAdapter(adapterRestaurantes);

        return root;
    }


    public List<Restaurantes> get_restaurantes() {
        List<Restaurantes> restaurantes = new ArrayList<>();

        String get_restaurantes = "SELECT * FROM donde_comer WHERE id_localidad = " + localidadId;
        Cursor cursor = db.rawQuery(get_restaurantes, null);
        if (cursor.moveToFirst()) {

            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre_restaurante"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion_restaurante"));
                Integer idLocalidad = cursor.getInt(cursor.getColumnIndexOrThrow("id_localidad"));
                restaurantes.add(new Restaurantes(nombre,descripcion,idLocalidad));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return restaurantes;
    }

    public List<Restaurantes> getTodosLosRestaurantes() {
        List<Restaurantes> restaurantes = new ArrayList<>();

        String get_restaurantes = "SELECT * FROM donde_comer";
        Cursor cursor = db.rawQuery(get_restaurantes, null);

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre_restaurante"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion_restaurante"));
                Integer idLocalidad = cursor.getInt(cursor.getColumnIndexOrThrow("id_localidad"));
                restaurantes.add(new Restaurantes(nombre,descripcion,idLocalidad));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return restaurantes;
    }

}