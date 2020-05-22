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
import android.view.View;
import android.widget.Button;
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
        servicio = API.getApi(Dashboard.this).create(ServiceApi.class);
        listAdapterUsuarios = new ListAdapterUsuarios(this::ClickUsuario);
        btnAdios = findViewById(R.id.btnSalir);
        rvLista = findViewById(R.id.rvLista);
        Peticion();
        rvLista.setHasFixedSize(true);
        rvLista.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        btnAdios.setOnClickListener(v -> {
            BorrarPreferencias();
            startActivity(new Intent(Dashboard.this, MainActivity.class));
            finish();
        });
    }

    public void Peticion(){
        servicio.TodoslosUsuarios("s").enqueue(new Callback<Peticion_Usuarios>() {
            @Override
            public void onResponse(Call<Peticion_Usuarios> call, Response<Peticion_Usuarios> response) {
                if(response.isSuccessful()){
                    Peticion_Usuarios peticion = response.body();
                    List<Peticion_Usuarios.Usuarios> usuarios = peticion.getUsuarios();
                    listAdapterUsuarios.setData(usuarios);
                    rvLista.setAdapter(listAdapterUsuarios);
                }else
                    Log.e("Api Error: ", response.message()+" "+ response.errorBody());
                    Toast.makeText(Dashboard.this, "A Ocurrido un error al procesar a los usuarios", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Peticion_Usuarios> call, Throwable t) {
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
