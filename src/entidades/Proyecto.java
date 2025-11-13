package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Proyecto {

    private int numeroID;
    private String clienteNombre;
    private String clienteEmail;
    private int clienteNumero;
    private String direccion;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaFin;
    private LocalDate fechaRealFin;
    private double costoFinal;
    private HashMap<String,Tarea> listaTareas;
    private ArrayList<Empleado> historialEmpleados;
    private String estado;

    // Constructor que usa los arrays de titulos/descripcion/duracion
    public Proyecto(int numeroID, String[] titulos, String[] descripcion, double[] duracion,
                    String direccion, String[] cliente, String inicio, String fin) {
        this.numeroID = numeroID;
        this.direccion = direccion;
        this.clienteNombre = cliente[0] != null ? cliente[0] : "";
        this.clienteEmail = cliente[1] != null ? cliente[1] : "";
        this.clienteNumero = (cliente[2].isEmpty()) ? 0 : Integer.parseInt(cliente[2]); 
        // agregue ternario para que no de error si el usuario pone 0. 
        this.fechaInicio = LocalDate.parse(inicio);
        this.fechaEstimadaFin = LocalDate.parse(fin);
        this.fechaRealFin = this.fechaEstimadaFin;
        this.estado = Estado.pendiente;
        this.costoFinal = 0;
        this.listaTareas = new HashMap<>();
        this.historialEmpleados = new ArrayList<>();
        
        for (int i = 0; i < titulos.length; i++) {
            Tarea nuevaTarea = new Tarea(titulos[i], descripcion[i], duracion[i]);
            listaTareas.put(titulos[i], nuevaTarea);
        }
        
    }
    
    public void finalizarProyecto() {
    	this.estado = Estado.finalizado;
    	this.fechaRealFin = LocalDate.parse(Estado.finalizado);
    	this.calcularCostoFinal();
    	
    	for (Tarea t : listaTareas.values()) {
            if (t.getResponsable() != null) {
                t.getResponsable().setAsignado(false);
            }
        }
    }
	
    public double calcularCostoFinal() {
        double costoTareas = 0;
        
        for (Tarea t : listaTareas.values()) {
            costoTareas += t.calcularCosto();
        }
        
        if (tuvoRetrasos()) {
            this.costoFinal = costoTareas * 1.25;
        } else {
            this.costoFinal = costoTareas * 1.35;
        }
        
        return this.costoFinal;
    }

    public boolean tuvoRetrasos(){
    	for (Tarea t : listaTareas.values()) {
    		if (t.tuvoRetrasos()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //============================================================
    //MARCA SI LA TAREA FINALIZO
    //============================================================
    // ERROR LISTA TAREAS
	public void finalizacion(String titulo, int dias, boolean terminado) {
        Tarea tarea= listaTareas.get(titulo);
        if (tarea == null) throw new IllegalArgumentException ("no existe ninguna tarea con ese ID");
        if (tarea.getTerminado()) {
	        tarea.dias= dias;
	        tarea.setTerminado(terminado);
	        tarea.duracionTotal= tarea.cantDias + tarea.diasRetraso;
    }
	}  

	public ArrayList<Empleado> obtenerHistorialEmpleados() {
		return historialEmpleados;
	}
	
	public HashMap<String, Tarea> obtenerListaTareas() {
		return listaTareas;
	}

//me permite no tener que hacer un ciclo for para encontrar el titulo. O(1)
	public Tarea getTareaPorTitulo(String titulo) throws Exception {
	    Tarea t = this.listaTareas.get(titulo);
	    if (t == null) {
	        throw new Exception("La tarea con el título '" + titulo + "' no existe en este proyecto.");
	    }
	    return t;
	}
	
	public void setEstado(String estado) {
		 this.estado = estado;
	}
	
	public int getID() {
		return numeroID;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public String getDireccion(){
		return direccion;
	}
	
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	
	public LocalDate getFechaEstimadaFin() {
		return fechaEstimadaFin;
	}
	
	public LocalDate getRealFin() {
		return fechaRealFin;
	}
	
	public double getCostoFinal() {
		return costoFinal;
	}
	
	public ArrayList<Empleado> getHistorialEmpleados() {
		return historialEmpleados;
	}	
	
	public void setTareas(String titulo, Tarea t) {
		listaTareas.put(titulo, t);
	}
	
	public LocalDate getFechaRealFin() {
		return fechaRealFin;
	}
	
	public void setFechaRealFin(LocalDate fecha) {
		this.fechaRealFin = fecha;
	}	
	
	public void setFechaEstimadaFin(LocalDate fecha) {
		this.fechaEstimadaFin = fecha;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();  // ✅ STRINGBUILDER (obligatorio)
	    
	    // ═══════════════════════════════════════════════════════════
	    // ENCABEZADO
	    // ═══════════════════════════════════════════════════════════
	    sb.append("═══════════════════════════════════════\n");
	    sb.append("           PROYECTO #").append(numeroID).append("\n");
	    sb.append("═══════════════════════════════════════\n\n");
	    
	    // ═══════════════════════════════════════════════════════════
	    // INFORMACIÓN GENERAL
	    // ═══════════════════════════════════════════════════════════
	    sb.append("📍 DOMICILIO:\n");
	    sb.append("   ").append(direccion).append("\n\n");
	    
	    // ═══════════════════════════════════════════════════════════
	    // INFORMACIÓN DEL CLIENTE
	    // ═══════════════════════════════════════════════════════════
	    sb.append("👤 CLIENTE:\n");
	    sb.append("   Nombre: ").append(clienteNombre).append("\n");
	    sb.append("   Email: ").append(clienteEmail).append("\n");
	    sb.append("   Teléfono: ").append(clienteNumero).append("\n\n");
	    
	    // ═══════════════════════════════════════════════════════════
	    // ESTADO Y FECHAS
	    // ═══════════════════════════════════════════════════════════
	    sb.append("📊 ESTADO DEL PROYECTO:\n");
	    sb.append("   Estado actual: ").append(estado).append("\n");
	    sb.append("   Fecha de inicio: ").append(fechaInicio).append("\n");
	    sb.append("   Fecha estimada de fin: ").append(fechaEstimadaFin).append("\n");
	    sb.append("   Fecha real de fin: ").append(fechaRealFin).append("\n\n");
	    
	    // ═══════════════════════════════════════════════════════════
	    // LISTA DE TAREAS (FOREACH OBLIGATORIO)
	    // ═══════════════════════════════════════════════════════════
	    sb.append("📋 TAREAS DEL PROYECTO:\n");
	    sb.append("───────────────────────────────────────\n");
	    
	    if (listaTareas.isEmpty()) {
	        sb.append("   (No hay tareas registradas)\n");
	    } else {
	        int contador = 1;
	        for (Tarea t : listaTareas.values()) {  // ✅ FOREACH (obligatorio)
	            sb.append("   ").append(contador).append(". ");
	            sb.append(t.toString());  // ⚠️ toString() de Tarea SOLO devuelve título
	            
	            // Indicar si está terminada (si tu compañero tiene el método)
	            if (t.getTerminado()) {
	                sb.append(" ✓ (Finalizada)");
	            }
	            sb.append("\n");
	            
	            contador++;
	        }
	    }
	    sb.append("\n");
	    
	    // ═══════════════════════════════════════════════════════════
	    // INFORMACIÓN FINANCIERA
	    // ═══════════════════════════════════════════════════════════
	    sb.append("💰 INFORMACIÓN FINANCIERA:\n");
	    sb.append("───────────────────────────────────────\n");
	    sb.append("   Costo total: $").append(String.format("%.2f", costoFinal)).append("\n");
	    
	    // ⚠️ Esto lo podrás activar cuando tengas el método tuvoRetrasos()
	    // sb.append("   Tuvo retrasos: ").append(tuvoRetrasos() ? "Sí" : "No").append("\n");
	    sb.append("   Tuvo retrasos: (Pendiente de implementar)\n");
	    
	    sb.append("\n");
	    
	    // ═══════════════════════════════════════════════════════════
	    // HISTORIAL DE EMPLEADOS
	    // ═══════════════════════════════════════════════════════════
	    sb.append("👥 EMPLEADOS ASIGNADOS:\n");
	    sb.append("───────────────────────────────────────\n");
	    
	    if (historialEmpleados.isEmpty()) {
	        sb.append("   (No hay empleados asignados)\n");
	    } else {
	        for (Empleado emp : historialEmpleados) {  // ✅ FOREACH (obligatorio)
	            sb.append("   - Legajo: ").append(emp.getLegajo());
	            sb.append(" | Nombre: ").append(emp.getNombre());
	            sb.append("\n");
	        }
	    }
	    
	    sb.append("\n");
	    
	    // ═══════════════════════════════════════════════════════════
	    // PIE
	    // ═══════════════════════════════════════════════════════════
	    sb.append("═══════════════════════════════════════\n");
	    
	    return sb.toString();
	}


}