package com.example.activity_login.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity_login.API.API;
import com.example.activity_login.Models.Peticion_DetallesU;
import com.example.activity_login.Models.Peticion_Usuarios;
import com.example.activity_login.R;
import com.example.activity_login.Services.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detalles_usuario extends AppCompatActivity {
    TextView usuario, creacion, actua, id;
    ImageButton btnRegresar;
    Peticion_Usuarios.Usuarios Usuario;
    ServiceApi servicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_usuario);
        getSupportActionBar().hide();
        //Iniciamos las variables
        servicio = API.getApi(this).create(ServiceApi.class);
        id = findViewById(R.id.txtvId);
        usuario = findViewById(R.id.txtvUsuario);
        creacion = findViewById(R.id.txtvCreacion);
        actua = findViewById(R.id.txtvActualizacion);
        btnRegresar = findViewById(R.id.ibtnAtras);
        //fin de la inicializacion de Variables
        //boton para regresar al Dashboard
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(detalles_usuario.this, Dashboard.class));
                finish();
            }
        });
        //Recibimos los datos de la informacion del usuario
        Intent intent = getIntent();
        //Comprobamos que los datos no esten vacios
        if(intent.getExtras() != null){
            //concentramos los datos en un objeto del mismo
            Usuario = (Peticion_Usuarios.Usuarios) intent.getSerializableExtra("data");
            //declaramos strings para desfragmentar el objeto
            String Id, Usuarion, Creacion, Actu;
            //guardamos sus valores en las variables
            Id = "Id Usuario: "+Usuario.getId();
            Usuarion = "Username: "+Usuario.getUsername();
            Creacion = "Fecha de Creacion: "+Usuario.getCreacion();
            Actu = "Ultima Actualizacion: "+Usuario.getActualizacion();
            //mostramos dichos datos en la vista
            id.setText(Id);
            usuario.setText(Usuarion);
            creacion.setText(Creacion);
            actua.setText(Actu);
            Peticion_DetallesUsuario(Id);
        }else{
            //Si no recibimos datos mandamos un mensaje de error y redireccionamos de nuevo a dashboard
            Toast.makeText(this, "Ocurrio un error al obtener los datos", Toast.LENGTH_LONG).show();
            startActivity(new Intent(detalles_usuario.this, Dashboard.class));
            finish();
        }
    }
    //Metodo para obtener detalles del usuario por medio de la API
    public void Peticion_DetallesUsuario(String id){
        //mandamos a la API el id para que nos regrese informacion de ese usuario
        servicio.DetalleUsuario(id).enqueue(new Callback<Peticion_DetallesU>() {
            @Override
            public void onResponse(Call<Peticion_DetallesU> call, Response<Peticion_DetallesU> response) {
                if (response.isSuccessful()){
                    if(response.body().isEstado()){
                        Toast.makeText(detalles_usuario.this, "El nombre del Usuario es: "+response.body().getUsuario(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(detalles_usuario.this, "No se encuentra un usuario con esa informacion", Toast.LENGTH_LONG).show();
                    }
                }else
                    Toast.makeText(detalles_usuario.this, "Ocurrio un error al obtener los datos del usuario", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Peticion_DetallesU> call, Throwable t) {
                Log.e("Error API: ", "El error fue causado por: "+t.getCause());
                Toast.makeText(detalles_usuario.this, "Ocurrio un error al conectarnos a nuestros servidores", Toast.LENGTH_LONG).show();
            }
        });
    }
}
