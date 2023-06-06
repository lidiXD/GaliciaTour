package com.example.navigationdrawer;

import android.widget.ImageButton;

public class QueverDondeComer {
    private String nombreLocalidad;
    private ImageButton que_ver;
    private ImageButton donde_comer;

    public QueverDondeComer(String nombreLocalidad, ImageButton que_ver, ImageButton donde_comer) {
        this.nombreLocalidad = nombreLocalidad;
        this.que_ver = que_ver;
        this.donde_comer = donde_comer;
    }

    public String getNombreLocalidad() {
        return nombreLocalidad;
    }

    public void setNombreLocalidad(String nombreLocalidad) {
        this.nombreLocalidad = nombreLocalidad;
    }

    public ImageButton getQue_ver() {
        return que_ver;
    }

    public void setQue_ver(ImageButton que_ver) {
        this.que_ver = que_ver;
    }

    public ImageButton getDonde_comer() {
        return donde_comer;
    }

    public void setDonde_comer(ImageButton donde_comer) {
        this.donde_comer = donde_comer;
    }
}