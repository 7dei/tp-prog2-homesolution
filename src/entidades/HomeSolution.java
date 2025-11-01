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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double costoProyecto(Integer numero) {
		
		for (Proyecto p : proyectos.values()) {
			if (p.getID()==numero) {
				return p.getCostoFinal();
			}
		}
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean estaFinalizado(Integer numero) {
		for (Proyecto p : proyectos.values()) {
			if ((p.getID() == numero) && (p.getEstado().equals(Estado.finalizado))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int consultarCantidadRetrasosEmpleado(Integer legajo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] tareasProyectoNoAsignadas(Integer numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] tareasDeUnProyecto(Integer numero) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Tupla<Integer, String>> empleados() {
		// TODO Auto-generated method stub
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
