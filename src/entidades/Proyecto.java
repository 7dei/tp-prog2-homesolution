package entidades;

import java.time.LocalDate;
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
		this.direccion = direccion;
		this.estado = Estado.pendiente;
		this.costoFinal = 0;
		
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

	public int getID() {
		return numeroID;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public String getDireccion(){
		return direccion;
	}
	
	public void setEstado(String estado) {
		 this.estado = estado;
	}
	
	public String toString() {
		return "Proyecto #" + numeroID + " - " + direccion;
	}	


}