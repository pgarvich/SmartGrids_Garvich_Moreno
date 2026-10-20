package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public abstract class PlantaNoRenovable extends Planta{
	
	private double objetivoEmisiones;
	
	public PlantaNoRenovable(String nombre, String direccion, double capacidadMaxima, LocalDateTime fechaPuestaEnFuncionamiento, double objEmisiones) {
		super(nombre, direccion, capacidadMaxima, fechaPuestaEnFuncionamiento);
		this.objetivoEmisiones = objEmisiones;
	}
	
	public double getObjetivoEmisiones() {
		return objetivoEmisiones;
	}

}
