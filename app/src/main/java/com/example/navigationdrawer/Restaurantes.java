package com.example.navigationdrawer;

public class Restaurantes {

    private String nombre;
    private String descripcion;
    private Integer idLocalidad;

    public Restaurantes(String nombre, String descripcion, Integer idLocalidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idLocalidad = idLocalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
    }
}
