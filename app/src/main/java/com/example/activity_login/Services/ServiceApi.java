package com.example.activity_login.Services;

import com.example.activity_login.Models.Peticion_DetallesU;
import com.example.activity_login.Models.Peticion_Login;
import com.example.activity_login.Models.Peticion_Registro;
import com.example.activity_login.Models.Peticion_Usuarios;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServiceApi {
    //Peticion a la API de Login
    @FormUrlEncoded
    @POST("api/loginSocial")
    Call<Peticion_Login> IniciarSesion(@Field("username") String name, @Field("password") String pass);
    //Peticion a la API de Registro de Usuarios
    @FormUrlEncoded
    @POST("api/crearUsuario")
    Call<Peticion_Registro> Registrase(@Field("username") String name, @Field("password") String pass);
    //Peticion a la API de Solicitar todos los Usuarios
    @FormUrlEncoded
    @POST("api/todosUsuarios")
    Call<Peticion_Usuarios> TodoslosUsuarios(@Field("") String s);
    //Peticion a la API de Detalle de algun Usuario
    @FormUrlEncoded
    @POST("api/detallesUsuario")
    Call<Peticion_DetallesU> DetalleUsuario(@Field("usuarioId") String s);

}
