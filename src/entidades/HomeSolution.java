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

	@Override // sobrecarga
	public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
		contadorLegajo++;
		Empleado nuevo = new Empleado(nombre, contadorLegajo, valor);
		empleados.put(contadorLegajo, nuevo);
		
	}

	@Override
	public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
		contadorLegajo++;
		Empleado nuevo = new Empleado(nombre, contadorLegajo, valor, categoria);
		empleados.put(contadorLegajo, nuevo);
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
		
		
	}

	@Override
	public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias)
			throws IllegalArgumentException {
		Proyecto p = proyectos.get(numero);
		for (Tarea t : p.getTareas()) {
			if (t.getTitulo().equals(titulo)) {
				t.setRetraso(cantidadDias);
			}
		}
		
	}

	@Override
	public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias)
			throws IllegalArgumentException {
		Proyecto p = proyectos.get(numero);
		Tarea nueva = new Tarea(titulo, descripcion, dias);
		p.setTareas(nueva);
	}

	@Override
	public void finalizarTarea(Integer numero, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		ArrayList<Tarea> tareas = p.getTareas();
		for (Tarea t : tareas) {
			if (t.getTitulo().equals(titulo) && t.getTerminado()==false) {
				t.setTerminado(true);
			}
		}
		
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
	public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero);
		return null;
		//	return p.getHistorialEmpleados(); // como lo ahgo list tupla
	}

	@Override
	public Object[] tareasProyectoNoAsignadas(Integer numero) {
	    Proyecto p = proyectos.get(numero);
	    if (p == null) return new Object[0];  // Array vacío si no existe
	    
	    ArrayList<Tarea> tareas = p.obtenerListaTareas();
	    ArrayList<String> tareasNoAsignadas = new ArrayList<>();
	    
	    for (Tarea t : tareas) {
	        if (!t.tieneResponsable()) {
	            tareasNoAsignadas.add(t.getTitulo());
	        }
	    }
	    return tareasNoAsignadas.toArray();
	}
	@Override
	public Object[] tareasDeUnProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero);
		return p.obtenerListaTareas().toArray();
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
		List<Tupla<Integer,String>>lista = new ArrayList<>();
		for (Empleado e : empleados.values()) {
			lista.add(new Tupla<> (e.getLegajo(), e.getNombre()));
		}
		return lista;
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
