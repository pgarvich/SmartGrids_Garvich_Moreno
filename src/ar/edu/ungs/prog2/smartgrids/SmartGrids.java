package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    	//Hidroeléctricas y Solares
    @Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias) {
    	
    	Planta planta = obtenerPlantaSegunNombre(nombre);
    	LocalDateTime fecha = parseoFecha(fechaHora);
    	RegistroMonitoreo registro = new RegistroMonitoreo(fecha, energiaGenerada, incidencias);
    	planta.agregarMonitoreo(registro);

    }

    	//Eólicas
	@Override
    public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double velocidadViento) {

		Planta planta = obtenerPlantaSegunNombre(nombre);
		LocalDateTime fecha = parseoFecha(fechaHora);
		RegistroMonitoreo registro = new RegistroMonitoreo(fecha, energiaGenerada, incidencias, velocidadViento);
		planta.agregarMonitoreo(registro);
		
    }
		//Térmicas
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
    	ArrayList<String> plantasQueNoCumplieronNunca = new ArrayList<String>();
    	for(Planta p: this.plantas.values()) {
    		if(p.nuncaCumplioCuota()) {
    			plantasQueNoCumplieronNunca.add(p.getNombre());
    		}    		
    	}
    	return plantasQueNoCumplieronNunca;
    }

    @Override
    public String horasSinVelocidadMinimaPorPlantaEolica() {
        StringBuilder sb = new StringBuilder();
        sb.append("Plantas Eolicas: Horas con poco viento\n");

        for (Planta p : this.plantas.values()){
            if (p instanceof  PlantaEolica){    // filtro solamente entre plantas eolicas
                PlantaEolica eolica = (PlantaEolica) p;
                double velMinima = eolica.getVelocidadMinimaViento();

                StringBuilder bloquePlanta = new StringBuilder();

                for(int hora = 0; hora < 24; hora++){       // evaluo cada una de las 24 horas del dia
                    boolean tieneRegistros = false;
                    boolean alcanzoMinimo = false;
                    double maxVientoRegistrado = -1;

                    for (RegistroMonitoreo m : eolica.getMonitoreos()){     // veo si coincide una hora con la hora monitoreada
                        if (m.getFechaHora().getHour() == hora){
                            tieneRegistros = true;
                            Double viento = m.getVelocidadViento();

                            if (viento != null){
                                if (viento >= velMinima){
                                    alcanzoMinimo = true;       // si al menos una vez el viento alcanzo el minimo devuelve true
                                }
                                if (viento > maxVientoRegistrado){
                                    maxVientoRegistrado = viento;
                                }
                            }
                        }
                    }

                    //si se monitoreo esa hora pero nunca alcanzo la velocidad minima
                    if (tieneRegistros && !alcanzoMinimo){
                        bloquePlanta.append(String.format(" %02d: %d m/s\n", hora, (int) maxVientoRegistrado)); //   %02d y %d agregan los valores
                    }                                                                                            // de al lado, osea hora y maxVientoRegistrado
                }                                                                                                // (es muy parecido a como se hace en C)
                if (!bloquePlanta.isEmpty()){
                    sb.append("- ").append(eolica.getNombre()).append(":  ").append((int) velMinima).append(" m/s\n");
                    sb.append(bloquePlanta);
                }   // aca basicamente agrego to.do al stringbuilder principal paso por paso. El resultado final deberia de quedar "05: 1 m/s" por ejemplo,
                    // y el sb.append del final lo pone abajo del to.do en la lista de horas con poco viento
            }
        }

        return sb.toString();
    }

    @Override
    public int limpiarMonitoreosSinDatos() {
        int totalBorrados = 0;

        for (Planta p : this.plantas.values()){
            totalBorrados += p.limpiarMonitoreosSinDatos();
        }

        return totalBorrados;
    }

    private void ordenarPorEnergiaDescendente(List<Planta> lista){
        for (int i = 0; i < lista.size() - 1; i++){
            for (int j = i + 1; j < lista.size(); j++){     // Ordeno la lista de mayor a menor por totalGenerado
                if (lista.get(j).totalGenerado() > lista.get(i).totalGenerado()){
                    Planta aux = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, aux);
                }
            }
        }
    }

    @Override
    public List<String> plantasOrdenadasPorEnergiaGenerada() {
        List<Planta> listaPlantas = new ArrayList<>(this.plantas.values());

        ordenarPorEnergiaDescendente(listaPlantas);

        List<String> resultado = new ArrayList<>();
        for (Planta p : listaPlantas){
            resultado.add(p.getNombre() + ": " + p.totalGenerado());
        }

        return resultado;
    }

    @Override
    public List<String> plantasInactivasEn(String fechaHora) {
    	ArrayList<String> listaDePlantasInactivasEnHora = new ArrayList<>();
    	
    	LocalDateTime fechaHoraParseada = parseoFecha(fechaHora);
    	
    	for(Planta p: plantas.values()) {
    		if(p.estuvoInactivaEn(fechaHoraParseada)){
    			listaDePlantasInactivasEnHora.add(p.getNombre());
    		}
    		
    	}
    	
        return listaDePlantasInactivasEnHora;
    }
}
