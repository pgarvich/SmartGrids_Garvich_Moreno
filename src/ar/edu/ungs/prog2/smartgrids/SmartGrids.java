package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SmartGrids implements ISmartGrids{

	private LinkedHashMap<String, Planta> plantas;

    public SmartGrids(){
        this.plantas = new LinkedHashMap<>();
    }

    //////////////////////////////AUXILIARES////////////////////////////////////////////
    ///
 
	
    private Planta obtenerPlantaSegunNombre(String nombre) {
        Planta planta = plantas.get(nombre);
        if (planta == null) {
            throw new RuntimeException("La planta con nombre '" + nombre + "' no existe.");
        }
        return planta;
    }
    
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
        List<String> resultado = new ArrayList<>();

        for (Planta planta : plantas.values()) {
            resultado.add(planta.getDetalleFormateado());
        }
        
        return resultado;
    }

    @Override
    public double capacidadMinimaCuota(String nombre) {
    	
    	Planta planta = obtenerPlantaSegunNombre(nombre);
    	return planta.capacidadMinimaCuota();
    	
    }

    @Override
    public void activarPlanta(String nombre, String fechaHora) {
        Planta planta = obtenerPlantaSegunNombre(nombre);
        LocalDateTime fecha = parseoFecha(fechaHora);
        planta.activar(fecha);
    }

    @Override
    public void desactivarPlanta(String nombre, String fechaHora) {
        Planta planta = obtenerPlantaSegunNombre(nombre);
        LocalDateTime fecha = parseoFecha(fechaHora);
        planta.desactivar(fecha);
    }

    @Override
    public boolean estaActiva(String nombre) {
        Planta planta = obtenerPlantaSegunNombre(nombre);
        return planta.estaActiva();
    }

    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias) {
    	
    	Planta planta = obtenerPlantaSegunNombre(nombre);
    	LocalDateTime fecha = parseoFecha(fechaHora);
    	RegistroMonitoreo registro = new RegistroMonitoreo(fecha, energiaGenerada, incidencias);
    	planta.agregarMonitoreo(registro);

    }


	@Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double velocidadViento) {

		Planta planta = obtenerPlantaSegunNombre(nombre);
		LocalDateTime fecha = parseoFecha(fechaHora);
		RegistroMonitoreo registro = new RegistroMonitoreo(fecha, energiaGenerada, incidencias, velocidadViento);
		planta.agregarMonitoreo(registro);
		
    }

    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double co2Emitido, double combustibleUtilizado) {

    	Planta planta = obtenerPlantaSegunNombre(nombre);
    	LocalDateTime fecha = parseoFecha(fechaHora);
    	RegistroMonitoreo registro = new RegistroMonitoreo(fecha, energiaGenerada, incidencias, co2Emitido, combustibleUtilizado);
    	planta.agregarMonitoreo(registro);

    }

    @Override
    public double totalGenerado(String nombre) {
        Planta planta = obtenerPlantaSegunNombre(nombre); // Lanza RuntimeException si no existe
        return planta.totalGenerado();
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
