package org.polizaxml;

import java.util.*;
import java.io.*;
import javax.xml.parsers.*;

import org.poliza.ConstructorPoliza;
import org.poliza.Poliza;
import org.xml.sax.*;

public class ConstructorPolizaXML implements ConstructorPoliza {

    private String cadenaXML;
    private String nombre;
    private String apellido;
    private String fumador;
    private Date fechaNacimiento;
    private String estadoCivil;

    public ConstructorPolizaXML(String cadenaXML) {
        this.cadenaXML = cadenaXML;
    }

    public Poliza construir() {
        try {
            SAXParserFactory fabrica = SAXParserFactory.newInstance();
            SAXParser parser = fabrica.newSAXParser();
            InputSource fuente = new InputSource(new StringBufferInputStream(cadenaXML));
            parser.parse(fuente, new ManejadorDefectoPoliza(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Poliza(this.nombre, this.apellido, this.fumador, this.fechaNacimiento, this.estadoCivil);
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setFumador(String fumador) { this.fumador = fumador; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
}
