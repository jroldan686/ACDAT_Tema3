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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TitularesActivity extends Activity implements AdapterView.OnItemClickListener{

    String nombreSitio, enlaceSitio;
    TextView txvSitio;
    ListView lstvListaTitulares;
    ArrayAdapter<Noticia> adapter;
    ArrayList<Noticia> noticias = new ArrayList<Noticia>();
    Analisis analisis = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titulares);

        Intent intent = getIntent();
        nombreSitio = intent.getStringExtra("nombre");
        enlaceSitio = intent.getStringExtra("enlace");

        txvSitio = (TextView)findViewById(R.id.txvSitio);
        lstvListaTitulares = (ListView)findViewById(R.id.lstvListaTitulares);
        lstvListaTitulares.setOnItemClickListener(this);

        txvSitio.setText(nombreSitio);
        analisis = new Analisis();

        descargarRSS(enlaceSitio);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse(noticias.get(position).getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void mostrarLista() {
        if (noticias != null) {
            adapter = new ArrayAdapter<Noticia>(this, android.R.layout.simple_list_item_1, noticias);
            lstvListaTitulares.setAdapter(adapter);
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
                        progreso = new ProgressDialog(TitularesActivity.this);
                        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progreso.setMessage("Conectando . . .");
                        progreso.show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        // called when response HTTP status is "200 OK"
                        progreso.dismiss();
                        try {
                            noticias = analisis.analizarNoticias(response);
                            mostrarLista();
                        } catch (Exception e) {
                            Toast.makeText(TitularesActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        progreso.dismiss();
                        Toast.makeText(TitularesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
