package jrl.acdat.acdat_tema3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Ejercicio4Activity extends Activity implements AdapterView.OnItemClickListener{

    ListView lstvListaSitios;
    ArrayAdapter<Sitio> adapter;
    ArrayList<Sitio> sitios = new ArrayList<Sitio>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio4);

        lstvListaSitios = (ListView)findViewById(R.id.lstvListaSitios);
        lstvListaSitios.setOnItemClickListener(this);

        // Sitios
        sitios.add(new Sitio("El Pais", "http://ep00.epimg.net/rss/ccaa/andalucia.xml"));
        sitios.add(new Sitio("El Mundo", "http://estaticos.elmundo.es/elmundo/rss/andalucia.xml"));
        sitios.add(new Sitio("Linux Magazine News", "http://www.linux-magazine.com/rss/feed/lmi_news"));
        sitios.add(new Sitio("PC World", "http://www.pcworld.com/index.rss"));

        mostrarLista();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TitularesActivity.class);
        intent.putExtra("nombre", sitios.get(position).getNombre());
        intent.putExtra("enlace", sitios.get(position).getEnlace());
        startActivity(intent);
    }

    public void mostrarLista() {
        if (sitios != null) {
            adapter = new ArrayAdapter<Sitio>(this, android.R.layout.simple_list_item_1, sitios);
            lstvListaSitios.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
        }
    }
}
