package jrl.acdat.acdat_tema3;

public class Estacion {

    private String title;
    private String estado;
    private String bicisDisponibles;
    private String anclajesDisponibles;
    private String coordinates;
    private String lastUpdated;

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getEstado() {
        return this.estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBicisDisponibles() {
        return this.bicisDisponibles;
    }
    public void setBicisDisponibles(String bicisDisponibles) {
        this.bicisDisponibles = bicisDisponibles;
    }

    public String getAnclajesDisponibles() {
        return this.anclajesDisponibles;
    }
    public void setAnclajesDisponibles(String anclajesDisponibles) {
        this.anclajesDisponibles = anclajesDisponibles;
    }

    public String getCoordinates() {
        return this.coordinates;
    }
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getLastUpdated() {
        return this.lastUpdated;
    }
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
