package com.example.app_inacap.Clases;

public class Usuario {
    private int id;
    private String pass;
    private String email;
    private  String nombre;

    public Usuario(int id, String pass, String email, String nombre) {
        this.id = id;
        this.pass = pass;
        this.email = email;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
