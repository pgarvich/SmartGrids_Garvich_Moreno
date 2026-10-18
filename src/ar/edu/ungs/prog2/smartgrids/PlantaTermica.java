package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaTermica extends Planta{
    private String tipoCombustible;
    private double objetivoEmisionesCo2PorHora;
    private double capacidadAlmacenamientoCombustible;


    public PlantaTermica(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuesta,
                         String tipoCombustible, double objetivoEmisionesCo2PorHora,
                         double capacidadAlmacenamientoCombustible) {

        super(nombre, direccion, capacidadMaxima, fechaPuesta);
        this.tipoCombustible = tipoCombustible;
        this.objetivoEmisionesCo2PorHora = objetivoEmisionesCo2PorHora;
        this.capacidadAlmacenamientoCombustible = capacidadAlmacenamientoCombustible;
    }

    @Override
    public double capacidadMinimaCuota() { // Fórmula del test: capacidad / 2
         return getCapacidadMaxima() / 2.0;
        }

    @Override
    public boolean cumpleCuota(RegistroMonitoreo m) { // Debe generar al menos la cuota Y emitir estrictamente menos CO2 que el objetivo
        if (m.getCo2Emitido() == null) {
            return false;
        }
        return m.getEnergiaGenerada() >= capacidadMinimaCuota() && m.getCo2Emitido() < objetivoEmisionesCo2PorHora; }
}
