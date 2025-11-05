package entidades;

import java.util.ArrayList;
import java.util.List;

public class empleadoContratado {
	private String nombre;
	private int legajo;
	private double valorHora;
	private boolean disponible;
	private double horas;
	private List<Tarea> tareasAsignadas;
	
	public empleadoContratado(String nombre, int legajo,double valorHora) {
		this.nombre=nombre;
		this.legajo=legajo;
		this.valorHora=valorHora;
		this.disponible=true;
		this.horas=horas;
		this.tareasAsignadas= new ArrayList<>();
	}
}
