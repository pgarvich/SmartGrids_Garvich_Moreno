package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartGrids implements ISmartGrids{

	private Map<String, Planta> plantas;

    public SmartGrids(){
        this.plantas = new HashMap<>();
    }

    //////////////////////////////AUXILIARES////////////////////////////////////////////
    private void nombrePlantaYaExiste(String nombre) {
    	if(plantas.containsKey(nombre)) {
    	throw new RuntimeException("Una planta con este nombre ya ha sido registrada.");
    	}
    }
    
    private LocalDateTime parseoFecha(String fecha) {
    	LocalDateTime fechaPuesta;
        try {
            fechaPuesta = LocalDateTime.parse(fecha);
        } catch (Exception e) {
            throw new RuntimeException("El formato de fecha y hora es inválido: " + fecha);
        }
        return fechaPuesta;
    }
    //////////////////////////////AUXILIARES////////////////////////////////////////////

    @Override
    public void registrarPlantaSolar(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, double superficieCubierta, int cantidadPaneles, double horasRadiacionDiaria) {

    	nombrePlantaYaExiste(nombre);
    	LocalDateTime fechaParseada = parseoFecha(fechaHoraPuestaEnFuncionamiento);    	
     	PlantaSolar plantaNueva = new PlantaSolar(nombre, direccion, capacidadMaxima, fechaParseada, fuentePrimaria, eficienciaPromedio, superficieCubierta, cantidadPaneles, horasRadiacionDiaria);
    	plantas.put(nombre, plantaNueva);
    }

    @Override
    public void registrarPlantaEolica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadAerogeneradores, double alturaTorres, double velocidadMinimaViento) {

    	nombrePlantaYaExiste(nombre);
    	LocalDateTime fechaParseada = parseoFecha(fechaHoraPuestaEnFuncionamiento);
    	PlantaEolica plantaNueva = new PlantaEolica(nombre, direccion, capacidadMaxima, fechaParseada, fuentePrimaria, eficienciaPromedio, cantidadAerogeneradores, alturaTorres, velocidadMinimaViento);
    	plantas.put(nombre, plantaNueva);
    }

    @Override
    public void registrarPlantaHidraulica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadTurbinas, double capacidadEmbalse) {

    	nombrePlantaYaExiste(nombre);
    	LocalDateTime fechaParseada = parseoFecha(fechaHoraPuestaEnFuncionamiento);
    	PlantaHidraulica plantaNueva = new PlantaHidraulica(nombre, direccion, capacidadMaxima, fechaParseada, fuentePrimaria, eficienciaPromedio, cantidadTurbinas, capacidadEmbalse);
    	plantas.put(nombre, plantaNueva);
    }

    @Override
    public void registrarPlantaTermica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String tipoCombustible, double objetivoEmisionesCo2PorHora, double capacidadAlmacenamientoCombustible) {

    	nombrePlantaYaExiste(nombre);
    	LocalDateTime fechaParseada = parseoFecha(fechaHoraPuestaEnFuncionamiento);
    	PlantaTermica plantaNueva = new PlantaTermica(nombre, direccion, capacidadMaxima, fechaParseada, tipoCombustible, objetivoEmisionesCo2PorHora, capacidadAlmacenamientoCombustible);
    	plantas.put(nombre, plantaNueva);
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
