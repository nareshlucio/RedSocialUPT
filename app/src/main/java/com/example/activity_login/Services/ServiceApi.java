package com.example.activity_login.Services;

import com.example.activity_login.Models.Peticion_Login;
import com.example.activity_login.Models.Peticion_Registro;
import com.example.activity_login.Models.Peticion_Usuarios;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServiceApi {
    @FormUrlEncoded
    @POST("api/loginSocial")
    Call<Peticion_Login> IniciarSesion(@Field("username") String name, @Field("password") String pass);

    @FormUrlEncoded
    @POST("api/crearUsuario")
    Call<Peticion_Registro> Registrase(@Field("username") String name, @Field("password") String pass);

    @FormUrlEncoded
    @POST("api/todosUsuarios")
    Call<Peticion_Usuarios> TodoslosUsuarios(@Field("") String s);

}
