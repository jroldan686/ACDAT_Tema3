package jrl.acdat.acdat_tema3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Ejercicio4Activity extends Activity implements View.OnClickListener{

    Button btnListarSitios;
    ListView lvwListaSitios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio4);

        btnListarSitios = (Button)findViewById(R.id.btnListarSitios);
        btnListarSitios.setOnClickListener(this);
        lvwListaSitios = (ListView)findViewById(R.id.lvwListaSitios);
    }

    @Override
    public void onClick(View v) {
        if(v == btnListarSitios) {
            
        }
    }
}
