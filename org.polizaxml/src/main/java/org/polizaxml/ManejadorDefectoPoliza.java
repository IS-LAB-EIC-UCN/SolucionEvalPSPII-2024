package org.polizaxml;

import java.util.Calendar;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.*;

public class ManejadorDefectoPoliza extends DefaultHandler {

    private ConstructorPolizaXML constructorPolizaXML;
    private String atributo;

    public ManejadorDefectoPoliza(ConstructorPolizaXML constructorPolizaXML) {
        this.constructorPolizaXML = constructorPolizaXML;
    }

    @Override
    public void characters(char[] ch, int inicio, int tamanho) {
        String elemento = new String(ch, inicio, tamanho);
        this.setAtributoPoliza(elemento);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        this.atributo = qName;
    }

    private void setAtributoPoliza(String valor) {
        if (this.atributo.equals("nombre")) {
            this.constructorPolizaXML.setNombre(valor);
        } else if (this.atributo.equals("apellido")) {
            this.constructorPolizaXML.setApellido(valor);
        } else if (this.atributo.equals("fechaNacimiento")) {
            Calendar cal = Calendar.getInstance();
            int mes = Integer.parseInt(valor.substring(0, 2));
            int dia = Integer.parseInt(valor.substring(3, 5));
            int anho = Integer.parseInt(valor.substring(6, 10));
            cal.set(anho, mes - 1, dia);
            this.constructorPolizaXML.setFechaNacimiento(cal.getTime());
        } else if (this.atributo.equals("fumador")) {
            this.constructorPolizaXML.setFumador(valor);
        } else if (this.atributo.equals("estadoCivil")) {
            this.constructorPolizaXML.setEstadoCivil(valor);
        }
    }

}
