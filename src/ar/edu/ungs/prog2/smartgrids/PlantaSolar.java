package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaSolar extends PlantaRenovable{
    private double superficieCubierta;
    private int cantidadPaneles;
    private double horasRadiacionDiaria;

    public PlantaSolar(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuesta,
                       String fuentePrimaria, double eficienciaPromedio, double superficieCubierta,
                       int cantidadPaneles, double horasRadiacionDiaria){

        super(nombre, direccion, capacidadMaxima, fechaPuesta, fuentePrimaria, eficienciaPromedio);
        this.superficieCubierta = superficieCubierta;
        this.cantidadPaneles = cantidadPaneles;
        this.horasRadiacionDiaria = horasRadiacionDiaria;

    }
    
    @Override
    public void agregarMonitoreo(RegistroMonitoreo m) {
        // Si el registro trae velocidad de viento o datos térmicos, no corresponde a una solar
        if (m.tieneDatosEolicos() || m.tieneDatosTermicos()) {
            throw new RuntimeException("Tipo de monitoreo incorrecto para planta solar.");
        }
        super.agregarMonitoreo(m); // Ejecuta las validaciones comunes
    }

    @Override
    public double capacidadMinimaCuota() { // Fórmula del test: capacidad \* eficiencia \* horasRadiacion / 24
        return (getCapacidadMaxima() * super.getEficienciaPromedio() * horasRadiacionDiaria) / 24.0;
    }

    @Override
    public boolean cumpleCuota(RegistroMonitoreo m) {
        return m.getEnergiaGenerada() >= capacidadMinimaCuota();
    }


}
