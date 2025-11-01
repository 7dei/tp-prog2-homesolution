package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Proyecto{

	private HashMap<Integer, Proyecto> historialEmpleados;
	private HashMap<Integer, Empleado> listaTareas;
	private int numeroID;
	private String clienteNombre;
	private String clienteEmail;
	private int clienteNumero;
	private String direccion;
	private LocalDate fechaInicio;
	private LocalDate fechaEstimadaFin;
	private LocalDate fechaRealFin;
	private String estado;
	private double costoFinal;
	
	
	public Proyecto(int numeroID, String direccion) {
	    this.numeroID = numeroID;
	    this.clienteNombre = clienteNombre;
	    this.clienteEmail = clienteEmail;
	    this.clienteNumero = 0;  // String → int
	    this.direccion = direccion;
//	    this.fechaInicio = LocalDate.parse(inicio);
//	    this.fechaEstimadaFin = LocalDate.parse(fin);
//	    this.fechaRealFin = LocalDate.parse(fin);             
	    this.estado = Estado.pendiente;
	    this.costoFinal = 0;
//	    this.historialEmpleados = new ArrayList<>();
//	    this.listaTareas = new ArrayList<>(); LOS ARRAYLIST Y LOS LOCALDATE APARECEN MAL. PQ?
	}
	
	public void registrarProyecto(String[] titulos, String[] descripciones, double[] duracion, String string,
			String[] cliente, String string2, String string3) {
		
	}
	
	public void registrarEmpleado(String string, int i) {
		
	}	

	public void registrarEmpleado(String string, int i, String string2) {
	
	}

	public void obtenerHistorialEmpleados() {
		
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
	
	
	public String toString() {
		return "Proyecto #" + numeroID + " - " + direccion;
	}	


}