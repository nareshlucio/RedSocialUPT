package com.example.activity_login.Models;

public class Peticion_Registro {

    public boolean estado;
    public String correo;
    public String password;
    public String detalle;

    public Peticion_Registro(String correo, String password){
        this.correo=correo;
        this.password=password;

    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

}
