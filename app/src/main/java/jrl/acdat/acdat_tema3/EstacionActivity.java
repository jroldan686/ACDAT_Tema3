package jrl.acdat.acdat_tema3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EstacionActivity extends Activity implements View.OnClickListener {

    private static final String GOOGLEMAPS = "https://www.google.com/maps?q=";

    TextView txvTitle, txvValorEstado, txvValorBicisDisp, txvValorAnclajesDisp, txvValorUltMod;
    Button btnMostrarUbicacion;
    String title, estado, bicisDisponibles, anclajesDisponibles, coordinates, ultimaModificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        estado = intent.getStringExtra("estado");
        bicisDisponibles = intent.getStringExtra("bicis");
        anclajesDisponibles = intent.getStringExtra("anclajes");
        coordinates = intent.getStringExtra("coordinates");
        ultimaModificacion = intent.getStringExtra("ultmod");

        txvTitle = (TextView)findViewById(R.id.txvTitle);
        txvValorEstado = (TextView)findViewById(R.id.txvValorEstado);
        txvValorBicisDisp = (TextView)findViewById(R.id.txvValorBicisDisp);
        txvValorAnclajesDisp = (TextView)findViewById(R.id.txvValorAnclajesDisp);
        txvValorUltMod = (TextView)findViewById(R.id.txvValorUltMod);
        btnMostrarUbicacion = (Button)findViewById(R.id.btnMostrarUbicacion);
        btnMostrarUbicacion.setOnClickListener(this);

        txvTitle.setText(title);
        txvValorEstado.setText(estado);
        txvValorBicisDisp.setText(bicisDisponibles);
        txvValorAnclajesDisp.setText(anclajesDisponibles);
        txvValorUltMod.setText(ultimaModificacion);
    }

    @Override
    public void onClick(View v) {
        if(v == btnMostrarUbicacion) {
            Uri uri = Uri.parse(GOOGLEMAPS + coordinates);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
