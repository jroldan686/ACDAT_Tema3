package jrl.acdat.acdat_tema3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Ejercicio1Activity extends Activity implements View.OnClickListener{

    TextView txvDatos;
    Button btnMostrarEmpleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio1);

        txvDatos = (TextView)findViewById(R.id.txvDatos);
        btnMostrarEmpleados = (Button)findViewById(R.id.btnMostrarEmpleados);
        btnMostrarEmpleados.setOnClickListener(this);

        txvDatos.setText("");
    }

    @Override
    public void onClick(View v) {
        try {
            txvDatos.setText(Analisis.analizarEmpleados(this));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Excepcion: " + ex, Toast.LENGTH_SHORT).show();
        }
    }
}
