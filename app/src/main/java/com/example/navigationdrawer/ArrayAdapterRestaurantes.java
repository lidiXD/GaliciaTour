package com.example.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

public class ArrayAdapterRestaurantes extends ArrayAdapter<Restaurantes> {
    Activity activity;
    int idlayout;
    List <Restaurantes> dondecomer;
    HashMap<String, Integer> imagenesMap;

    static class Ref{
        TextView nombre;
        TextView descripcion;
        ImageView img_imagen;

    }


    public ArrayAdapterRestaurantes(@NonNull Context context, int resource, @NonNull List<Restaurantes> dondecomer) {
        super(context, resource, dondecomer);
        activity= (Activity) context;
        idlayout=resource;
        this.dondecomer=dondecomer;

        imagenesMap = new HashMap<>();
        imagenesMap.put("Casa dos xacobes", R.drawable.casa_dos_xacobes);
        imagenesMap.put("Restaurante Abrasador Gaia Medieval",R.drawable.abrasador_gaia);
        imagenesMap.put("Sicilia in Bocca",R.drawable.sicilia_in_bocca);
        imagenesMap.put("Mesón 42",R.drawable.meson_42);
        imagenesMap.put("Garum Bistro",R.drawable.garum_bistro);

        imagenesMap.put("Epoca Restaurante Peruano Fusión",R.drawable.peruano_fusion);
        imagenesMap.put("Os Padróns",R.drawable.os_padrons);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArrayAdapterRestaurantes.Ref ref;
        View layout;
        if(convertView==null){
            layout=activity.getLayoutInflater().inflate(idlayout,null);
            TextView nombre=layout.findViewById(R.id.tv_nombre_restaurante);
            TextView descripcion=layout.findViewById(R.id.tv_descripcion_restaurante);
            ImageView img_imagen=layout.findViewById(R.id.imageView);

            ref=new ArrayAdapterRestaurantes.Ref();
            ref.nombre=nombre;
            ref.descripcion=descripcion;
            ref.img_imagen=img_imagen;

            layout.setTag(ref);
        }
        else{
            layout=convertView;
            ref= (ArrayAdapterRestaurantes.Ref) layout.getTag();
        }
        Restaurantes dondeComer=dondecomer.get(position);
        ref.nombre.setText(dondeComer.getNombre());
        ref.descripcion.setText(dondeComer.getDescripcion());
        Integer imagenId = imagenesMap.get(dondeComer.getNombre());
        if (imagenId != null) {
            ref.img_imagen.setImageResource(imagenId);
        } else {
            ref.img_imagen.setImageResource(0);
        }

        return layout;
    }

}
