package com.example.navigationdrawer.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.navigationdrawer.ArrayAdapterLocalidades_ListView;
import com.example.navigationdrawer.QueverDondeComer;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.TurismoGaliciaDB;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ArrayAdapterLocalidades_ListView.OnButtonClickListener{
ListView lisviewlugaresgalicia;
private SimpleCursorAdapter sc_adaptador;
ArrayAdapterLocalidades_ListView adapterLocalidades_listView;
SQLiteDatabase db;
TurismoGaliciaDB TurismoGaliciaDB;
Cursor cursor;
SharedPreferences preferencias;
OnLocalidadSeleccionadaListener listener;
String localidadNombre;
int localidadId;


    public interface OnLocalidadSeleccionadaListener {
        void onLocalidadSeleccionada(String localidadNombre);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnLocalidadSeleccionadaListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar OnLocalidadSeleccionadaListener");
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);




        TurismoGaliciaDB dbHelper = new TurismoGaliciaDB(getActivity());
        db = dbHelper.abreBD();


        List<QueverDondeComer> lugares_galicia = new ArrayList<>();
        lugares_galicia=get_lugares_galicia();
        preferencias=getActivity().getSharedPreferences("preferencias.xml",MODE_PRIVATE);

        adapterLocalidades_listView = new ArrayAdapterLocalidades_ListView(getActivity(), R.layout.item_listview, lugares_galicia);
        adapterLocalidades_listView.setOnButtonClickListener(this);
        lisviewlugaresgalicia = root.findViewById(R.id.listViewLocalidades);
        lisviewlugaresgalicia.setAdapter(adapterLocalidades_listView);

        return root;



    }

    public List<QueverDondeComer> get_lugares_galicia() {
        List<QueverDondeComer> lugares_galicia = new ArrayList<>();
        String get_lugares_galicia = "SELECT * FROM localidades";
        cursor = db.rawQuery(get_lugares_galicia, null);
        if (cursor.moveToFirst()) {
            do {
                String localidad = cursor.getString(cursor.getColumnIndexOrThrow("localidad"));
                ImageButton btn_que_ver = new ImageButton(getContext());
                btn_que_ver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                ImageButton btn_donde_comer = new ImageButton(getContext());
                lugares_galicia.add(new QueverDondeComer(localidad, btn_que_ver, btn_donde_comer));
            } while (cursor.moveToNext());
        }

        return lugares_galicia;
    }
    @Override
    public void onQueVerClick(int position) {
        cursor.moveToPosition(position);
        localidadId = cursor.getInt(cursor.getColumnIndexOrThrow("id_localidad"));
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        localidadNombre = cursor.getString(cursor.getColumnIndexOrThrow("localidad"));
        listener.onLocalidadSeleccionada(localidadNombre);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_localidad",localidadId);
        editor.putString("localidad",localidadNombre);
        editor.apply();

        Bundle bundle = new Bundle();
        bundle.putInt("id_localidad", localidadId);
        bundle.putString("fragmentoDestino", "fragmento2");
        navController.navigate(R.id.fragmento2, bundle);
    }

    @Override
    public void onDondeComerClick(int position) {
        cursor.moveToPosition(position);
        localidadId = cursor.getInt(cursor.getColumnIndexOrThrow("id_localidad"));
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        localidadNombre = cursor.getString(cursor.getColumnIndexOrThrow("localidad"));
        listener.onLocalidadSeleccionada(localidadNombre);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_localidad",localidadId);
        editor.putString("localidad",localidadNombre);
        editor.apply();

        Bundle bundle = new Bundle();
        bundle.putInt("id_localidad", localidadId);
        bundle.putString("fragmentoDestino", "fragmento3");
        navController.navigate(R.id.fragmento3, bundle);
    }

        @Override
    public void onPause() {
        super.onPause();


    }
}