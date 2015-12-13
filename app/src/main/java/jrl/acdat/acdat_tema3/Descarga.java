package jrl.acdat.acdat_tema3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Descarga {

    private Context contexto;
    private AsyncHttpClient cliente;
    private ArrayList<Noticia> noticias;

    public Descarga(Context contexto, String url, String nombreFichero) {
        this.contexto = contexto;
        cliente = new AsyncHttpClient();
        descargar(url, nombreFichero);
    }

    private void descargar(String url, String nombreFichero) {
        final ProgressDialog progreso = new ProgressDialog(this.contexto);
        final File miFichero=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), nombreFichero);
        cliente.get(url,new FileAsyncHttpResponseHandler(miFichero){
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(false);
                progreso.show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file){
                progreso.dismiss();
                Toast.makeText(contexto, "Fallo: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, File file){
                progreso.dismiss();
                Toast.makeText(contexto, "Descarga OK " + file.getPath(), Toast.LENGTH_LONG).show();
                try {
                    noticias = Analisis.analizarNoticias(file);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    Toast.makeText(contexto, "Excepcion XML: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
