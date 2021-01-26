package com.example.gestionvideojuegos;

import java.util.ArrayList;
import java.util.Collections;

public class Modelo {
    public static final int ASCENDENTE = 89;
    public static final int DESCENDENTE = 43;
    private final ArrayList<Videojuego> videojuegos;

    public Modelo() {
        videojuegos = new ArrayList<>();
        introducirDatosIniciales();
    }

    public ArrayList<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void nuevoJuego(Videojuego videojuego) {
        videojuegos.add(videojuego);
    }

    public void eliminarJuego(Videojuego videojuego) {
        videojuegos.remove(videojuego);
    }

    public void ordenar(int campo, int orden) {
        if (orden == DESCENDENTE) {
            Collections.reverse(videojuegos);
        } else {
            Collections.sort(videojuegos, (videojuego1, videojuego2) -> {
                int resultado = 0;
                switch (campo) {
                    case 0:
                        resultado = videojuego1.getTitulo().toLowerCase().compareTo(videojuego2.getTitulo().toLowerCase());
                        break;
                    case 1:
                        resultado = videojuego1.getPlataforma().toLowerCase().compareTo(videojuego2.getPlataforma().toLowerCase());
                        break;
                    case 2:
                        resultado = videojuego1.getGenero().toLowerCase().compareTo(videojuego2.getGenero().toLowerCase());
                        break;
                    case 3:
                        resultado = videojuego1.getFechaSalida() - videojuego2.getFechaSalida();
                        break;
                }
                return resultado;
            });
        }
    }


    public ArrayList<Videojuego> filtrarPorCampo(String filtrado, int filtro) {
        ArrayList<Videojuego> videojuegosEncontrados = new ArrayList<>();
        switch (filtro) {
            case 0: //Titulo
                for (Videojuego videojuego : videojuegos) {
                    if (videojuego.getTitulo().toLowerCase().contains(filtrado.toLowerCase())) {
                        videojuegosEncontrados.add(videojuego);
                    }
                }
                break;
            case 1: //Plataforma
                for (Videojuego videojuego : videojuegos) {
                    if (videojuego.getPlataforma().toLowerCase().contains(filtrado.toLowerCase())) {
                        videojuegosEncontrados.add(videojuego);
                    }
                }
                break;
            case 2: //Genero
                for (Videojuego videojuego : videojuegos) {
                    if (videojuego.getGenero().toLowerCase().contains(filtrado.toLowerCase())) {
                        videojuegosEncontrados.add(videojuego);
                    }
                }
                break;
            case 3: //Fecha de salida
                for (Videojuego videojuego : videojuegos) {
                    if (Integer.toString(videojuego.getFechaSalida()).equals(filtrado)) {
                        videojuegosEncontrados.add(videojuego);
                    }
                }
                break;
        }
        return videojuegosEncontrados;
    }

    private void introducirDatosIniciales() {
        videojuegos.add(new Videojuego("Astroneer", "PC/PS4", "Micro-gestión", 2019, R.drawable.juego13));
        videojuegos.add(new Videojuego("Dark Souls", "PC/PS4/XBOX", "Rol", 2011, R.drawable.juego15));
        videojuegos.add(new Videojuego("Grand Theft Auto V", "PC", "Rol", 2013, R.drawable.juego8));
        videojuegos.add(new Videojuego("League Of Legends", "PC", "MOBA", 2009, R.drawable.juego19));
        videojuegos.add(new Videojuego("Minecraft", "PC/PS4/XBOX/Android", "Sandbox", 2009, R.drawable.juego16));
        videojuegos.add(new Videojuego("Oxygen Not Included", "PC", "Micro-gestión", 2019, R.drawable.juego2));
        videojuegos.add(new Videojuego("Pacman", "PC/PS4/XBOX/Android", "Arcade", 1980, R.drawable.juego12));
        videojuegos.add(new Videojuego("Subnautica", "PC/PS4", "Supervivencia", 2018, R.drawable.juego9));
        videojuegos.add(new Videojuego("Unturned", "PC", "Supervivencia", 2017, R.drawable.juego5));
        videojuegos.add(new Videojuego("World Of Warcraft", "PC", "MMORPG", 2005, R.drawable.juego3));
        Collections.shuffle(videojuegos);
    }

}
