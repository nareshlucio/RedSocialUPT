package com.example.activity_login.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity_login.API.API;
import com.example.activity_login.MainActivity;
import com.example.activity_login.Models.Peticion_Registro;
import com.example.activity_login.R;
import com.example.activity_login.Services.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Registro extends AppCompatActivity {

    TextView txtvRegresar;
    Button btnRegistrase;
    EditText edtUsername, edtPassword, edtPass2;
    ServiceApi servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__registro);
        servicio = API.getApi(Activity_Registro.this).create(ServiceApi.class);
        txtvRegresar = findViewById(R.id.txtvRegresar);
        btnRegistrase = findViewById(R.id.btnRegistrarse);
        edtUsername = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        edtPass2 = findViewById(R.id.edtPassword2);
        txtvRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Registro.this, MainActivity.class));
            }
        });
        btnRegistrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUsername.getText().toString().equals("") && edtPassword.getText().toString().equals("") && edtPass2.getText().toString().equals("")){
                    Toast.makeText(Activity_Registro.this, "No Puede dejar los campos vacios", Toast.LENGTH_LONG).show();
                }
                if(edtPassword.getText().toString().equals(edtPass2.getText().toString())){
                    Toast.makeText(Activity_Registro.this, "No Coinciden las Contraseñas", Toast.LENGTH_LONG).show();
                }else
                    Registro(edtUsername.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void Registro(String usuario, String pass){
        servicio.Registrase(usuario, pass).enqueue(new Callback<Peticion_Registro>() {
            @Override
            public void onResponse(Call<Peticion_Registro> call, Response<Peticion_Registro> response) {
                if(response.isSuccessful()){
                    if(response.body().getEstado()){
                        Toast.makeText(Activity_Registro.this, "Te has registrado con Exito", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Activity_Registro.this, MainActivity.class));
                        finish();
                    }else
                        Toast.makeText(Activity_Registro.this, "Ya exite alguien con esas credenciales", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(Activity_Registro.this, "A Ocurrido un error al Realizar el Registro", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Peticion_Registro> call, Throwable t) {

            }
        });
    }
}
