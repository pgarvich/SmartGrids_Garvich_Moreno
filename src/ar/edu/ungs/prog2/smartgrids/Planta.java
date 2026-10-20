package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Planta {
	private Integer ID;
    private String nombre;
    private String direccion;
    private double capacidadMaxima;
    private LocalDateTime fechaPuestaEnFuncionamiento;
    private boolean activa;
    private List <RegistroMonitoreo> monitoreos;
    private int horasActiva;
    private HashMap <LocalDateTime, LocalDateTime> periodosInactiva;

    public Planta (String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuestaEnFuncionamiento) {

    	if (nombre == null || nombre.trim().isEmpty()) {
            throw new RuntimeException("El nombre de la planta no puede ser nulo o vacío.");
        }

        if (capacidadMaxima <= 0) {
            throw new RuntimeException("La capacidad máxima debe ser un valor positivo.");
        }

        if (fechaPuestaEnFuncionamiento == null) {
            throw new RuntimeException("La fecha de puesta en funcionamiento no puede ser nula.");
        }

        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadMaxima = capacidadMaxima;
        this.fechaPuestaEnFuncionamiento = fechaPuestaEnFuncionamiento;
        this.activa = true;
        this.monitoreos = new ArrayList<>();
        this.horasActiva = 0;
        this.periodosInactiva = new HashMap<>();

    }

    ////////////// METODOS ABSTRACTOS


    // calcula la capacidad minima de cada planta para que cumpla su cuota
    public abstract double capacidadMinimaCuota();

    // evalua si un registro de monitoreo en particular cumplio su cuota
    public abstract boolean cumpleCuota(RegistroMonitoreo m);



    ////////////// METODOS DE ESTADO Y MONITOREO

    public boolean estaActiva() {
        return this.activa;
    }

    public void activar() {
        this.activa = true;
    }

    public void desactivar() {
        this.activa = false;
    }


    // registro una nueva medicion horaria segun las reglas
    public void agregarMonitoreo(RegistroMonitoreo m) {
        if (!this.activa) {
            throw new RuntimeException("No se pueden agregar registros a una planta inactiva.");
        }
        if (m.getFechaHora().isBefore(this.fechaPuestaEnFuncionamiento)) {
            throw new RuntimeException("La fecha del monitoreo es anterior a la puesta en marcha de la planta.");
        }

        for (RegistroMonitoreo reg : this.monitoreos) {     // verifico que no se duplique ningun registro segun fecha y hora
            if (reg.getFechaHora().equals(m.getFechaHora())) {
                throw new RuntimeException("Ya existe un registro de monitoreo para la fecha y hora: " + m.getFechaHora());
            }
        }
        this.monitoreos.add(m);

    }

    // devuelve el total de energia generada
    public double totalGenerado() {
        double total = 0.0;
        for (RegistroMonitoreo m : this.monitoreos) {
            total += m.getEnergiaGenerada();
        }
        return total;
    }


    // genera un arreglo de 24 posiciones con la suma acumulada de incidencias por hora del dia (0 a 23)
    public int[] incidenciasPorHora() {
        int[] resultado = new int[24];

        for (RegistroMonitoreo m : this.monitoreos) {
            int hora = m.getFechaHora().getHour();
            resultado[hora] += m.getIncidencias();
        }
        return resultado;
    }


    // formatea el detalle de la planta segun el requerimeniento del tp: nombre, activa/inactiva, fecha puesta en funcionamiento
    public String getDetalleFormateado() {
        String estadoStr = this.activa ? "ACTIVA" : "INACTIVA";
        return this.nombre + " (" + estadoStr + "): " + this.fechaPuestaEnFuncionamiento;
    }


    //////////// GETTERS

    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public double getCapacidadMaxima() { return capacidadMaxima; }
    public LocalDateTime getFechaPuestaEnFuncionamiento() { return fechaPuestaEnFuncionamiento; }
    public List<RegistroMonitoreo> getMonitoreos() { return monitoreos; }


}
