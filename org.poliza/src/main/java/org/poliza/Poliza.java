package org.poliza;

import java.util.Date;

public class Poliza {
    private String nombre;
    private String apellido;
    private String fumador;
    private Date fechaNacimiento;
    private String estadoCivil;

    public Poliza(String nombre, String apellido, String fumador, Date fechaNacimiento, String estadoCivil) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.fumador = fumador;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
    }

    public static Poliza construirPoliza(ConstructorPoliza constructorPoliza) {
        return constructorPoliza.construir();
    }

    void setNombre(String nombre) { this.nombre = nombre; }
    void setApellido(String apellido) { this.apellido = apellido; }
    void setFumador(String fumador) { this.fumador = fumador; }
    void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public String getNombre() { return this.nombre; }
    public Date getFechaNacimiento() { return this.fechaNacimiento; }
    public String getApellido() { return this.apellido; }
    public String getEstadoCivil() { return this.estadoCivil; }
    public String getFumador() { return this.fumador; }

    public void validar() {
        // Validar los datos.
    }

    public void guardar() {
        // Guardar los datos.
    }
}
