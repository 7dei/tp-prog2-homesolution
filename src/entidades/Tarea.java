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

    public void resgistrarRetraso(int dias) {
        if (dias < 0) throw new IllegalArgumentException ("Cantidad de dias de retraso invalidos");
        this.diasRetraso += dias; // Simplificar
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
    	if (responsable != null) {
    		responsable.setAsignado(false);
    		responsable = nuevoEmpleado;
    		responsable.setAsignado(true);
    	}
    	else {
    		responsable = nuevoEmpleado;
    		responsable.setAsignado(true);
    	}
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
    
    public boolean tieneEmpleadoAsignado() {
    	return responsable != null;
    }

    public void setRetraso(double cantidadDias) {
        this.diasRetraso = cantidadDias;
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
        // TODO: Implementar
        // IMPORTANTE: Solo retornar el título según enunciado parte 3
        // return titulo;
    }
    
    /**
     * Método opcional para debugging con información completa
     */
    
    private boolean verificarIREP() {
    	return (titulo != null && !titulo.isEmpty() &&
    			descripcion != null && !descripcion.isEmpty() &&
    			cantDias >= 0 &&
    			diasRetraso >= 0 &&
    			(responsable == null || !responsable.getAsignado()) &&
    			(!terminado || responsable != null));
    }
}