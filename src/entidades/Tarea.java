package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tarea {

	private String titulo;
	private String descripcion;
	private double cantdias;
	private int diasRetraso;
	private boolean terminado;
	private int dias;
	private Empleado responsable;
	private double duracionTotal;
	private Map<String, Tarea> tareas;
}

