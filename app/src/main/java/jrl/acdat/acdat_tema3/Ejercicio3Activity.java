package jrl.acdat.acdat_tema3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Ejercicio3Activity extends Activity implements AdapterView.OnItemClickListener {

    private static final String RSS = "http://www.zaragoza.es/api/recurso/urbanismo-infraestructuras/estacion-bicicleta.xml";

    ListView lstvEstaciones;

    Analisis analisis = null;
    ArrayList<Estacion> estaciones = null;
    ArrayAdapter<Estacion> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio3);

        lstvEstaciones = (ListView) findViewById(R.id.lstvEstaciones);
        lstvEstaciones.setOnItemClickListener(this);

        analisis = new Analisis();
        estaciones = new ArrayList<Estacion>();

        descargarRSS(RSS);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EstacionActivity.class);
        intent.putExtra("title", estaciones.get(position).getTitle());
        intent.putExtra("estado", estaciones.get(position).getEstado());
        intent.putExtra("bicis", estaciones.get(position).getBicisDisponibles());
        intent.putExtra("anclajes", estaciones.get(position).getAnclajesDisponibles());
        intent.putExtra("coordinates", estaciones.get(position).getCoordinates());
        intent.putExtra("ultmod", estaciones.get(position).getLastUpdated());
        startActivity(intent);
    }

    public void mostrarLista() {
        if (estaciones != null) {
            adapter = new ArrayAdapter<Estacion>(this, android.R.layout.simple_list_item_1, estaciones);
            lstvEstaciones.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
        }
    }

    public void descargarRSS(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {

                    private ProgressDialog progreso;

                    @Override
                    public void onStart() {
                        // called before request is started
                        progreso = new ProgressDialog(Ejercicio3Activity.this);
                        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progreso.setMessage("Conectando . . .");
                        progreso.show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        // called when response HTTP status is "200 OK"
                        progreso.dismiss();
                        try {
                            estaciones = analisis.analizarEstaciones(response);
                            mostrarLista();
                        } catch (Exception e) {
                            Toast.makeText(Ejercicio3Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        progreso.dismiss();
                        Toast.makeText(Ejercicio3Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
