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
    
    
    public void finalizarProyecto(String fin) {
    	this.estado = Estado.finalizado;
    	this.fechaRealFin = LocalDate.parse(fin);
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
    	
    	if ((fechaRealFin) != null && fechaEstimadaFin != null && fechaRealFin.isAfter(fechaEstimadaFin)) {
    		return true;
    	}
    	
    	return false;
    }  

    public void agregarEmpleadoAlHistorial(Empleado e) {
    	if (e!=null && !historialEmpleados.contains(e)) {
    		historialEmpleados.add(e);
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
	    Tarea t = listaTareas.get(titulo);
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
	    // StringBuilder es OBLIGATORIO por el enunciado 
	    StringBuilder sb = new StringBuilder();
	    
	    // --- Datos del proyecto (número, domicilio, cliente, fecha fin)  ---
	    sb.append("Proyecto: #" + numeroID);
	    sb.append(" - Domicilio: ").append(direccion).append("\n");
	    sb.append("Cliente: ").append(clienteNombre);
	    sb.append(" - Fecha Finalización: ").append(fechaRealFin);
	    sb.append("\nCosto Final: $").append(String.format("%.2f", costoFinal));
	    sb.append("\nTuvo Retrasos: ").append(tuvoRetrasos() ? "SI" : "NO").append("\n\n");
	    
	    sb.append("TAREAS REALIZADAS:\n");
	    if (listaTareas.isEmpty()) {
	        sb.append("No hay tareas registradas\n");
	    } else {
	        for (Tarea t : listaTareas.values()) { 
	            sb.append(" - ").append(t.getTitulo());
	        }
	    }
	    
	    return sb.toString();
	}
}