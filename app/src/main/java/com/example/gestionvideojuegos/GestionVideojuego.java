package com.example.gestionvideojuegos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class GestionVideojuego extends AppCompatActivity implements View.OnClickListener {

    public static final int RESULT_NUEVO = 2002;
    public static final int RESULT_MODIFICAR = 2406;
    private int contImagen;
    private ImageView imgGameplay;
    private EditText txtTitulo, txtGenero, txtPlataforma, txtAnoSalida;
    private Button btnCrear, btnCancelar, btnSiguienteImg, btnAnteriorImg;
    private Videojuego videojuego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_gestion_videojuego);

        imgGameplay = (ImageView) findViewById(R.id.imgGameplayNueva);
        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtGenero = (EditText) findViewById(R.id.txtGenero);
        txtPlataforma = (EditText) findViewById(R.id.txtPlataforma);
        txtAnoSalida = (EditText) findViewById(R.id.txtAnoSalida);
        btnCrear = (Button) findViewById(R.id.btnCrear);
        btnCancelar = (Button) findViewById(R.id.btnVolver);
        btnSiguienteImg = (Button) findViewById(R.id.btnSiguienteImg);
        btnAnteriorImg = (Button) findViewById(R.id.btnAnteriorImg);
        contImagen = 1;
        videojuego = null;

        Intent data = getIntent();
        if (data != null && data.hasExtra("videojuego")) {
            videojuego = data.getParcelableExtra("videojuego");
            txtTitulo.setText(videojuego.getTitulo());
            txtPlataforma.setText(videojuego.getPlataforma());
            txtGenero.setText(videojuego.getGenero());
            txtAnoSalida.setText(String.valueOf(videojuego.getFechaSalida()));
            imgGameplay.setImageResource(videojuego.getImagenGameplay());
            contImagen = -1;
            btnCrear.setText(getResources().getString(R.string.modificar));
        }

        initHandlers();


    }

    private void initHandlers() {
        btnCrear.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnSiguienteImg.setOnClickListener(this);
        btnAnteriorImg.setOnClickListener(this);
    }

    private void siguienteImagen() {
        if (contImagen < 20 && contImagen > 0) {
            contImagen++;
        } else {
            contImagen = 1;
        }
        imgGameplay.setImageResource(generarIdImagenSeleccionada());
    }

    private void anteriorImagen() {
        if (contImagen > 1) {
            contImagen--;
        } else {
            contImagen = 20;
        }
        imgGameplay.setImageResource(generarIdImagenSeleccionada());
    }

    private void gestionarVideojuego() {
        String titulo = txtTitulo.getText().toString().trim();
        if (!titulo.equals("")) {
            String plataforma = txtPlataforma.getText().toString().trim().replaceAll(", ", "/");
            if (!plataforma.equals("")) {
                String genero = txtGenero.getText().toString().trim();
                if (!genero.equals("")) {
                    try {
                        int anoSalida = Integer.parseInt(txtAnoSalida.getText().toString().trim());
                        if (anoSalida >= 1915 && anoSalida <= Calendar.getInstance().get(Calendar.YEAR)) {
                            if (videojuego == null) {
                                Videojuego nuevoVideojuego = new Videojuego(titulo, plataforma, genero, anoSalida, generarIdImagenSeleccionada());
                                Intent intent = new Intent(GestionVideojuego.this, MainActivity.class);
                                intent.putExtra("videojuego", nuevoVideojuego);
                                setResult(RESULT_NUEVO, intent);
                            } else {
                                videojuego.setTitulo(titulo);
                                videojuego.setGenero(genero);
                                videojuego.setPlataforma(plataforma);
                                videojuego.setFechaSalida(anoSalida);
                                if (contImagen != -1) {
                                    videojuego.setImagenGameplay(generarIdImagenSeleccionada());
                                }
                                Intent in = new Intent(GestionVideojuego.this, VisualizarVideojuego.class);
                                in.putExtra("videojuego", videojuego);
                                setResult(RESULT_MODIFICAR, in);
                            }
                            finish();
                        } else {
                            Toast.makeText(GestionVideojuego.this, R.string.incorrecto_ano_salida, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(GestionVideojuego.this, R.string.falta_ano_salida, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GestionVideojuego.this, R.string.falta_genero, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(GestionVideojuego.this, R.string.falta_plataforma, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(GestionVideojuego.this, R.string.falta_titulo, Toast.LENGTH_SHORT).show();
        }
    }

    private int generarIdImagenSeleccionada() {
        String nombreDeLaImagen = "juego" + contImagen;
        return getResources().getIdentifier(nombreDeLaImagen, "drawable", getPackageName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCrear:
                gestionarVideojuego();
                break;
            case R.id.btnVolver:
                finish();
                break;
            case R.id.btnSiguienteImg:
                siguienteImagen();
                break;
            case R.id.btnAnteriorImg:
                anteriorImagen();
                break;
        }
    }
}