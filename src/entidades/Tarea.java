package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tarea {

	private String titulo;
	private String descripcion;
	private double cantDias;
	private double diasRetraso;
	private boolean terminado;
	private int dias;
	private Empleado responsable;
	private double duracionTotal;
	
	public Tarea (String titulo, String descripcion, double cantDias){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantDias = cantDias; // CORREGIR: estaba sin asignar
        this.diasRetraso = 0;
        this.dias = 0;
        this.terminado = false;
        this.responsable = null;
        this.duracionTotal = cantDias; // CORREGIR: usar el parámetro
    }

//    //============================================================
//    //BUSCA EMPLEADO EFICIENTE
//    //============================================================
//    // SUGERENCIA: Este método debería estar en HomeSolution, no en Tarea
//    // Una tarea solo conoce a SU responsable, no busca empleados
//    public Empleado buscarEmpleadoEficiente() {
//        if (responsable == null) {
//            return null;
//        }
//        return responsable;
//    }

    public void resgistrarRetraso(double cantidadDias) {
        if (cantidadDias < 0) throw new IllegalArgumentException ("Cantidad de dias de retraso invalidos");
        this.diasRetraso += cantidadDias; // Simplificar
        this.duracionTotal = diasRetraso + cantDias;
        if (responsable != null) {
            responsable.registrarRetraso(); // CORREGIR: nombre del método
        }
    }
    /**
     * Finaliza la tarea y libera al empleado asignado
     * El empleado queda en el historial del proyecto
     */
    public void finalizarTarea() {
    	terminado = true;
    	if (responsable != null) {
    	responsable.setAsignado(false);
    	}
    }
    
    /**
     * Reasigna la tarea a un nuevo empleado
     * Debe ser O(1) - el HashMap está en HomeSolution
     */
    public void reasignarEmpleado(Empleado nuevoEmpleado) {

    	if (nuevoEmpleado == null) {throw new IllegalArgumentException("El nuevo empleado no puede ser null.");}
    	if (nuevoEmpleado.getAsignado()) { throw new IllegalArgumentException("El empleado ya esta asignado!");}
    		
    	if (responsable != null) {
    		responsable.setAsignado(false);
    		responsable = nuevoEmpleado;
    	}
    	this.responsable = nuevoEmpleado; 
    	nuevoEmpleado.setAsignado(true);
    	nuevoEmpleado.agregarTareaAsignada(this); //THIS
    }

    public double calcularCosto() {
    	if (responsable==null) {return 0;}
    	else {return responsable.calcularCostoTarea(cantDias);}
    }
    
    public double getDuracionTotal() {
    	return duracionTotal = cantDias + diasRetraso;
    }

    public boolean tuvoRetrasos() {
    	return diasRetraso > 0;
    }
    
    public void agregarRetraso(double cantidadDias) {
        this.diasRetraso += cantidadDias;
        this.duracionTotal = cantDias + diasRetraso;
    }   
    
    public boolean tieneEmpleadoAsignado() {
    	return responsable != null;
    }

    public boolean tieneResponsable() {
        return responsable != null;
    }

    public void setResponsable(Empleado e) {
        this.responsable = e;
    }

    public boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(boolean estado) {
        this.terminado = estado; 
    }

    public String getTitulo() {
        return titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public double getCantDias() {
        return cantDias;
    }
    
    public double getDiasRetraso() {
        return diasRetraso;
    }
    
    public Empleado getResponsable() {
        return responsable;
    }
    
    public int getDias() {
        return dias;
    }

    @Override
    public String toString() {
    	return titulo;
    }

}