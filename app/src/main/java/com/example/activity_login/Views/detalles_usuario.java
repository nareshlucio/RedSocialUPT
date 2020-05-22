package com.example.activity_login.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.activity_login.Models.Peticion_Usuarios;
import com.example.activity_login.R;

public class detalles_usuario extends AppCompatActivity {
    TextView usuario, creacion, actua, id;
    ImageButton btnRegresar;
    Peticion_Usuarios.Usuarios Usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_usuario);
        id = findViewById(R.id.txtvId);
        usuario = findViewById(R.id.txtvUsuario);
        creacion = findViewById(R.id.txtvCreacion);
        actua = findViewById(R.id.txtvActualizacion);
        btnRegresar = findViewById(R.id.ibtnAtras);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(detalles_usuario.this, Dashboard.class));
                finish();
            }
        });
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            Usuario = (Peticion_Usuarios.Usuarios) intent.getSerializableExtra("data");
            String Id, Usuarion, Creacion, Actu;
            Id = "Id Usuario: "+Usuario.getId();
            Usuarion = "Username: "+Usuario.getUsername();
            Creacion = "Fecha de Creacion: "+Usuario.getCreacion();
            Actu = "Ultima Actualizacion: "+Usuario.getActualizacion();
            id.setText(Id);
            usuario.setText(Usuarion);
            creacion.setText(Creacion);
            actua.setText(Actu);
        }
    }
}
