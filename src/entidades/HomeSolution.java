package entidades;

import java.time.LocalDate;
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
		Empleado nuevo = new EmpleadoContratado(nombre, contadorLegajo, valor);
		empleados.put(contadorLegajo, nuevo);
		
	}

	@Override
	public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
		contadorLegajo++;
		Empleado nuevo = new EmpleadoPlanta(nombre, contadorLegajo, valor, categoria);
		empleados.put(contadorLegajo, nuevo);
	}

	@Override
	public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias, String domicilio,
	        String[] cliente, String inicio, String fin) throws IllegalArgumentException {
		 
		contadorProyecto++; // generar id Unico
		Proyecto nuevo = new Proyecto(contadorProyecto, titulos, descripcion, dias, domicilio, cliente, inicio, fin);
		proyectos.put(contadorProyecto, nuevo);
	}
	
	@Override
	public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		if (p == null) throw new Exception("Proyecto no encontrado.");

		Empleado responsable = null;
		// Buscar el primer empleado disponible (sigue siendo O(N) de empleados)
		for (Empleado e : empleados.values()) {
			if (!e.getAsignado()) {
				responsable = e;
				break;
			}
		}
		
		if (responsable == null) {
			throw new Exception("No hay empleados disponibles para asignar.");
		}

		// Acceso O(1) a la tarea
		Tarea t = p.getTareaPorTitulo(titulo); // <-- O(1)

		// Lógica de asignación O(1)
		t.setResponsable(responsable);
		responsable.setAsignado(true); // <--- CORRECCIÓN: Marcarlo como ocupado
		responsable.agregarTareaAsignada(t); // <--- AGREGAR: Registrar tarea en empleado
	}

	@Override
	public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		if (p == null) throw new Exception("Proyecto no encontrado.");
		
		Empleado menosRetraso = null;
		
		// 1. Buscar al de menos retraso SÓLO entre los disponibles (O(N) global)
		for (Empleado e : empleados.values()) {
			if (!e.getAsignado()) { // <--- CORRECCIÓN LÓGICA: SÓLO DISPONIBLES
				if (menosRetraso == null || e.getTareasConRetraso() < menosRetraso.getTareasConRetraso()) {
					menosRetraso = e;
				}
			}
		}
		
		if (menosRetraso == null) {
			throw new Exception("No hay empleados disponibles para asignar.");
		}

		// 2. Acceso O(1) a la tarea y asignación
		Tarea t = p.getTareaPorTitulo(titulo); // <-- O(1)
		
		// 3. Asignación O(1)
		t.setResponsable(menosRetraso);
		menosRetraso.setAsignado(true);
		menosRetraso.agregarTareaAsignada(t); // AGREGAR
	}

	// --------------------------------------------------------------------------------
		// METODO CON ACCESO O(1)
		// --------------------------------------------------------------------------------
		@Override
	public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias)
			throws IllegalArgumentException {
		Proyecto p = proyectos.get(numero);
		if (p == null) throw new IllegalArgumentException("Proyecto no encontrado.");
			
		try {
			Tarea t = p.getTareaPorTitulo(titulo);
			t.setRetraso(cantidadDias);
			
			if (t.getResponsable() != null) {
					t.getResponsable().registrarRetraso();
			}
				
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
	    
	    // ✅ ACTUALIZAR FECHAS (según enunciado)
	    // La fecha estimada y real se extienden con los días de la nueva tareaf
	    p.setFechaEstimadaFin(p.getFechaEstimadaFin().plusDays((long)dias));
	    p.setFechaRealFin(p.getFechaRealFin().plusDays((long)dias));
	}

	@Override
	public void finalizarTarea(Integer numero, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		if (p == null) throw new Exception("Proyecto no encontrado.");
		
		// 1. Acceso O(1) a la tarea
		Tarea t = p.getTareaPorTitulo(titulo); // <-- O(1)
		
		// 2. Finalizar y liberar (O(1))
		if (!t.getTerminado()) {
			t.setTerminado(true);
			
			// CORRECCIÓN: Liberar al empleado asignado (O(1))
			Empleado responsable = t.getResponsable();
			if (responsable != null) {
				responsable.setAsignado(false); // <--- CORRECCIÓN: Liberar empleado
			}
		}
	}

	@Override
	public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException { 
		Proyecto p = proyectos.get(numero); 
		
		if (p==null) { throw new IllegalArgumentException("Proyecto no encontrado"); }
		LocalDate fechaFin = LocalDate.parse(fin);
		if (fechaFin.isBefore(p.getFechaInicio())) {
			throw new IllegalArgumentException("La fecha de finalizacion no puede ser menor a la de inicio."); }
		
		p.setEstado(Estado.finalizado);
		}
	

	@Override
	public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
		Proyecto p = proyectos.get(numero);
		if (p == null) throw new Exception("Proyecto no encontrado.");
		
		Empleado nuevoResponsable = empleados.get(legajo);
		if (nuevoResponsable == null) throw new Exception("Empleado no encontrado.");
		
		// 1. Acceso O(1) a la tarea
		Tarea t = p.getTareaPorTitulo(titulo); // <-- O(1)
		
		// 2. Reasignación (O(1))
		Empleado viejoResponsable = t.getResponsable();
		
		if (viejoResponsable != null) {
			viejoResponsable.setAsignado(false); // Liberar al viejo
		}
		
		if (nuevoResponsable.getAsignado()) {
			throw new Exception("El nuevo empleado ya está asignado a otra tarea.");
		}
		
		t.setResponsable(nuevoResponsable);
		nuevoResponsable.setAsignado(true); // Ocupar al nuevo
		nuevoResponsable.agregarTareaAsignada(t); // AGREGAR
	}


	@Override
	public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
	    Proyecto p = proyectos.get(numero);
	    if (p == null) throw new Exception("Proyecto no encontrado.");
	    
	    // 1. Encontrar el empleado con menos retraso (sigue siendo O(N) global de empleados)
	    Empleado menosRetraso = null;
	    for (Empleado e : empleados.values()) {
	        if (!e.getAsignado()) { // SÓLO DISPONIBLES
	            // Usar el getter (getTareasConRetraso())
	            if (menosRetraso == null || e.getTareasConRetraso() < menosRetraso.getTareasConRetraso()) {
	                menosRetraso = e;
	            }
	        }
	    }
	    
	    if (menosRetraso == null) {
	        throw new Exception("No hay empleados disponibles para reasignar.");
	    }
	    
	    // 2. Acceso O(1) a la tarea
	    Tarea t = p.getTareaPorTitulo(titulo); // <-- O(1)
	    
	    // 3. Reasignación (O(1))
	    Empleado viejoResponsable = t.getResponsable();
	    
	    if (viejoResponsable != null) {
	        viejoResponsable.setAsignado(false); // Liberar al viejo (O(1))
	    }
	    
	    t.setResponsable(menosRetraso);
	    menosRetraso.setAsignado(true); // Ocupar al nuevo (O(1))
	    menosRetraso.agregarTareaAsignada(t); // AGREGAR (O(1))
	}

	@Override
	public double costoProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero); // <-- O(1)
		return (p!=null) ? p.getCostoFinal() : 0;
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


	public void agregarTarea(Integer numero, String titulo, String descripcion, double cantdias) throws IllegalArgumentException {
        Proyecto p = proyectos.get(numero);
    	if (p.obtenerListaTareas().containsKey(titulo)
    	) throw new IllegalArgumentException ("Ya existe una tarea con ese nombre");
        Tarea nueva= new Tarea(titulo, descripcion, cantdias);
        p.setTareas(titulo, nueva);
    }
    
  

	@Override
	public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
		Proyecto p = proyectos.get(numero);
		List<Tupla<Integer, String>>nueva = new ArrayList<>();
		for (Empleado e : p.getHistorialEmpleados()) {
			if (e.getAsignado()) {
				nueva.add(new Tupla<>(e.getLegajo(), e.getNombre()));
			}
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
