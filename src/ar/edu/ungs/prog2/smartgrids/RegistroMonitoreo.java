package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroMonitoreo {
    private LocalDateTime fechaHora;
    private double energiaGenerada;
    private int incidencias;
    

    private Double velocidadViento;
    private Double co2Emitido;
    private Double combustibleUtilizado;

    // constructor base (plantas solares e hidraulicas)
    public RegistroMonitoreo(LocalDateTime fechaHora, double energiaGenerada, int incidencias) {
        validarDatosBasicos(fechaHora, energiaGenerada, incidencias);
        this.fechaHora = fechaHora;
        this.energiaGenerada = energiaGenerada;
        this.incidencias = incidencias;
    }


    // constructor para plantas eolicas
    public RegistroMonitoreo(LocalDateTime fechaHora, double energiaGenerada, int incidencias, double velocidadViento) {
        this(fechaHora, energiaGenerada, incidencias);
        if (velocidadViento < 0) {
            throw new RuntimeException("La velocidad del viento no puede ser negativa.");
        }
        this.velocidadViento = velocidadViento;
    }


    //constructor para plantas termicas
    public RegistroMonitoreo(LocalDateTime fechaHora, double energiaGenerada, int incidencias, double co2Emitido, double combustibleUtilizado) {
        this(fechaHora, energiaGenerada, incidencias); if (co2Emitido < 0 || combustibleUtilizado < 0) {
            throw new RuntimeException("Las emisiones de CO2 y el combustible no pueden ser negativos."); }
        this.co2Emitido = co2Emitido;
        this.combustibleUtilizado = combustibleUtilizado;
    }

    private void validarDatosBasicos(LocalDateTime fechaHora, double energia, int incidencias) {
        if (fechaHora == null) {
            throw new RuntimeException("La fecha y hora no pueden ser nulas.");
        }
        if (energia < 0 || incidencias < 0) {
            throw new RuntimeException("La energía y las incidencias no pueden ser valores negativos.");
        }
    }


    /////////// GETTERS

    public LocalDateTime getFechaHora() { return fechaHora; }
    public double getEnergiaGenerada() { return energiaGenerada; }
    public int getIncidencias() { return incidencias; }
    public Double getVelocidadViento() { return velocidadViento; }
    public Double getCo2Emitido() { return co2Emitido; }
    public Double getCombustibleUtilizado() { return combustibleUtilizado; }
}
