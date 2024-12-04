package org.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.polizaxml.ConstructorPolizaXML;
import org.poliza.Poliza;

import java.util.Calendar;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class EvaluacionTest {

    @Test
    public void testConstruirPoliza() {
        // Crear un mock del ConstructorPolizaXML
        ConstructorPolizaXML constructorMock = mock(ConstructorPolizaXML.class);

        // Crear un objeto Poliza simulado que será retornado por el mock
        Poliza polizaSimulada = new Poliza("Juan", "Pérez", "no", null, "soltero");

        // Configurar el mock: cuando se llame a construir(), devolver la póliza simulada
        when(constructorMock.construir()).thenReturn(polizaSimulada);

        // Llamar al método construir del mock
        Poliza polizaResultado = constructorMock.construir();

        // Verificar que se llamó al método construir() del mock
        verify(constructorMock, times(1)).construir();

        // Comprobar que la póliza devuelta es la misma simulada
        assertNotNull(polizaResultado);
        assertEquals("Juan", polizaResultado.getNombre());
        assertEquals("Pérez", polizaResultado.getApellido());
        assertEquals("no", polizaResultado.getFumador());
        assertEquals("soltero", polizaResultado.getEstadoCivil());
    }

    @Test
    public void testPoliza() throws Exception {

        String polizaXML = "<poliza>" +
                "<nombre>Juan</nombre>" +
                "<apellido>Perez</apellido>" +
                "<estadoCivil>C</estadoCivil>" +
                "<fechaNacimiento>01/10/1967</fechaNacimiento>" +
                "<fumador>N</fumador>" +
                "</poliza>";

        // Crear un objeto PolicyXMLBuilder con el XML proporcionado
        ConstructorPolizaXML constructorPolizaXML = new ConstructorPolizaXML(polizaXML);


        // Usar el método
        Poliza poliza = Poliza.construirPoliza(constructorPolizaXML);

        // Verificar las propiedades de la política
        assertEquals("Juan", poliza.getNombre());
        assertEquals("Perez", poliza.getApellido());
        assertEquals("C", poliza.getEstadoCivil());
        assertEquals("N", poliza.getFumador());

        // Verificar la fecha de nacimiento
        Calendar cal = Calendar.getInstance();
        cal.setTime(poliza.getFechaNacimiento());
        assertEquals(10, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(1967, cal.get(Calendar.YEAR));
        assertEquals(1, cal.get(Calendar.MONTH) + 1);
    }
}