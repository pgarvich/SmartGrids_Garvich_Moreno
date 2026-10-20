package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public abstract class PlantaRenovable extends Planta{
	
	private String fuentePrimaria;
	private double eficienciaPromedio;
	
	public PlantaRenovable(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuesta, String fuentePrimaria, double eficienciaPromedio){
		super(nombre, direccion, capacidadMaxima, fechaPuesta);
		this.fuentePrimaria = fuentePrimaria;
		this.eficienciaPromedio = eficienciaPromedio;
		
	}
	
    public String getFuentePrimaria() {
        return fuentePrimaria;
    }

    public double getEficienciaPromedio() {
    	return eficienciaPromedio;
    }
}
