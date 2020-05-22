package com.example.activity_login.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Peticion_Usuarios implements Serializable{
    private boolean estado;
    private List<Usuarios> usuarios= new ArrayList<>();

    public class Usuarios implements Serializable{
        int id;
        String username;
        @SerializedName("created_at")
        @Expose
        String creacion;
        @SerializedName("updated_at")
        @Expose
        String actualizacion;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCreacion() {
            return creacion;
        }

        public void setCreacion(String creacion) {
            this.creacion = creacion;
        }

        public String getActualizacion() {
            return actualizacion;
        }

        public void setActualizacion(String actualizacion) {
            this.actualizacion = actualizacion;
        }
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }
}
