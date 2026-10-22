package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaHidraulica extends PlantaRenovable{
    private String fuentePrimaria;
    private double eficienciaPromedio;
    private int cantidadTurbinas;
    private double capacidadEmbalse;


    public PlantaHidraulica(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuesta,
                            String fuentePrimaria, double eficienciaPromedio, int cantidadTurbinas,
                            double capacidadEmbalse) {

    	super(nombre, direccion, capacidadMaxima, fechaPuesta, fuentePrimaria, eficienciaPromedio);
        this.cantidadTurbinas = cantidadTurbinas;
        this.capacidadEmbalse = capacidadEmbalse;
    }
    
    
    @Override
    public void agregarMonitoreo(RegistroMonitoreo m) {
        // Si el registro trae velocidad de viento o datos térmicos, no corresponde a una hidroelectica
        if (m.tieneDatosEolicos() || m.tieneDatosTermicos()) {
            throw new RuntimeException("Tipo de monitoreo incorrecto para planta Hidraulica.");
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

    public String getFuentePrimaria() {
        return fuentePrimaria;
    }

}
