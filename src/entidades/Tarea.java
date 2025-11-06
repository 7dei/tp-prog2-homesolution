package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tarea {

	private String titulo;
	private String descripcion;
	private double cantdias;
	private double diasRetraso;
	private boolean terminado;
	private int dias;
	private Empleado responsable;
	private double duracionTotal;
	private Map<String, Tarea> tareas;
	
	public Tarea (String titulo, String descripcion, double cantDias){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantdias = cantdias;
        this.diasRetraso = 0;
        this.dias = 0;
        this.terminado = false;
        this.responsable = null;
        this.duracionTotal = cantdias;
}

// ============================================================
// AGREGAR UNA NUEVA TAREA AL HASHMAP
// ============================================================
public void agregarTarea(String id, String titulo, String descripcion, double cantdias) throws IllegalArgumentException {
	if (tareas.containsKey(id)) throw new IllegalArgumentException ("Ya existe una tarea con ese ID");
	Tarea nueva= new Tarea(titulo, descripcion, cantdias);
	tareas.put(id, nueva);
}

//============================================================
//MARCA SI LA TAREA FINALIZO
//============================================================

public void finalizacion(String id, int dias, boolean terminado) {
	Tarea tarea= tareas.get(id);
	if (tarea == null) throw new IllegalArgumentException ("no existe ninguna tarea con ese ID");
	tarea.dias= dias;
	tarea.terminado= terminado;
	tarea.duracionTotal= tarea.cantdias + tarea.diasRetraso;
}

//============================================================
//ASIGNA RESPONSABLE A LA TAREA
//============================================================

public void responsable(Empleado e) {
	if (e == null) throw new IllegalArgumentException ("Empleado invalido");
	if (!e.disponible) throw new IllegalArgumentException ("Empleado no disponible");
	this.responsable= e;
	e.disponible= false;
}

//============================================================
//BUSCA EMPLEADO EFICIENTE
//============================================================

public Empleado buscarEmpleadoEficiente() {
    if (responsable == null) {
    	return null;
    }
    return responsable;
}

//============================================================
//RESGISTRA DIAS DE RETRASOS DE LA TAREA
//============================================================

public void resgistrarRetraso(int dias) {
	if (dias < 0) throw new IllegalArgumentException ("Cantidad de dias de retraso invalidos");
	this.diasRetraso= diasRetraso + dias;
	this.duracionTotal = diasRetraso + cantdias;
	if (responsable != null) {
		responsable.registrarRetraso();
	}
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

public void setRetraso(double cantidadDias) {
	// TODO Auto-generated method stub
	
}

}