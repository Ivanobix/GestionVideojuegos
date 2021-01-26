package com.example.gestionvideojuegos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdaptadorVideojuego extends ArrayAdapter<Videojuego> {

    private final ArrayList<Videojuego> datos;

    public AdaptadorVideojuego(@NonNull Context context, ArrayList<Videojuego> videojuegos) {
        super(context, R.layout.item_videojuego, videojuegos);
        datos = videojuegos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View vistaItem = convertView;

        if (vistaItem == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            vistaItem = inflater.inflate(R.layout.item_videojuego, null);
            holder = new ViewHolder();
            holder.titulo = vistaItem.findViewById(R.id.lblTitulo);
            holder.fechaSalida = vistaItem.findViewById(R.id.lblFechaSalida);
            holder.gameplay = vistaItem.findViewById(R.id.imgGameplay);
            vistaItem.setTag(holder);
        } else {
            holder = (ViewHolder) vistaItem.getTag();
        }

        Videojuego videojuego = datos.get(position);

        holder.gameplay.setImageResource(videojuego.getImagenGameplay());
        holder.titulo.setText(videojuego.getTitulo());
        holder.fechaSalida.setText(String.valueOf(videojuego.getFechaSalida()));

        return vistaItem;
    }

    static class ViewHolder {
        TextView titulo;
        TextView fechaSalida;
        ImageView gameplay;
    }
}
