# Evaluación II-2024 
<hr>

### <br> Asignatura: Patrones de Software y Programación <br> Profesor: Daniel San Martín <br> Fecha: 03-12-2024 14:30-17:00

<hr>

### Resultados de Aprendizaje

RA3: Definir un conjunto de tests para diversos algoritmos complejos de acuerdo a los requisitos del problema <br>
RA5: Implementar un conjunto de algoritmos dado un conjunto de algoritmos <br>
RA6: Optimizar algoritmos ya implementados obteniendo un rendimiento óptimo de acuerdo a las restricciones del problema

<hr>

### Enunciado

Una compañía de seguros recibe solicitudes de pólizas que se escanean en un sistema. La copia escaneada de la imagen se 
almacena en un sistema de gestión de documentos empresarial. El software OCR (Reconocimiento Óptico de Caracteres) extrae 
datos de la imagen escaneada y los almacena en la base de datos de la aplicación. Antes de almacenar los datos 
en la base de datos, los datos deben pasar por una serie de validaciones de reglas de negocio, y la póliza se marca con 
un estado basado en el resultado de las reglas de negocio.

Una aplicación web es utilizada por el departamento de suscripción para revisar la información de una póliza, 
limpiar cualquier dato interpretado incorrectamente por el software OCR y, en última instancia, permitir al suscriptor 
evaluar la asegurabilidad del cliente. Cualquier cambio en la información de la póliza debe pasar por el mismo conjunto 
de validaciones de reglas de negocio. Sin embargo, si alguna de las reglas de validación falla, en lugar de marcar la póliza, 
se solicita al suscriptor que corrija la información. En este escenario, tenemos dos sistemas independientes que deben 
compartir algunas reglas de validación comunes.

El software OCR envía la alimentación de datos en formato XML. El primer paso para validar la información nos lleva 
a crear una clase **Poliza**. El constructor de la clase **Poliza** acepta la cadena XML y la analiza para construir el 
objeto **Poliza**. El método **validar** se puede llamar entonces para asegurar que los datos son correctos antes de 
llamar al método **guardar** para almacenar los datos en la base de datos de la aplicación.


### Problemática

El resultado es una instancia válida de **Poliza** que ahora se puede validar y guardar en la base de datos. El enfoque anterior 
funciona bien para la aplicación de escaneo OCR. Sin embargo, **Poliza** está fuertemente acoplada al comportamiento XML y no se 
puede usar en la aplicación de suscripción. Para que el comportamiento de validación y persistencia de la clase **Poliza**
sea reutilizable en otra aplicación, no solo debemos desacoplar la clase **Poliza** de esta cadena XML, sino también 
asegurarnos de que la clase **Poliza** pueda implementarse de manera independiente al comportamiento XML.

En esta situación, debido a que la clase **Poliza** depende tanto de la funcionalidad de análisis XML, el comportamiento **Poliza** 
que queremos reutilizar no se puede implementar por separado. En su lugar, un solo módulo **org.poliza.jar** es nuestra única unidad implementable, como se encuentra actualmente el proyecto.


### Desarrollo de una Solución

1. Deberá crear dos bundles, uno llamado **org.poliza** (que ya existe) y otro llamado **org.polizaxml**.
2. Para la clase **Poliza**, crear un nuevo método estático llamado **construirPoliza**, el cual recibirá como parámetro 
una abstracción llamada **ConstructorPoliza** capaz de construir una **Poliza**. En el cuerpo de este nuevo método (**construirPoliza**), la 
abstracción pasada como parámetro ejecutará un método llamado **construir()**. 
El constructor de **Poliza**, recibirá como parámetros variables que serán atribuidas a sus atributos de clase. Algo importante 
a tomar en cuenta es que la clase **Poliza** no debe tener acoplamiento con ninguna API XML. 
3. Crear la nueva clase que se muestra a continuación. Usted deberá organizar las clases/abstracciones en los dos bundles mencionados para 
que efectivamente ocurra independencia en el deployment. 


```
    import java.io.*;
    import javax.xml.parsers.*;

    import org.poliza.ConstructorPoliza;
    import org.poliza.ManejadorDefectoPoliza;
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
```

4. Las alteraciones mencionadas también afectarán a la clase **ManejadorDefectoPoliza**, por lo que deberá realizar
pequeños cambios en esta.
5. Si ha realizado correctamente los pasos anteriores, el test que se encuentra en la clase **ModularidadTest (testDespliegueIndependiente)**
pasará sin problemas.

### Evaluación

1. Que los pasos mencionados anteriormente, para desacoplar la construcción de una Poliza y el formato de construcción
se hayan realizado correctamente **(50pts)**, verificables con el caso de test de la clase  **ModularidadTest**.
2. Construir un caso de test en una nueva clase y método llamado **EvaluaciónTest.testConstruirPoliza**,  para
   verificar el correcto funcionamiento de la clase **ConstructorPolizaXML**, que es responsable de crear una instancia de la clase **Poliza**. (**25pts**)

Debe escribir el test utilizando **Mockito** para:
* Simular la creación de una Poliza con estos datos: `new Poliza("Juan", "Pérez", "no", null, "soltero");`
* Verificar que el método `construir()` se llama correctamente y devuelve la póliza esperada.

3. Construir un caso de test en la clase **EvaluaciónTest** llamado **testPoliza** que verifique que los datos que 
han sido ingresados por medio de un xml, son los mismos que tiene la poliza creada. Utilice el siguiente xml para la prueba (**25pts**):

       <poliza>
          <nombre>Juan</nombre>
          <apellido>Perez</apellido>
          <estadoCivil>C</estadoCivil>
          <fechaNacimiento>01/10/1967</fechaNacimiento>
          <fumador>N</fumador>
       </poliza>

### Consideraciones

1. Todos los bundles deben ser registrados en una biblioteca de bundles (verificar los POM.xml).
2. Despliegue: `mvn -pl org.poliza,org.test clean deploy`
3. Test: `mvn test`
4. Limpieza: `mvn clean`
5. Dependencias como Mockito ya se encuentran en el POM.xml del bundle **org.test**.
6. Puede ejecutar los casos de test directamente seleccionando la clase de test y correrla en el IDE.
