package jrl.acdat.acdat_tema3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Ejercicio2Activity extends Activity implements View.OnClickListener {

    private static final String RSS = "http://www.aemet.es/xml/municipios/localidad_29067.xml";

    Analisis analisis;

    TextView txvFechaHoy, txvFechaMnn, txvTempMinHoy, txvTempMaxHoy, txvTempMinMnn, txvTempMaxMnn;
    ImageView imgvPeriodoHoy0_6, imgvPeriodoHoy6_12, imgvPeriodoHoy12_18, imgvPeriodoHoy18_24;
    ImageView imgvPeriodoMnn0_6, imgvPeriodoMnn6_12, imgvPeriodoMnn12_18, imgvPeriodoMnn18_24;
    Button btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);

        txvFechaHoy = (TextView)findViewById(R.id.txvFechaHoy);
        txvFechaMnn = (TextView)findViewById(R.id.txvFechaMnn);

        txvTempMinHoy = (TextView)findViewById(R.id.txvTempMinHoy);
        txvTempMaxHoy = (TextView)findViewById(R.id.txvTempMaxHoy);
        txvTempMinMnn = (TextView)findViewById(R.id.txvTempMinMnn);
        txvTempMaxMnn = (TextView)findViewById(R.id.txvTempMaxMnn);

        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(this);

        txvTempMinHoy.setText("?");
        txvTempMaxHoy.setText("?");
        txvTempMinMnn.setText("?");
        txvTempMaxMnn.setText("?");

        analisis = new Analisis();

        descargarRSS(RSS);
    }

    @Override
    public void onClick(View v) {
        if(v == btnActualizar)
            descargarRSS(RSS);
    }

    public void descargarRSS(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {

                    private ProgressDialog progreso;

                    @Override
                    public void onStart() {
                        // called before request is started
                        progreso = new ProgressDialog(Ejercicio2Activity.this);
                        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progreso.setMessage("Conectando . . .");
                        progreso.show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        // called when response HTTP status is "200 OK"
                        progreso.dismiss();
                        try {
                            ArrayList<Clima> climas = analisis.analizarPronostico(response);
                            Clima clima = null;
                            ImageView image = new ImageView(Ejercicio2Activity.this);

                            // Hoy
                            clima = climas.get(0);
                            txvFechaHoy.setText(clima.getFecha());
                            txvTempMinHoy.setText(clima.getTemperaturaMinima());
                            txvTempMaxHoy.setText(clima.getTemperaturaMaxima());
                            // Imagenes
                            for(int i = 0; i < clima.getEnlacesImagenes().size(); i++) {
                                switch (i) {
                                    case 0: image = imgvPeriodoHoy0_6; break;
                                    case 1: image = imgvPeriodoHoy6_12; break;
                                    case 2: image = imgvPeriodoHoy12_18; break;
                                    case 3: image = imgvPeriodoHoy18_24; break;
                                }
                                Picasso.with(Ejercicio2Activity.this)
                                        .load(clima.getEnlacesImagenes().get(i))
                                        .error(R.drawable.error)
                                        .into(image);
                            }

                            // Mañana
                            clima = climas.get(1);
                            txvFechaMnn.setText(clima.getFecha());
                            txvTempMinMnn.setText(clima.getTemperaturaMinima());
                            txvTempMaxMnn.setText(clima.getTemperaturaMaxima());
                            // Imagenes
                            for(int i = 0; i < clima.getEnlacesImagenes().size(); i++) {
                                switch (i) {
                                    case 0: image = imgvPeriodoMnn0_6; break;
                                    case 1: image = imgvPeriodoMnn6_12; break;
                                    case 2: image = imgvPeriodoMnn12_18; break;
                                    case 3: image = imgvPeriodoMnn18_24; break;
                                }
                                Picasso.with(Ejercicio2Activity.this)
                                        .load(clima.getEnlacesImagenes().get(i))
                                        .error(R.drawable.error)
                                        .into(image);
                            }

                        } catch (Exception e) {
                            Toast.makeText(Ejercicio2Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        progreso.dismiss();
                        Toast.makeText(Ejercicio2Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
