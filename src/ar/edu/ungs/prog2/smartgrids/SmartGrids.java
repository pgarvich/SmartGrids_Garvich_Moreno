package ar.edu.ungs.prog2.smartgrids;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartGrids implements ISmartGrids{

	private Map<String, Planta> plantas;

    public SmartGrids(){
        this.plantas = new HashMap<>();
    }



    @Override
    public void registrarPlantaSolar(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, double superficieCubierta, int cantidadPaneles, double horasRadiacionDiaria) {

    	if(plantas.containsKey(nombre)) {
    		throw new RuntimeException("Una planta con este nombre ya ha sido registrada.");
    	}
    	
    	PlantaSolar plantaNueva = new PlantaSolar(fuentePrimaria, fuentePrimaria, horasRadiacionDiaria, null, fuentePrimaria, horasRadiacionDiaria, horasRadiacionDiaria, cantidadPaneles, horasRadiacionDiaria);
    	plantas.put(nombre, plantaNueva);
    }

    @Override
    public void registrarPlantaEolica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadAerogeneradores, double alturaTorres, double velocidadMinimaViento) {

    	if(plantas.containsKey(nombre)) {
    		throw new RuntimeException("Una planta con este nombre ya ha sido registrada.");
    	}
    	
    	PlantaEolica plantaNueva = new PlantaEolica(fuentePrimaria, fuentePrimaria, velocidadMinimaViento, null, fuentePrimaria, velocidadMinimaViento, cantidadAerogeneradores, velocidadMinimaViento, velocidadMinimaViento);
    }

    @Override
    public void registrarPlantaHidraulica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadTurbinas, double capacidadEmbalse) {

    	if(plantas.containsKey(nombre)) {
    		throw new RuntimeException("Una planta con este nombre ya ha sido registrada.");
    	}
    }

    @Override
    public void registrarPlantaTermica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String tipoCombustible, double objetivoEmisionesCo2PorHora, double capacidadAlmacenamientoCombustible) {

    	if(plantas.containsKey(nombre)) {
    		throw new RuntimeException("Una planta con este nombre ya ha sido registrada.");
    	}
    }

    @Override
    public List<String> consultarPlantas() {
        return List.of();
    }

    @Override
    public double capacidadMinimaCuota(String nombre) {
        return 0;
    }

    @Override
    public void activarPlanta(String nombre, String fechaHora) {

    }

    @Override
    public void desactivarPlanta(String nombre, String fechaHora) {

    }

    @Override
    public boolean estaActiva(String nombre) {
        return false;
    }

    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias) {

    }

    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double velocidadViento) {

    }

    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double co2Emitido, double combustibleUtilizado) {

    }

    @Override
    public double totalGenerado(String nombre) {
        return 0;
    }

    @Override
    public int[] incidenciasPorHora(String nombre) {
        return new int[0];
    }

    @Override
    public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
        return new int[0];
    }

    @Override
    public Map<String, Integer> plantasRenovablesPorFuente() {
        return Map.of();
    }

    @Override
    public List<String> plantasQueNuncaCumplieronCuota() {
        return List.of();
    }

    @Override
    public String horasSinVelocidadMinimaPorPlantaEolica() {
        return "";
    }

    @Override
    public int limpiarMonitoreosSinDatos() {
        return 0;
    }

    @Override
    public List<String> plantasOrdenadasPorEnergiaGenerada() {
        return List.of();
    }

    @Override
    public List<String> plantasInactivasEn(String fechaHora) {
        return List.of();
    }
}
