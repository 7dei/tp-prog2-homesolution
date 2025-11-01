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
    private ArrayList<Tarea> listaTareas;
    private ArrayList<Empleado> historialEmpleados;
    private String estado;

    // Constructor que usa los arrays de titulos/descripcion/duracion
    public Proyecto(int numeroID, String[] titulos, String[] descripcion, double[] duracion,
                    String direccion, String[] cliente, String inicio, String fin) {
        this.numeroID = numeroID;
        this.direccion = direccion;
        this.clienteNombre = cliente[0] != null ? cliente[0] : "";
        this.clienteEmail = cliente[1] != null ? cliente[1] : "";
        this.clienteNumero = Integer.parseInt(cliente[2]);
        this.fechaInicio = LocalDate.parse(inicio);
        this.fechaEstimadaFin = LocalDate.parse(fin);
        this.fechaRealFin = this.fechaEstimadaFin;
        this.estado = Estado.pendiente;
        this.costoFinal = 0;
        this.listaTareas = new ArrayList<>();
        this.historialEmpleados = new ArrayList<>();
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