package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaEolica extends PlantaRenovable{
    private String fuentePrimaria;
    private double eficienciaPromedio;
    private int cantidadAerogeneradores;
    private double alturaTorres;
    private double velocidadMinimaViento;


    public PlantaEolica(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuesta,
                        String fuentePrimaria, double eficienciaPromedio, int cantidadAerogeneradores,
                        double alturaTorres, double velocidadMinimaViento) {

    	super(nombre, direccion, capacidadMaxima, fechaPuesta, fuentePrimaria, eficienciaPromedio);
        this.cantidadAerogeneradores = cantidadAerogeneradores;
        this.alturaTorres = alturaTorres;
        this.velocidadMinimaViento = velocidadMinimaViento;
    }

    	
    @Override
    public void agregarMonitoreo(RegistroMonitoreo m) {
        // Si el registro datos térmicos o no trae velocidad viento no sirve para plantas eolicas
        if (!m.tieneDatosEolicos() || m.tieneDatosTermicos()) {
            throw new RuntimeException("Tipo de monitoreo incorrecto para planta solar.");
        }
        super.agregarMonitoreo(m); // Ejecuta las validaciones comunes
    }

    @Override
    public double capacidadMinimaCuota() { // Fórmula del test: capacidad \* eficiencia
         return getCapacidadMaxima() * super.getEficienciaPromedio();
    }

    @Override
    public boolean cumpleCuota(RegistroMonitoreo m) {
        return m.getEnergiaGenerada() >= capacidadMinimaCuota();
    }

    public double getVelocidadMinimaViento() {
        return velocidadMinimaViento;
    }



}
