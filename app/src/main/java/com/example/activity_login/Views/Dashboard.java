package com.example.activity_login.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.activity_login.API.API;
import com.example.activity_login.MainActivity;
import com.example.activity_login.Models.ListAdapterUsuarios;
import com.example.activity_login.Models.Peticion_Registro;
import com.example.activity_login.Models.Peticion_Usuarios;
import com.example.activity_login.R;
import com.example.activity_login.Services.ServiceApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements ListAdapterUsuarios.clickDetalles {

    RecyclerView rvLista;
    ListAdapterUsuarios listAdapterUsuarios;
    ServiceApi servicio;
    Button btnAdios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        //Inicializamos las veriables
        servicio = API.getApi(Dashboard.this).create(ServiceApi.class);
        btnAdios = findViewById(R.id.btnSalir);
        rvLista = findViewById(R.id.rvLista);
        //Finalizamos la inicializacion
        //Configuramos nuestro RecycleView
        rvLista.setHasFixedSize(true);
        rvLista.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        listAdapterUsuarios = new ListAdapterUsuarios(this::ClickUsuario);//creamos un objeto de nuestro adaptador para el RecycleView
        Peticion();//Realizamos la peticon a la API
        //Boton para desloggearse
        btnAdios.setOnClickListener(v -> {
            BorrarPreferencias();//Borramos las Preferencias si es que desea salir
            startActivity(new Intent(Dashboard.this, MainActivity.class));
            finish();
        });
    }
    //Funcion para la Peticion de todos los Usuarios
    public void Peticion(){
        //Hacemos la peticion a la API para recibir los datos de los usuarios
        servicio.TodoslosUsuarios("s").enqueue(new Callback<Peticion_Usuarios>() {
            @Override
            public void onResponse(Call<Peticion_Usuarios> call, Response<Peticion_Usuarios> response) {
                if(response.isSuccessful()){ //si la Peticion fue exitosa
                    //Creamos un objeto de la peticion
                    Peticion_Usuarios peticion = response.body();
                    //Creamos la lista de objetos que nos da la peticion
                    List<Peticion_Usuarios.Usuarios> usuarios = peticion.getUsuarios();
                    //Guardamos la lista en el adaptador
                    listAdapterUsuarios.setData(usuarios);
                    //Le asignamos el al adaptador al recycleView
                    rvLista.setAdapter(listAdapterUsuarios);

                }else {
                    //Mandamos un mensaje en consola y al usaurio de que ocrrurio un error
                    Log.e("Api Error: ", response.message() + " errorBody:" + response.errorBody());
                    Toast.makeText(Dashboard.this, "A Ocurrido un error al procesar a los usuarios", Toast.LENGTH_LONG).show();
                    Peticion();//Volvemos a hacer la peticion
                }
            }

            @Override
            public void onFailure(Call<Peticion_Usuarios> call, Throwable t) {
                Peticion();//Volvemos a hacer una peticion
                //Mandamos un mensaje al usuario y a la consola de que Hubo algun error
                Log.e("Api Error: ", t.getMessage() +" "+ t.getCause());
                Toast.makeText(Dashboard.this, "A Ocurrido Al conectarse con el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void ClickUsuario(Peticion_Usuarios.Usuarios Usuarios) {
        startActivity(new Intent(Dashboard.this, detalles_usuario.class).putExtra("data",Usuarios));
    }

    public void BorrarPreferencias(){

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TOKEN", "");
        editor.commit();
    }
}
