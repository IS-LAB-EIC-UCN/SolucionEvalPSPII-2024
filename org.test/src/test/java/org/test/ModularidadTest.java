package org.test;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;

public class ModularidadTest {

    @Test
    public void testDespliegueIndependiente() {
        try {
            // Verificar carga de clase desde el bundle org.poliza
            Class<?> polizaClass = Class.forName("org.poliza.Poliza");
            assertNotNull("La clase Poliza no se cargó correctamente.", polizaClass);

            // Verificar carga de clase desde el bundle org.polizaxml
            Class<?> constructorPolizaXMLClass = Class.forName("org.polizaxml.ConstructorPolizaXML");
            assertNotNull("La clase ConstructorPolizaXML no se cargó correctamente.", constructorPolizaXMLClass);

            // Instanciar ConstructorPolizaXML mediante reflexión
            Constructor<?> constructor = constructorPolizaXMLClass.getConstructor(String.class);
            Object constructorInstance = constructor.newInstance("<poliza><nombre>Juan</nombre></poliza>");
            assertNotNull("No se pudo crear la instancia de ConstructorPolizaXML.", constructorInstance);

            // Invocar el método construir() y validar el resultado
            Method construirMethod = constructorPolizaXMLClass.getMethod("construir");
            Object polizaInstance = construirMethod.invoke(constructorInstance);
            assertNotNull("El método construir no retornó una instancia de Poliza.", polizaInstance);

        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Error durante la ejecución de la prueba: " + e.getMessage();
        }
    }
}