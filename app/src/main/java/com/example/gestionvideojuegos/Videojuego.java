package com.example.gestionvideojuegos;

import android.os.Parcel;
import android.os.Parcelable;

public class Videojuego implements Parcelable {
    public static final Creator<Videojuego> CREATOR = new Creator<Videojuego>() {
        @Override
        public Videojuego createFromParcel(Parcel in) {
            return new Videojuego(in);
        }

        @Override
        public Videojuego[] newArray(int size) {
            return new Videojuego[size];
        }
    };

    private String titulo, plataforma, genero;
    private int anoSalida;
    private int imagenGameplay;

    public Videojuego(String titulo, String plataforma, String genero, int fechaSalida, int imagenGameplay) {
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.genero = genero;
        this.anoSalida = fechaSalida;
        this.imagenGameplay = imagenGameplay;
    }

    protected Videojuego(Parcel in) {
        titulo = in.readString();
        plataforma = in.readString();
        genero = in.readString();
        anoSalida = in.readInt();
        imagenGameplay = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(plataforma);
        dest.writeString(genero);
        dest.writeInt(anoSalida);
        dest.writeInt(imagenGameplay);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getFechaSalida() {
        return anoSalida;
    }

    public void setFechaSalida(int fechaSalida) {
        this.anoSalida = fechaSalida;
    }

    public int getImagenGameplay() {
        return imagenGameplay;
    }

    public void setImagenGameplay(int imagenGameplay) {
        this.imagenGameplay = imagenGameplay;
    }

}
