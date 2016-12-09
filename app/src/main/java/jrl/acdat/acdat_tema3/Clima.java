package jrl.acdat.acdat_tema3;

import android.widget.Toast;

import java.util.ArrayList;

public class Clima {

    private String fecha = "";
    private ArrayList<String> enlacesImagenes = new ArrayList<String>();
    private String temperaturaMaxima = "";
    private String temperaturaMinima = "";

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public ArrayList<String> getEnlacesImagenes() {
        return enlacesImagenes;
    }
    public void setEnlacesImagenes(ArrayList<String> enlacesImagenes) {
        if(enlacesImagenes.size() == 0) {
            for(int i = 0; i < 4; i++)
                enlacesImagenes.add("error");
        }
        this.enlacesImagenes = enlacesImagenes;
    }

    public String getTemperaturaMaxima() {
        return temperaturaMaxima;
    }
    public void setTemperaturaMaxima(String temperaturaMaxima) {
        if(temperaturaMaxima.equals(null) || temperaturaMaxima.equals(""))
            temperaturaMaxima = "?";
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public String getTemperaturaMinima() {
        return temperaturaMinima;
    }
    public void setTemperaturaMinima(String temperaturaMinima) {
        if(temperaturaMinima.equals(null) || temperaturaMinima.equals(""))
            temperaturaMinima = "?";
        this.temperaturaMinima = temperaturaMinima;
    }
}
