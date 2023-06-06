package com.example.navigationdrawer;

import android.widget.Button;

public class Localidades {
    private String nombre;
    private String descripcion;
    private Integer idLocalidad;
    private Button btn_ver_mapa;


    public Localidades(String nombre, String descripcion, Integer idLocalidad, Button btn_ver_mapa) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idLocalidad=idLocalidad;
        this.btn_ver_mapa=btn_ver_mapa;
    }


    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }


}
