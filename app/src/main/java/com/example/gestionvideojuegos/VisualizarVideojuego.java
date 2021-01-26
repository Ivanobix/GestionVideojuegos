package com.example.gestionvideojuegos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class VisualizarVideojuego extends AppCompatActivity implements View.OnClickListener {

    public static final int RESULT_ELIMINAR = 2405;
    public static final int RESULT_MODIFICAR = 2406;
    private static final int REQUEST_MODIFICAR_VIDEOJUEGO = 3000;
    private Button btnEliminar, btnModificar;
    private Videojuego videojuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_visualizar_videojuego);
        TextView lblTitulo = (TextView) findViewById(R.id.lblTituloExtendido);
        TextView lblPlataforma = (TextView) findViewById(R.id.lblPlataformaExtendida);
        TextView lblGenero = (TextView) findViewById(R.id.lblGeneroExtendido);
        TextView lblAnoSalida = (TextView) findViewById(R.id.lblAnoSalidaExtendido);
        ImageView imgGameplay = (ImageView) findViewById(R.id.imgGameplayNueva);
        videojuego = null;
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnModificar = (Button) findViewById(R.id.btnModificar);

        initHandlers();

        Intent data = getIntent();
        if (data != null && data.hasExtra("videojuego")) {
            videojuego = data.getParcelableExtra("videojuego");
            lblTitulo.setText(videojuego.getTitulo());
            lblPlataforma.setText(videojuego.getPlataforma());
            lblGenero.setText(videojuego.getGenero());
            lblAnoSalida.setText(String.valueOf(videojuego.getFechaSalida()));
            imgGameplay.setImageResource(videojuego.getImagenGameplay());
        }
    }

    private void initHandlers() {
        btnEliminar.setOnClickListener(this);
        btnModificar.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MODIFICAR_VIDEOJUEGO) {
            if (resultCode == GestionVideojuego.RESULT_MODIFICAR) {
                Intent in = new Intent(VisualizarVideojuego.this, MainActivity.class);
                assert data != null;
                Videojuego videojuego = data.getParcelableExtra("videojuego");
                in.putExtra("videojuego", videojuego);
                setResult(RESULT_MODIFICAR, in);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (videojuego != null) {
            switch (v.getId()) {
                case R.id.btnEliminar:
                    setResult(RESULT_ELIMINAR);
                    finish();
                    break;
                case R.id.btnModificar:
                    Intent intent = new Intent(VisualizarVideojuego.this, GestionVideojuego.class);
                    intent.putExtra("videojuego", videojuego);
                    startActivityForResult(intent, REQUEST_MODIFICAR_VIDEOJUEGO);
                    break;
            }
        }
    }
}