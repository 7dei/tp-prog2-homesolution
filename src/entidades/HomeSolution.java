package entidades;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
		if (valor<0) {throw new IllegalArgumentException("Ingrese un valor que no sea negativo.");} 
		Empleado nuevo = new EmpleadoContratado(nombre, contadorLegajo, valor);
		empleados.put(contadorLegajo, nuevo);
		
	}

	@Override
	public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
		contadorLegajo++;
		if (valor<0) {throw new IllegalArgumentException("Ingrese un valor que no sea negativo.");} 
		if ((categoria==null)||(!categoria.equals(Categoria.tecnico)
		&& !categoria.equals(Categoria.experto) 
		&& !categoria.equals(Categoria.inicial)))
		{ throw new IllegalArgumentException("La categoria "+ categoria + "no es valida.");}
		
		Empleado nuevo = new EmpleadoPlanta(nombre, contadorLegajo, valor, categoria);
		empleados.put(contadorLegajo, nuevo);
	}

	@Override
	public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias, String domicilio,
	        String[] cliente, String inicio, String fin) throws IllegalArgumentException {
		if (titulos==null || descripcion==null) { throw new IllegalArgumentException("Ingrese un titulo o descripcion.");}
		if (domicilio==null || cliente==null) { throw new IllegalArgumentException("Ingrese un domicilio o nombre de cliente.");}
		if (inicio==null || fin==null) { throw new IllegalArgumentException("Ingrese una fecha de inicio/fin correcta.");}
		if (dias==null) { throw new IllegalArgumentException("Ingrese al menos un dia.");}
	    try {
	        LocalDate fechaInicio = LocalDate.parse(inicio);
	        LocalDate fechaFinEstimada = LocalDate.parse(fin);
	        
	        if (fechaFinEstimada.isBefore(fechaInicio)) {
	            throw new IllegalArgumentException("La fecha de fin estimada no puede ser anterior a la fecha de inicio.");
	        }
	        contadorProyecto++; // generar id unico
	        Proyecto nuevo = new Proyecto(contadorProyecto, titulos, descripcion, dias, domicilio, cliente, inicio, fin);
	        proyectos.put(contadorProyecto, nuevo);

	    } catch (DateTimeParseException e) {
	        throw new IllegalArgumentException("Formato de fecha invalido. Use AAAA/MM/DD.");
	    }
	}
	
	@Override
	public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		Empleado responsable = null;
		
		if (p == null) throw new Exception("Proyecto no encontrado.");
		if (p.getEstado().equals(Estado.finalizado)) {
		    throw new Exception("No se pueden asignar empleados a un proyecto finalizado");
		}
		
		for (Empleado e : empleados.values()) {
			if (!e.getAsignado()) {
				responsable = e;
				break;
			}
		}
		
		if (responsable == null) {
			p.setEstado(Estado.pendiente);
			throw new Exception("No hay empleados disponibles para asignar.");
		}

		Tarea t = p.getTareaPorTitulo(titulo);

		t.setResponsable(responsable);
		responsable.setAsignado(true);
		responsable.agregarTareaAsignada(t);
		p.agregarEmpleadoAlHistorial(responsable);
		//hago el proyecto de pendiente a activo.
		if (p.getEstado().equals(Estado.pendiente)) {
	        p.setEstado(Estado.activo);
	    }
	}

	@Override
	public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		if (p == null) throw new Exception("Proyecto no encontrado.");
		
		if (p.getEstado().equals(Estado.finalizado)) {
		    throw new Exception("No se pueden asignar empleados a un proyecto finalizado");
		}
		
		Empleado menosRetraso = null;
		
		for (Empleado e : empleados.values()) {
			if (!e.getAsignado()) {
				if (menosRetraso == null || e.getTareasConRetraso() < menosRetraso.getTareasConRetraso()) {
					menosRetraso = e;
				}
			}
		}
		
		if (menosRetraso == null) {
			p.setEstado(Estado.pendiente);
			throw new Exception("No hay empleados disponibles para asignar.");
		}

		Tarea t = p.getTareaPorTitulo(titulo); 
		
		t.setResponsable(menosRetraso);
		menosRetraso.setAsignado(true);
		menosRetraso.agregarTareaAsignada(t);
		p.agregarEmpleadoAlHistorial(menosRetraso);
		
		if (p.getEstado().equals(Estado.pendiente)) {
	        p.setEstado(Estado.activo);
	    }
	}

	@Override
	public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias)
	        throws IllegalArgumentException {
	    Proyecto p = proyectos.get(numero);
	    if (p == null) throw new IllegalArgumentException("Proyecto no encontrado.");
	        
	    try {
	        Tarea t = p.getTareaPorTitulo(titulo);
	        t.resgistrarRetraso((int)cantidadDias);
	            
	    } catch (Exception e) {
	        throw new IllegalArgumentException(e.getMessage());
	    }
	}

		
	@Override
	public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias)
	        throws IllegalArgumentException {
	    Proyecto p = proyectos.get(numero);
	    if (p == null) throw new IllegalArgumentException("Proyecto no encontrado"); // ✅
	    
	    if (p.getEstado().equals(Estado.finalizado)) { // ✅ VALIDAR
	        throw new IllegalArgumentException("No se pueden agregar tareas a un proyecto finalizado");
	    }
	    
	    if (p.obtenerListaTareas().containsKey(titulo)) { 
	        throw new IllegalArgumentException("Ya existe una tarea con ese nombre");
	    }
	    
	    Tarea nueva = new Tarea(titulo, descripcion, dias);
	    p.setTareas(titulo, nueva);
	    
	    p.setFechaEstimadaFin(p.getFechaEstimadaFin().plusDays((long)dias));
	    p.setFechaRealFin(p.getFechaRealFin().plusDays((long)dias));
	}

	@Override
	public void finalizarTarea(Integer numero, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		if (p == null) throw new Exception("Proyecto no encontrado.");
		
		// acceso O(1) a la tarea
		Tarea t = p.getTareaPorTitulo(titulo);
		
		if (!t.getTerminado()) {
			t.setTerminado(true);
			
			Empleado responsable = t.getResponsable();
			if (responsable != null) {
				responsable.setAsignado(false);
			}
		}
	}

	@Override
	public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
	    Proyecto p = proyectos.get(numero); 
	    
	    if (p == null) { 
	        throw new IllegalArgumentException("Error: Proyecto con ID " + numero + " no encontrado."); 
	    }	    
	    LocalDate fechaFin;	    
	    try {
	        fechaFin = LocalDate.parse(fin); 
	    } catch (DateTimeParseException e) {
	        throw new IllegalArgumentException("Error de fecha: El formato es año/mes/dia, o el mes/dia/año ingresado es invalido.");
	    }
	    
	    if (fechaFin.isBefore(p.getFechaInicio())) {
	        throw new IllegalArgumentException("Error: La fecha de finalización no puede ser anterior a la de inicio."); 
	    }
	    
	    if (fechaFin.isBefore(p.getFechaEstimadaFin())) {
	        throw new IllegalArgumentException("Error: La fecha de finalización real no puede ser anterior a la fecha estimada de finalización.");
	    }
	    
	    p.finalizarProyecto(fin);
	}

	@Override
	public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		if (p == null) throw new Exception("Proyecto no encontrado.");
		
		Empleado nuevoResponsable = empleados.get(legajo);
		if (nuevoResponsable == null) throw new Exception("Empleado no encontrado.");
		
		// acceso O(1) a la tarea
		Tarea t = p.getTareaPorTitulo(titulo);
		
		Empleado viejoResponsable = t.getResponsable();
		
		if (viejoResponsable != null) {
			viejoResponsable.setAsignado(false); // libera al viejo
		}
		
		if (nuevoResponsable.getAsignado()) {
			throw new Exception("El nuevo empleado ya está asignado a otra tarea.");
		}
		
		t.setResponsable(nuevoResponsable);
		nuevoResponsable.setAsignado(true);
		nuevoResponsable.agregarTareaAsignada(t);
		p.agregarEmpleadoAlHistorial(nuevoResponsable);
	}


	@Override
	public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
	    Proyecto p = proyectos.get(numero);
	    if (p == null) throw new Exception("Proyecto no encontrado.");
	    if (p.getEstado().equals(Estado.finalizado)) {
	        throw new Exception("No se pueden reasignar empleados en un proyecto finalizado");
	    }
	    
	    Empleado menosRetraso = null;
	    for (Empleado e : empleados.values()) {
	        if (!e.getAsignado()) { 
	            if (menosRetraso == null || e.getTareasConRetraso() < menosRetraso.getTareasConRetraso()) {
	                menosRetraso = e;
	            }
	        }
	    }
	    
	    if (menosRetraso == null) {
	        throw new Exception("No hay empleados disponibles para reasignar.");
	    }
	    
	    // 2. Acceso O(1) a la tarea
	    Tarea t = p.getTareaPorTitulo(titulo);
	    Empleado viejoResponsable = t.getResponsable();
	    
	    if (viejoResponsable != null) {
	        viejoResponsable.setAsignado(false);
	    }
	    
	    t.setResponsable(menosRetraso);
	    menosRetraso.setAsignado(true); // Ocupar al nuevo (O(1))
	    menosRetraso.agregarTareaAsignada(t); // AGREGAR (O(1))
	    p.agregarEmpleadoAlHistorial(menosRetraso);
	}

	@Override
	public double costoProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero); // <-- O(1)
		if (p==null) return 0;
		
		return p.calcularCostoFinal();
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
		ArrayList<Empleado>lista = new ArrayList<>();
	
		for (Empleado e : empleados.values()) {
			if (!e.getAsignado()) {
				lista.add(e);
			}
		}
		return lista.toArray();
	}

	@Override
	public boolean estaFinalizado(Integer numero) {
		Proyecto p = proyectos.get(numero);
		return p != null && p.getEstado().equals(Estado.finalizado); 
	}

	@Override
	public int consultarCantidadRetrasosEmpleado(Integer legajo) {
		Empleado e = empleados.get(legajo);
		return e.getTareasConRetraso();
	}


	@Override
	public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero);
	    List<Tupla<Integer, String>> nueva = new ArrayList<>();

	    for (Empleado e : p.getHistorialEmpleados()) {
	        nueva.add(new Tupla<>(e.getLegajo(), e.getNombre()));
	    }
	    return nueva;
	}
	
	@Override
	public Object[] tareasProyectoNoAsignadas(Integer numero) {
	    Proyecto p = proyectos.get(numero);
	    
	    if (p == null) {
	        return new Object[0];
	    }
	    
	    if (p.getEstado().equals(Estado.finalizado)) {
	        throw new IllegalArgumentException("No se pueden consultar tareas no asignadas de un proyecto finalizado.");
	    }
	    
	    ArrayList<String> tareasNoAsignadas = new ArrayList<>();
	    
	    for (Tarea t : p.obtenerListaTareas().values()) {
	        if (!t.tieneResponsable()) {
	            tareasNoAsignadas.add(t.getTitulo());
	        }
	    }
	    return tareasNoAsignadas.toArray();
	}
	
	@Override
	public Object[] tareasDeUnProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero);
		if (p==null) return new Object[0];
		
		ArrayList<String> titulos = new ArrayList<>();
		
		for (Tarea t : p.obtenerListaTareas().values()) {
			titulos.add(t.getTitulo());
		}
		return titulos.toArray();
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
		if (e.getTareasConRetraso() > 0) {
			return true;
		}else { return false;}
	}

	@Override
	public List<Tupla<Integer, String>> empleados() {
		List<Tupla<Integer,String>>lista = new ArrayList<>();
		for (Empleado e : empleados.values()) {
			lista.add(new Tupla<>(e.getLegajo(), e.getNombre()));
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
