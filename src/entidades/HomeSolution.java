package entidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeSolution implements IHomeSolution{

	private HashMap<Integer,Proyecto> proyectos;
	private HashMap<Integer, Empleado> empleados;
	private int contadorProyecto; 
	private int contadorLegajo;
	
	public HomeSolution() { 
		this.proyectos = new HashMap<>();
		this.empleados = new HashMap<>();
		this.contadorProyecto = 0;
		this.contadorLegajo = 0;
	}

	@Override
	public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
//		return empleados.put(valor, "nombre");
		
	}

	@Override
	public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
		contadorLegajo++;
	}

	@Override
	public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias, String domicilio,
	        String[] cliente, String inicio, String fin) throws IllegalArgumentException {
		 
		contadorProyecto++; // generar id único
		Proyecto nuevo = new Proyecto(contadorProyecto, titulos, descripcion, dias, domicilio, cliente, inicio, fin);
		proyectos.put(contadorProyecto, nuevo);
	}
	
	@Override
	public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finalizarTarea(Integer numero, String titulo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
		
		for (Proyecto p : proyectos.values()) {
			if (p.getID()==numero) {
				p.setEstado(Estado.finalizado);
			}
		}
		
	}

	@Override
	public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		Empleado e = empleados.get(legajo);
		return ; // no se como seguir este, porque ademas mi compa no puso el proyecto del empleado, deberia haber hecho eso o no
		
	}

	@Override
	public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double costoProyecto(Integer numero) {

		Proyecto p = proyectos.get(numero);
		return (p!=null) ? p.getCostoFinal() : 0; //ESTE NO LO ENTENDI BIEN, SI PODES EXPLICAME PQ EL ? Y EL : 0
	}

	@Override
	public List<Tupla<Integer, String>> proyectosFinalizados() {
		
		List<Tupla<Integer, String>>lista = new ArrayList<>();
		
		for (Proyecto p: proyectos.values()) {
			if (p.getEstado().equals(Estado.finalizado)) {
				lista.add(new Tupla<>(p.getID(),p.getDireccion()));
			}
		}
		return lista;
	}

	@Override
	public List<Tupla<Integer, String>> proyectosPendientes() {
		
		List<Tupla<Integer,String>>lista = new ArrayList<>();
		
		for (Proyecto p : proyectos.values()) {
			if (p.getEstado().equals(Estado.pendiente)) {
				lista.add(new Tupla<>(p.getID(),p.getDireccion()));
			}
		}
		return lista;
	}

	@Override
	public List<Tupla<Integer, String>> proyectosActivos() {
		
		List<Tupla<Integer,String>>lista = new ArrayList<>();
		
		for (Proyecto p : proyectos.values()) {
			if (p.getEstado().equals(Estado.activo)) {
				lista.add(new Tupla<>(p.getID(),p.getDireccion()));
			}
		}
		return lista;
	}
	
	@Override
	public Object[] empleadosNoAsignados() {
//		for (Empleado e : empleados) {
//			for (Empleado noAsignado : e.empleadosNoAsignados()) (esto no deberia ser un array o no, no tiene sentido,)
//			deberia ser un valor unico y yo acceder al estado de ese empleado?).
//			}
//		}
		return null;
	}

	@Override
	public boolean estaFinalizado(Integer numero) {
		Proyecto p = proyectos.get(numero);
		return p != null && p.getEstado().equals(Estado.finalizado); 
	}

	@Override
	public int consultarCantidadRetrasosEmpleado(Integer legajo) {
		Empleado e = empleados.get(legajo);
		return e.getCuantoRetraso();
	}

	@Override
	public ArrayList<Empleado> empleadosAsignadosAProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero);
		return p.getHistorialEmpleados();
	}

	@Override
	public Object[] tareasProyectoNoAsignadas(Integer numero) {
		Proyecto p = proyectos.get(numero);
		ArrayList<Tarea> tareas = new ArrayList<>();
		tareas = p.obtenerListaTareas();
		
		ArrayList<Tarea> tareasNoAsignadas =  new ArrayList<>();
		for (Tarea t : tareas) {
			if (t.getTerminado()==false){   //aca no se como hacer para saber si no estan asignadas, pq no hay ningun valor
				tareasNoAsignadas.add(t);
			}
		}
		return tareasNoAsignadas; //pide object, q significa?
	}

	@Override
	public Object[] tareasDeUnProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero);
		return p.obtenerListaTareas();
	}

	@Override
	public String consultarDomicilioProyecto(Integer numero) {
		for (Proyecto p : proyectos.values()) {
			if (p.getID() == numero) {
				return p.getDireccion();
			}
		}
		return null;
	}

	@Override
	public boolean tieneRestrasos(Integer legajo) {
		Empleado e = empleados.get(legajo);
		if (e.getRetraso() > 0) {
			return true;
		}else { return false;}
	}

	@Override
	public List<Tupla<Integer, String>> empleados() {
		return null;
	}

	@Override
	public String consultarProyecto(Integer numero) {
		for (Proyecto p : proyectos.values()) {
			if (p.getID() == numero) {
				return p.toString();
			}
		}		
		return null;
	}
	
}
