package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaEolica extends Planta{
    private String fuentePrimaria;
    private double eficienciaPromedio;
    private int cantidadAerogeneradores;
    private double alturaTorres;
    private double velocidadMinimaViento;


    public PlantaEolica(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuesta,
                        String fuentePrimaria, double eficienciaPromedio, int cantidadAerogeneradores,
                        double alturaTorres, double velocidadMinimaViento) {

        super(nombre, direccion, capacidadMaxima, fechaPuesta);
        this.fuentePrimaria = fuentePrimaria;
        this.eficienciaPromedio = eficienciaPromedio;
        this.cantidadAerogeneradores = cantidadAerogeneradores;
        this.alturaTorres = alturaTorres;
        this.velocidadMinimaViento = velocidadMinimaViento;
    }

    @Override
    public double capacidadMinimaCuota() { // Fórmula del test: capacidad \* eficiencia
         return getCapacidadMaxima() * eficienciaPromedio;
    }

    @Override
    public boolean cumpleCuota(RegistroMonitoreo m) {
        return m.getEnergiaGenerada() >= capacidadMinimaCuota();
    }

    public String getFuentePrimaria() {
        return fuentePrimaria;
    }

    public double getVelocidadMinimaViento() {
        return velocidadMinimaViento;
    }



}
