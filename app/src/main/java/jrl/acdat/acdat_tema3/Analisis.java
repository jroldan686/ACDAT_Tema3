package jrl.acdat.acdat_tema3;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Analisis {

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

    public static ArrayList<Noticia> analizarNoticias(File file) throws XmlPullParserException, IOException {
        boolean dentroItem = false;
        boolean dentroTitle = false;
        boolean dentroLink = false;
        boolean dentroDescription = false;
        boolean dentroPubDate = false;

        int eventType;
        ArrayList<Noticia> noticias = new ArrayList<Noticia>();
        Noticia actual = null;
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType=xpp.getEventType();
        while (eventType!=XmlPullParser.END_DOCUMENT ){
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equalsIgnoreCase("item")) {
                        dentroItem = true;
                        actual = new Noticia();
                    }
                    if(xpp.getName().equalsIgnoreCase("title") && dentroItem)
                        dentroTitle = true;
                    if(xpp.getName().equalsIgnoreCase("link") && dentroItem)
                        dentroLink = true;
                    if(xpp.getName().equalsIgnoreCase("description") && dentroItem)
                        dentroDescription = true;
                    if(xpp.getName().equalsIgnoreCase("pubDate") && dentroItem)
                        dentroPubDate = true;
                    break;
                case XmlPullParser.TEXT:
                    if(dentroTitle && dentroItem)
                        actual.setTitle(xpp.getText());
                    if(dentroLink && dentroItem)
                        actual.setLink(xpp.getText());
                    if(dentroDescription && dentroItem)
                        actual.setDescription(xpp.getText());
                    if(dentroPubDate && dentroItem)
                        actual.setPubDate(xpp.getText());
                    break;
                case XmlPullParser.END_TAG:
                    if(xpp.getName().equalsIgnoreCase("item")) {
                        dentroItem = false;
                        noticias.add(actual);
                    }
                    if(xpp.getName().equalsIgnoreCase("title") && dentroItem)
                        dentroTitle = false;
                    if(xpp.getName().equalsIgnoreCase("link") && dentroItem)
                        dentroLink = false;
                    if(xpp.getName().equalsIgnoreCase("description") && dentroItem)
                        dentroDescription = false;
                    if(xpp.getName().equalsIgnoreCase("pubDate") && dentroItem)
                        dentroPubDate = false;
                    break;
            }
            eventType = xpp.next();
        }
        throw new NullPointerException();
        //devolver el array de noticias
        //return noticias;
    }
}
