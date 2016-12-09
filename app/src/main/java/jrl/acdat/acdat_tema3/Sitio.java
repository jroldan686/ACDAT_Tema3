package jrl.acdat.acdat_tema3;

public class Sitio {

    private String nombre;
    private String enlace;

    public Sitio(String nombre, String enlace) {
        this.setNombre(nombre);
        this.setEnlace(enlace);
    }

    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEnlace() {
        return this.enlace;
    }
    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
