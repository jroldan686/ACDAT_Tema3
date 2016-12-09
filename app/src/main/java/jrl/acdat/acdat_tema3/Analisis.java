package jrl.acdat.acdat_tema3;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import android.widget.DatePicker;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Analisis {

    private static final String CDATA = "<![CDATA[";
    private static final String FINCDATA = "]]>";

    public static ArrayList<Noticia> analizarNoticias(String xml) throws XmlPullParserException, IOException {

        boolean esItem = false;
        boolean esTitle = false;
        boolean esLink = false;

        Noticia noticia = null;
        ArrayList<Noticia> noticias = new ArrayList<Noticia>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(xml));

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("item")) {
                        esItem = true;
                        noticia = new Noticia();
                    }
                    if (esItem) {
                        if (xpp.getName().equals("title")) {
                            esTitle = true;
                        }
                        if (xpp.getName().equals("link")) {
                            esLink = true;
                        }
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (esTitle) {
                        if(xpp.getText().length() >= CDATA.length()) {
                            String cdata = xpp.getText().substring(0, CDATA.length());
                            if (cdata.equals(CDATA)) {
                                noticia.setTitle(xpp.getText().substring(CDATA.length(), xpp.getText().length() - FINCDATA.length()));
                            } else {
                                noticia.setTitle(xpp.getText());
                            }
                        } else {
                            noticia.setTitle(xpp.getText());
                        }
                    }
                    if (esLink) {
                        if(xpp.getText().length() >= CDATA.length()) {
                            String cdata = xpp.getText().substring(0, CDATA.length());
                            if (cdata.equals(CDATA)) {
                                noticia.setLink(xpp.getText().substring(CDATA.length(), xpp.getText().length() - FINCDATA.length()));
                            } else {
                                noticia.setLink(xpp.getText());
                            }
                        } else {
                            noticia.setTitle(xpp.getText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("item")) {
                        esItem = false;
                        noticias.add(noticia);
                    }
                    if (xpp.getName().equals("title")) {
                        esTitle = false;
                    }
                    if (xpp.getName().equals("link")) {
                        esLink = false;
                    }
                    break;
            }
            eventType = xpp.next();
        }
        return noticias;
    }

    public static ArrayList<Estacion> analizarEstaciones(String xml) throws XmlPullParserException, IOException {

        boolean esEstacion = false;
        boolean esTitle = false;
        boolean esEstado = false;
        boolean esBicisDisponibles = false;
        boolean esAnclajesDisponibles = false;
        boolean esCoordinates = false;
        boolean esLastUpdated = false;

        Estacion estacion = null;
        ArrayList<Estacion> estaciones = new ArrayList<Estacion>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(xml));

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("estacion")) {
                        esEstacion = true;
                        estacion = new Estacion();
                    }
                    if(esEstacion) {
                        if (xpp.getName().equals("title")) {
                            esTitle = true;
                        }
                        if (xpp.getName().equals("estado")) {
                            esEstado = true;
                        }
                        if (xpp.getName().equals("bicisDisponibles")) {
                            esBicisDisponibles = true;
                        }
                        if (xpp.getName().equals("anclajesDisponibles")) {
                            esAnclajesDisponibles = true;
                        }
                        if (xpp.getName().equals("coordinates")) {
                            esCoordinates = true;
                        }
                        if (xpp.getName().equals("lastUpdated")) {
                            esLastUpdated = true;
                        }
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(esTitle) {
                        estacion.setTitle(xpp.getText());
                    }
                    if(esEstado) {
                        estacion.setEstado(xpp.getText());
                    }
                    if(esBicisDisponibles) {
                        estacion.setBicisDisponibles(xpp.getText());
                    }
                    if(esAnclajesDisponibles) {
                        estacion.setAnclajesDisponibles(xpp.getText());
                    }
                    if(esCoordinates) {
                        estacion.setCoordinates(xpp.getText());
                    }
                    if(esLastUpdated) {
                        String fecha = xpp.getText().substring(0, 10);
                        String hora = xpp.getText().substring(11, 19);
                        estacion.setLastUpdated(fecha + " " + hora);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("estacion")) {
                        esEstacion = false;
                        estaciones.add(estacion);
                    }
                    esTitle = false;
                    esEstado = false;
                    esBicisDisponibles = false;
                    esAnclajesDisponibles = false;
                    esCoordinates = false;
                    esLastUpdated = false;
            }
            eventType = xpp.next();
        }

        return estaciones;
    }

    public static ArrayList<Clima> analizarPronostico(String xml) throws XmlPullParserException, IOException {

        boolean esTemperatura = false;
        boolean esMaxima = false;
        boolean esMinima = false;
        boolean esPeriodo0_6 = false;
        boolean esPeriodo6_12 = false;
        boolean esPeriodo12_18 = false;
        boolean esPeriodo18_24 = false;

        String periodo = "";
        ArrayList<String> enlacesImagenes = new ArrayList<String>();
        ArrayList<Clima> climas = new ArrayList<Clima>();
        Clima clima = null;

        String enlaceImagen = "http://www.aemet.es/imagenes/png/estado_cielo/";
        String extensionImagen = ".png";

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(xml));

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equals("dia")) {
                        clima = new Clima();
                        clima.setFecha(xpp.getAttributeValue(0));
                    }
                    if(xpp.getName().equals("estado_cielo")) {
                        periodo = xpp.getAttributeValue(0);
                        if(periodo.equals("00-06")) {
                            esPeriodo0_6 = true;
                        }
                        if(periodo.equals("06-12")) {
                            esPeriodo6_12 = true;
                        }
                        if(periodo.equals("12-18")) {
                            esPeriodo12_18 = true;
                        }
                        if(periodo.equals("18-24")) {
                            esPeriodo18_24 = true;
                        }
                    }
                    if(xpp.getName().equals("temperatura")) {
                        esTemperatura = true;
                    }
                    if(esTemperatura && xpp.getName().equals("maxima")) {
                        esMaxima = true;
                    }
                    if(esTemperatura && xpp.getName().equals("minima")) {
                        esMinima = true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(esPeriodo0_6 || esPeriodo6_12 || esPeriodo12_18 || esPeriodo18_24) {
                        enlacesImagenes.add(enlaceImagen + xpp.getText() + extensionImagen);
                        clima.setEnlacesImagenes(enlacesImagenes);
                    }
                    if(esMaxima) {
                        clima.setTemperaturaMaxima(xpp.getText());
                    }
                    if(esMinima) {
                        clima.setTemperaturaMinima(xpp.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if(xpp.getName().equals("dia")) {
                        climas.add(clima);
                        enlacesImagenes.clear();
                    }
                    if(xpp.getName().equals("estado_cielo")) {
                        if(periodo.equals("00-06")) {
                            esPeriodo0_6 = false;
                        }
                        if(periodo.equals("06-12")) {
                            esPeriodo6_12 = false;
                        }
                        if(periodo.equals("12-18")) {
                            esPeriodo12_18 = false;
                        }
                        if(periodo.equals("18-24")) {
                            esPeriodo18_24 = false;
                        }
                    }
                    if(xpp.getName().equals("temperatura")) {
                        esTemperatura = false;
                    }
                    if(esTemperatura && xpp.getName().equals("maxima")) {
                        esMaxima = false;
                    }
                    if(esTemperatura && xpp.getName().equals("minima")) {
                        esMinima = false;
                    }
                    break;
            }
            eventType = xpp.next();
        }

        return climas;
    }

    private static Date calcularDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);                    // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);   // Número de días a añadir, o restar en caso de días<0
        return calendar.getTime();                  // Devuelve el objeto Date con los nuevos días añadidos
    }

    public static String analizarEmpleados(Context contexto) throws XmlPullParserException, IOException {
        boolean esEmpleado = false;
        boolean esNombre = false;
        boolean esPuesto = false;
        boolean esEdad = false;
        boolean esSueldo = false;
        StringBuilder informacion = new StringBuilder();
        int contadorEmpleados = 0;
        int sumaEdades = 0;
        int contadorEdades = 0;
        int sueldo = 0;
        int sueldoMinimo = 0;
        int sueldoMaximo = 0;

        XmlResourceParser xrp = contexto.getResources().getXml(R.xml.empleados);
        int eventType = xrp.getEventType();
        informacion.append("Inicio . . . \n");
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xrp.getName().equals("empleado")) {
                        esEmpleado = true;
                        informacion.append("EMPLEADO " + ++contadorEmpleados + "\n");
                    }
                    if (esEmpleado) {
                        if (xrp.getName().equals("nombre"))
                            esNombre = true;
                        if (xrp.getName().equals("puesto"))
                            esPuesto = true;
                        if (xrp.getName().equals("edad"))
                            esEdad = true;
                        if (xrp.getName().equals("sueldo"))
                            esSueldo = true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (esEmpleado) {
                        if (esNombre)
                            informacion.append("Nombre: " + xrp.getText() + "\n");
                        if (esPuesto) {
                            informacion.append("Puesto: " + xrp.getText() + "\n");
                        }
                        if (esEdad) {
                            informacion.append("Edad: " + xrp.getText() + " años\n");
                            sumaEdades += Double.parseDouble(xrp.getText());
                            contadorEdades++;
                        }
                        if (esSueldo) {
                            informacion.append("Sueldo: " + xrp.getText() + " euros\n");
                            sueldo = Integer.parseInt(xrp.getText());
                            if(sueldo > sueldoMaximo)
                                sueldoMaximo = sueldo;
                            if(sueldo < sueldoMaximo)
                                sueldoMinimo = sueldo;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xrp.getName().equals("empleado"))
                        esEmpleado = false;
                    if (xrp.getName().equals("nombre"))
                        esNombre = false;
                    if (xrp.getName().equals("puesto"))
                        esPuesto = false;
                    if (xrp.getName().equals("edad"))
                        esEdad = false;
                    if (xrp.getName().equals("sueldo"))
                        esSueldo = false;
                    break;
            }
            eventType = xrp.next();
        }
        informacion.append("Fin\n");
        informacion.append("La edad media es de " + sumaEdades / contadorEdades + " años\n");
        informacion.append("El sueldo minimo es de " + sueldoMinimo + " euros\n");
        informacion.append("El sueldo maximo es de " + sueldoMaximo + " euros");

        return informacion.toString();
    }
}
