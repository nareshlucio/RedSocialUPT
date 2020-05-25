package com.example.activity_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity_login.API.API;
import com.example.activity_login.Models.Peticion_Login;
import com.example.activity_login.Services.ServiceApi;
import com.example.activity_login.Views.Activity_Registro;
import com.example.activity_login.Views.Dashboard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView txtvRegistrarse;
    Button btnIniciar;
    String APITOKEN;
    ServiceApi servicio;
    Switch sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //----------------------Inicializacion de Variables-----------------------------
        servicio = API.getApi(MainActivity.this).create(ServiceApi.class);
        txtvRegistrarse = findViewById(R.id.txtvRegistrar);
        final EditText edtCorreo = findViewById(R.id.edtEmail), edtPassword = findViewById(R.id.edtPassword);
        btnIniciar = findViewById(R.id.btnInicio);
        sesion = findViewById(R.id.swSesion);
        //-----------------------Fin de la Inicializacion de Variables------------------
        getSupportActionBar().hide();//Esconde el ActionBar
        verificarPreferencias();//Verifica si inicio Sesion Antes
        //Texto que manda a la actividad de Registro
        txtvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Manda a la Actividad de Registro
                startActivity(new Intent(MainActivity.this, Activity_Registro.class));
            }
        });
        //Boton Para Iniciar Sesion
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprobacion De los Campos
                if(!edtCorreo.getText().toString().equals("") && !edtPassword.getText().toString().equals("")){
                    Peticion(edtCorreo.getText().toString(), edtPassword.getText().toString());
                }else {//Mensaje de Alerta Si es que los campos estan vacios
                    Toast.makeText(MainActivity.this, "No Puedes Dejar los Campos Vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //Metodo para Realizar Peticion a la API
    public void Peticion(String correo, String password){
        //Se Hace la Peticion
        servicio.IniciarSesion(correo, password).enqueue(new Callback<Peticion_Login>() {
            @Override
            public void onResponse(Call<Peticion_Login> call, Response<Peticion_Login> response) {
                //Si la peticion Fue Exitosa
                if(response.isSuccessful()){
                    //Si el estado es true
                    if(response.body().getEstado()) {
                        //Guardamos Token
                        APITOKEN = response.body().getToken();
                        //Comprobamos si el switch de guardar sesion esta activo
                        if(sesion.isChecked())
                        GuardarPreferencias(APITOKEN);
                        //Pasamos a la siguiente actividad
                        Toast.makeText(MainActivity.this, "Bienvenido!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, Dashboard.class));
                        //Finalizamos Actividad de Login
                        finish();
                    }else
                        Toast.makeText(MainActivity.this, "Nombre de Usuario y/o Contraseña incorrecta", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(MainActivity.this, "A Ocurrido un error al intentar iniciar sesion", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Peticion_Login> call, Throwable t) {
                Log.e("Error API", t.getLocalizedMessage());
                Log.e("Error Api", call.request().body().toString());
                Toast.makeText(MainActivity.this, "A Ocurrido un error al conectar con el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }
    //Usamos metodo para Guardar el token para guardar la sesion
    public void GuardarPreferencias(String token){

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TOKEN", token);
        editor.commit();
    }
    //Funcion para porder verificar si existe alguna sesion guardada
    private void verificarPreferencias(){
        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String token = preferencias.getString("TOKEN", "");
        if(token != ""){
            Toast.makeText(MainActivity.this,"Bienvenido de Nuevo :)",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, Dashboard.class));
            finish();
        }
    }
}
