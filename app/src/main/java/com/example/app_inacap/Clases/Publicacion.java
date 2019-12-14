package com.example.app_inacap.Clases;

public class Publicacion {

    private int id_publicacion;
    private int id_user;
    private String texto;

    public String getNombre() {
        return nombre;
    }

    private String nombre;

    public Publicacion(int id_publicacion, int id_user, String texto) {
        this.id_publicacion = id_publicacion;
        this.id_user = id_user;
        this.texto = texto;
    }

    public Publicacion(String nombre, String texto) {

        this.nombre = nombre;
        this.texto = texto;
    }

    public int getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String toString(){
        return  "User:  "+this.getNombre()+
                "\nTexto:  "+this.getTexto();
    }

}
