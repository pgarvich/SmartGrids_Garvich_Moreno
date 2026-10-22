package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
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
    
    private LocalDateTime parsearFechaInicio(String fechaStr) {
        try {
            if (fechaStr.length() == 10) { // Formato "yyyy-MM-dd"
                return LocalDate.parse(fechaStr).atStartOfDay();
            }
            return LocalDateTime.parse(fechaStr);
        } catch (Exception e) {
            throw new RuntimeException("El formato de fecha es inválido: " + fechaStr);
        }
    }

    private LocalDateTime parsearFechaFin(String fechaStr) {
        try {
            if (fechaStr.length() == 10) { // Formato "yyyy-MM-dd"
                return LocalDate.parse(fechaStr).atTime(23, 59, 59);
            }
            return LocalDateTime.parse(fechaStr);
        } catch (Exception e) {
            throw new RuntimeException("El formato de fecha es inválido: " + fechaStr);
        }
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
        Planta planta = obtenerPlantaSegunNombre(nombre); // Lanza RuntimeException si no existe
        return planta.incidenciasPorHora();
    }

    @Override
    public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
        Planta planta = obtenerPlantaSegunNombre(nombre);

        LocalDateTime desde = parsearFechaInicio(fechaDesde);
        LocalDateTime hasta = parsearFechaFin(fechaHasta);
        
        if (hasta.isBefore(desde)) {
            throw new RuntimeException("La fecha inicial no puede ser posterior a la final.");
        }
                
        return planta.histogramaPlanta(desde, hasta);        
    }

    @Override
    public Map<String, Integer> plantasRenovablesPorFuente() {
    	Map<String, Integer> renovablesPorFuente = new HashMap<>(Map.of(
    		    "Sol", 0,
    		    "Agua", 0,
    		    "Viento", 0
    		));
    	
    	for (Planta p : plantas.values()) {
            if (p instanceof PlantaSolar) {
                renovablesPorFuente.put("Sol", renovablesPorFuente.get("Sol") + 1);
            } else if (p instanceof PlantaHidraulica) {
                renovablesPorFuente.put("Agua", renovablesPorFuente.get("Agua") + 1);
            } else if (p instanceof PlantaEolica) {
                renovablesPorFuente.put("Viento", renovablesPorFuente.get("Viento") + 1);
            }
    	}
        return renovablesPorFuente;
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
