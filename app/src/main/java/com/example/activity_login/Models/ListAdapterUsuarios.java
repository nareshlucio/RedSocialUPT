package com.example.activity_login.Models;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activity_login.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListAdapterUsuarios extends RecyclerView.Adapter<ListAdapterUsuarios.ViewHolder>{

    List<Peticion_Usuarios.Usuarios> lista;
    clickDetalles clickDetalles;
    public ListAdapterUsuarios(clickDetalles clickDetalles){
        this.clickDetalles = clickDetalles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Peticion_Usuarios.Usuarios usuario = lista.get(position);
        holder.texto.setText(usuario.getUsername());
        holder.btnImage.setOnClickListener(v -> clickDetalles.ClickUsuario(usuario));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setData(List<Peticion_Usuarios.Usuarios> lista){
        this.lista = lista;
        notifyDataSetChanged();
    }

    public interface clickDetalles{
        public void ClickUsuario(Peticion_Usuarios.Usuarios Usuarios);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView texto;
        ImageButton btnImage;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            texto = itemView.findViewById(R.id.txtvTexto);
            btnImage = (ImageButton) itemView.findViewById(R.id.ibtnDetalles);
        }
    }

}
