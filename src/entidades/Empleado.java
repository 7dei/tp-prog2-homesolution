package entidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Empleado {
	private String nombre;
	private int legajo;
	private double valorHora;
	boolean disponible;
	private double calcularCosto;
	private double horas;
	private double cantdias;
	private int tareasConRetraso;
	private int totalRetraso;
	private List<Tarea> tareasAsignadas;
	private HashMap<Integer, Empleado> empleados;
    private List<Empleado> noAsignados = new ArrayList<>();

	private boolean asignado;
    
	public Empleado(String nombre, int legajo, double valorHora, String categoria){
		this.nombre=nombre;
		this.legajo=legajo;
		this.valorHora = valorHora;
	    this.disponible = true;
	        this.tareasAsignadas = new ArrayList<>();
	        this.tareasConRetraso = 0;
	        this.totalRetraso = 0;
	        this.empleados= new HashMap<>();
	        this.noAsignados= new ArrayList<>();
	   this.asignado = false;
	}
	
	// ============================================================
    // REGISTRA LAS TAREAS CON RETRASOS
    // ============================================================
	
	public void registrarRetraso() {
		tareasConRetraso++;
	}
	
	// ============================================================
    // REGISTRO DE NUEVOS EMPLEADOS
    // ============================================================
	
	public void registrarEmpleado(String nombre,int legajo,double  valorHora) throws IllegalArgumentException {
		//validaciones
		
		if (valorHora <= 0) throw new IllegalArgumentException("valor hora invalido");
		
		if (nombre ==null || nombre.isEmpty()) throw new IllegalArgumentException("nombre invalido");
	
		//creamos el nuevo empleado
		Empleado nuevo= new Empleado(nombre, legajo, valorHora);
		
		empleados.put(legajo, nuevo);
	}
	
	// ============================================================
    // EMPLEADOS NO ASIGNADOS
    // ============================================================
	
	//Devuelve un lista arraylist con los empleados que no tienen tareas asignadas
	public Object[] empleadosNoAsignados() {
        for (Empleado e : empleados.values()) {
        	if (e.tareasAsignadas.isEmpty()) {
        		noAsignados.add(e);
        	}
        }
        return noAsignados.toArray();
	}
	
	// ============================================================
    // TUVO RETRASOS
    // ============================================================
    // Devuelve true si un empleado tuvo retrasos (tareasConRetraso > 0)
	public boolean tuvoRetrasos(Empleado e) {
		if (e.tareasConRetraso >= 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// ============================================================
    // BUSCAR EMPLEADO EFICIENTE
    // ============================================================
    // Retorna el empleado más eficiente
	
	public Empleado buscarEmpleadoEficiente(Empleado e) {
		if (empleados.isEmpty()) {
			return null;
		}
		Empleado mejor= null;
		for (Empleado empleado : empleados.values()) {
			if (mejor == null || empleado.tareasConRetraso < mejor.tareasConRetraso) {
				mejor = empleado;
			}
		}
		return mejor;
	}
	
	// ============================================================
    // CALCULAR COSTO
    // ============================================================
	//CALCULA LAS HORAS TRABAJADAS Y EL VALORHORA
	
	public double calcularCosto(Empleado e) {
		e.calcularCosto= e.horas * e.valorHora;
		return e.calcularCosto;
	}
	
	public int getRetraso() {
		return tareasConRetraso;
	}
	
	public int getCuantoRetraso() {
		return totalRetraso;
	}
	
	public boolean getAsignado() {
		return asignado;
	}
}
