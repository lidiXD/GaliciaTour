package com.example.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

public class ArrayAdapterLocalidades extends ArrayAdapter<Localidades> {
    Activity activity;
    int idlayout;
    List <Localidades> localidades;
    HashMap<String, Integer> imagenesMap;

    static class Ref{
        TextView nombre;
        TextView descripcion;
        Button btn_ver_mapa;
        ImageView img_imagen;

    }

    public ArrayAdapterLocalidades(@NonNull Context context, int resource, @NonNull List<Localidades> localidades) {
        super(context, resource, localidades);
        activity= (Activity) context;
        idlayout=resource;
        this.localidades=localidades;

        imagenesMap = new HashMap<>();
        imagenesMap.put("Castillo de San Sebastián en Vigo", R.drawable.san_sebastian);
        imagenesMap.put("Museo Catedralicio de Santiago de Compostela", R.drawable.museo_santiago);
        imagenesMap.put("Sireno de Vigo y la Puerta del Sol", R.drawable.sireno);
        imagenesMap.put("El Puerto de Vigo",R.drawable.puerto_vigo);
        imagenesMap.put("Concatedral de Santa María de Vigo",R.drawable.santa_maria);
        imagenesMap.put("Islas Cíes",R.drawable.islas_cies);
        imagenesMap.put("VigoZoo",R.drawable.vigozoo);
        imagenesMap.put("Playa de Samil",R.drawable.playa_samil);
        imagenesMap.put("Playa de O Vao",R.drawable.playa_o_vao);
        imagenesMap.put("Playa de Tombo do Gato",R.drawable.playa_tombo_do_gato);

        imagenesMap.put("Catedral de Santiago de Compostela", R.drawable.catedral_santiago);
        imagenesMap.put("Mercado de Abastos en Santiago de Compostela", R.drawable.abastos);
        imagenesMap.put("Universidad de Santiago de Compostela (USC)",R.drawable.usc);
        imagenesMap.put("Monte do Gozo",R.drawable.monte_do_gozo);
        imagenesMap.put("Parque de la Alameda",R.drawable.parque_alameda);
        imagenesMap.put("Parque de Belvís",R.drawable.parque_belvis);
        imagenesMap.put("Seminario Mayor",R.drawable.seminario_mayor);
        imagenesMap.put("Seminario Menor",R.drawable.seminario_menor);


        imagenesMap.put("Muralla Romana de Lugo",R.drawable.muralla_lugo);
        imagenesMap.put("Catedral de Lugo",R.drawable.catedral_lugo);
        imagenesMap.put("Plaza Mayor de Lugo",R.drawable.plaza_mayor_lugo);
        imagenesMap.put("Bosque da Fervenza en Lugo",R.drawable.bosque_fervenza);
        imagenesMap.put("Plaza de Santa María",R.drawable.plaza_santa_maria);
        imagenesMap.put("Playa de las Catedrales",R.drawable.playa_catedrales);
        imagenesMap.put("Casa dos Mosaicos",R.drawable.casa_dos_mosaicos);
        imagenesMap.put("Museo Universitario «A Domus do Mitreo»",R.drawable.domus_do_mitreo);
        imagenesMap.put("Cañón y riberas del Sil",R.drawable.canhon_sil);


        imagenesMap.put("Castillo de San Felipe en Ferrol",R.drawable.castillo_san_felipe);
        imagenesMap.put("Ermita de Chamorro",R.drawable.chamorro);
        imagenesMap.put("Puerto de Ferrol",R.drawable.puerto_ferrol);


        imagenesMap.put("Torre de Hércules",R.drawable.torre_hercules);
        imagenesMap.put("Casa de las Ciencias en A Coruña",R.drawable.casa_ciencias);
        imagenesMap.put("Aquarium Finisterrae",R.drawable.aquarium_finisterrae);
        imagenesMap.put("Domus-Casa del Hombre en A Coruña",R.drawable.domus);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArrayAdapterLocalidades.Ref ref;
        View layout;
        if(convertView==null){
            layout=activity.getLayoutInflater().inflate(idlayout,null);
            TextView nombre=layout.findViewById(R.id.tv_nombre);
            TextView descripcion=layout.findViewById(R.id.tv_descripcion);
            Button btn_ver_mapa=layout.findViewById(R.id.btn_ver_mapa);
            ImageView img_imagen=layout.findViewById(R.id.img_imagen);

            ref=new ArrayAdapterLocalidades.Ref();
            ref.nombre=nombre;
            ref.descripcion=descripcion;
            ref.btn_ver_mapa=btn_ver_mapa;
            ref.img_imagen=img_imagen;

            layout.setTag(ref);
        }
        else{
            layout=convertView;
            ref= (ArrayAdapterLocalidades.Ref) layout.getTag();
        }
        Localidades localidad=localidades.get(position);
        ref.nombre.setText(localidad.getNombre());
        ref.descripcion.setText(localidad.getDescripcion());
        ref.btn_ver_mapa.setText("Ver mapa");
        ref.btn_ver_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirMapa(localidad.getNombre());
            }
        });
        Integer imagenId = imagenesMap.get(localidad.getNombre());
        if (imagenId != null) {
            ref.img_imagen.setImageResource(imagenId);
        } else {
            ref.img_imagen.setImageResource(0);
        }

        return layout;
    }
    private void abrirMapa(String nombreLocalidad) {
        String uri = "geo:0,0?q=" + nombreLocalidad;
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        } else {
            Toast.makeText(activity, "No se encontró la aplicación de Google Maps", Toast.LENGTH_SHORT).show();
        }
    }
}
