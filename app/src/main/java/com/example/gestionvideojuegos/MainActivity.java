package com.example.gestionvideojuegos;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int REQUEST_VISUALIZAR_VIDEOJUEGO = 2404;
    private static final int REQUEST_NUEVO_VIDEOJUEGO = 2001;
    private static final int REQUEST_NOTIFICACION = 123;

    private boolean ordenado;
    private boolean filtrado;
    private String ultimoFiltro;
    private int ultimoFiltradoSeleccionado;
    private Modelo modelo;
    private Videojuego ultimoVideojuegoVisualizado;
    private ListView listVideojuegos;
    private AdaptadorVideojuego at;
    private AdaptadorVideojuego at2;
    private FloatingActionButton btnNuevo, btnFiltrar, btnOrdenar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        ordenado = false;
        filtrado = false;
        modelo = new Modelo();
        ultimoVideojuegoVisualizado = null;
        listVideojuegos = findViewById(R.id.listVideojuegos);
        at = new AdaptadorVideojuego(this, modelo.getVideojuegos());
        listVideojuegos.setAdapter(at);
        btnNuevo = (FloatingActionButton) findViewById(R.id.btnNuevo);
        btnFiltrar = (FloatingActionButton) findViewById(R.id.btnFiltrar);
        btnOrdenar = (FloatingActionButton) findViewById(R.id.btnOrdenar);

        initHandlers();
    }

    private void initHandlers() {
        listVideojuegos.setOnItemClickListener(this);
        btnNuevo.setOnClickListener(this);
        btnOrdenar.setOnClickListener(this);
        btnFiltrar.setOnClickListener(this);
    }

    private void filtrar() {
        AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alerta_filtrado, null);

        EditText txtFiltro = (EditText) dialogView.findViewById(R.id.txtFiltro);
        Button btnCancelar = (Button) dialogView.findViewById(R.id.btnCancelar);
        Button btnFiltrado = (Button) dialogView.findViewById(R.id.btnFiltrado);

        Spinner spinSeleccionFiltrado = (Spinner) dialogView.findViewById(R.id.spinSeleccionFiltrado);
        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this, R.array.filtrados, android.R.layout.simple_spinner_item);
        adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinSeleccionFiltrado.setAdapter(adap);

        btnCancelar.setOnClickListener(view -> dialogBuilder.dismiss());
        btnFiltrado.setOnClickListener(view -> {
            ultimoFiltradoSeleccionado = spinSeleccionFiltrado.getSelectedItemPosition();
            ultimoFiltro = txtFiltro.getText().toString().trim();
            if (!ultimoFiltro.equals("")) {
                btnOrdenar.setVisibility(View.GONE);
                at2 = new AdaptadorVideojuego(MainActivity.this, modelo.filtrarPorCampo(ultimoFiltro, ultimoFiltradoSeleccionado));
                listVideojuegos.setAdapter(at2);
                filtrado = true;
                Toast.makeText(MainActivity.this, R.string.lista_filtrada, Toast.LENGTH_SHORT).show();
            } else {
                deshacerFiltrado();
            }
            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void ordenar() {
        if (ordenado) {
            Toast.makeText(MainActivity.this, R.string.lista_ordenada_des, Toast.LENGTH_SHORT).show();
            modelo.ordenar(-1, Modelo.DESCENDENTE);
            btnOrdenar.setIcon(R.drawable.ordenar_ascendente);
            ordenado = false;
            at.notifyDataSetChanged();
        } else {
            AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alerta_ordenar, null);

            Button btnCancelarOrdenar = (Button) dialogView.findViewById(R.id.btnCancelarOrdenar);
            Button btnConfirmarOrdenar = (Button) dialogView.findViewById(R.id.btnConfirmarOrdenar);

            Spinner spinSeleccionOrdenacion = (Spinner) dialogView.findViewById(R.id.spinSeleccionOrdenacion);
            ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this, R.array.filtrados, android.R.layout.simple_spinner_item);
            adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinSeleccionOrdenacion.setAdapter(adap);

            btnCancelarOrdenar.setOnClickListener(view -> dialogBuilder.dismiss());
            btnConfirmarOrdenar.setOnClickListener(view -> {
                Toast.makeText(MainActivity.this, R.string.lista_ordenada_asc, Toast.LENGTH_SHORT).show();
                modelo.ordenar(spinSeleccionOrdenacion.getSelectedItemPosition(), Modelo.ASCENDENTE);
                btnOrdenar.setIcon(R.drawable.ordenar_descendente);
                ordenado = true;
                at.notifyDataSetChanged();
                dialogBuilder.dismiss();
            });
            dialogBuilder.setView(dialogView);
            dialogBuilder.show();
        }
    }

    private void deshacerFiltrado() {
        if (filtrado) {
            listVideojuegos.setAdapter(at);
            btnOrdenar.setVisibility(View.VISIBLE);
            at2.clear();
            at2 = null;
            filtrado = false;
        }
    }

    private void notificarListaVacia() {
        Intent nIntent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent nPending = PendingIntent.getActivity(MainActivity.this, REQUEST_NOTIFICACION, nIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder nBuilder;
        if (Build.VERSION.SDK_INT < 26) {
            nBuilder = new NotificationCompat.Builder(MainActivity.this);
        } else {
            NotificationChannel nCanal = new NotificationChannel("basicas", "NotificacionesBasicas", NotificationManager.IMPORTANCE_DEFAULT);
            nManager.createNotificationChannel(nCanal);
            nBuilder = new NotificationCompat.Builder(MainActivity.this, "basicas");
        }
        nBuilder.setWhen(System.currentTimeMillis());
        nBuilder.setSmallIcon(R.drawable.notificacion);
        nBuilder.setContentTitle("LISTA VACÍA");
        nBuilder.setContentText("Has eliminado todos los elementos.");
        nBuilder.setContentIntent(nPending);
        Notification notificacion = nBuilder.build();
        nManager.notify(1, notificacion);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VISUALIZAR_VIDEOJUEGO) {
            if (resultCode == VisualizarVideojuego.RESULT_ELIMINAR) {
                modelo.eliminarJuego(ultimoVideojuegoVisualizado);
                at.notifyDataSetChanged();
                deshacerFiltrado();
                Toast.makeText(MainActivity.this, R.string.videojuego_eliminado, Toast.LENGTH_SHORT).show();
                if (modelo.getVideojuegos().size() == 0) {
                    notificarListaVacia();
                }
            } else if (resultCode == VisualizarVideojuego.RESULT_MODIFICAR) {
                modelo.eliminarJuego(ultimoVideojuegoVisualizado);
                assert data != null;
                modelo.nuevoJuego(data.getParcelableExtra("videojuego"));
                at.notifyDataSetChanged();
                deshacerFiltrado();
                Toast.makeText(MainActivity.this, R.string.videojuego_modificado, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_NUEVO_VIDEOJUEGO) {
            if (resultCode == GestionVideojuego.RESULT_NUEVO) {
                assert data != null;
                modelo.nuevoJuego(data.getParcelableExtra("videojuego"));
                at.notifyDataSetChanged();
                deshacerFiltrado();
                Toast.makeText(MainActivity.this, R.string.videojuego_nuevo, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (filtrado) {
            ultimoVideojuegoVisualizado = modelo.filtrarPorCampo(ultimoFiltro, ultimoFiltradoSeleccionado).get(position);
        } else {
            ultimoVideojuegoVisualizado = modelo.getVideojuegos().get(position);
        }
        Intent intent = new Intent(MainActivity.this, VisualizarVideojuego.class);
        intent.putExtra("videojuego", ultimoVideojuegoVisualizado);
        startActivityForResult(intent, REQUEST_VISUALIZAR_VIDEOJUEGO);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNuevo:
                Intent intent = new Intent(MainActivity.this, GestionVideojuego.class);
                startActivityForResult(intent, REQUEST_NUEVO_VIDEOJUEGO);
                break;
            case R.id.btnOrdenar:
                ordenar();
                break;
            case R.id.btnFiltrar:
                filtrar();
                break;
        }
    }
}