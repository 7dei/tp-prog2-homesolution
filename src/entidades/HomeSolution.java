package entidades;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class HomeSolution implements IHomeSolution {

    private HashMap<Integer, Proyecto> proyectos;
    private HashMap<Integer, Empleado> empleados;
    private int contadorProyecto;
    private int contadorLegajo;

    // ═══════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════
    public HomeSolution() {
        this.proyectos = new HashMap<>();
        this.empleados = new HashMap<>();
        this.contadorProyecto = 1;
        this.contadorLegajo = 1;
    }

    // ═══════════════════════════════════════════════════════════
    // REGISTRO DE EMPLEADOS (SOBRECARGA)
    // ═══════════════════════════════════════════════════════════
    @Override
    public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
        // Validaciones
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("Valor negativo");
        }

        // Crear EmpleadoContratado
        EmpleadoContratado emp = new EmpleadoContratado(contadorLegajo, nombre, valor);
        empleados.put(contadorLegajo, emp);
        contadorLegajo++;
    }

    @Override
    public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
        // Validaciones
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("Valor negativo");
        }
        if (categoria == null || categoria.isEmpty()) {
            throw new IllegalArgumentException("Categoría inválida");
        }

        // Crear EmpleadoPlanta
        EmpleadoPlanta emp = new EmpleadoPlanta(contadorLegajo, nombre, valor, categoria);
        empleados.put(contadorLegajo, emp);
        contadorLegajo++;
    }

    // ═══════════════════════════════════════════════════════════
    // REGISTRO DE PROYECTOS
    // ═══════════════════════════════════════════════════════════
    @Override
    public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias,
                                  String domicilio, String[] cliente, String inicio, String fin)
            throws IllegalArgumentException {
        // Validaciones básicas
        if (titulos == null || descripcion == null || dias == null) {
            throw new IllegalArgumentException("Arrays no pueden ser null");
        }
        if (titulos.length != descripcion.length || titulos.length != dias.length) {
            throw new IllegalArgumentException("Arrays con longitudes diferentes");
        }

        // Crear proyecto
        Proyecto proyecto = new Proyecto(contadorProyecto, cliente[0], cliente[1], 
                                        cliente[2], domicilio, inicio, fin);

        // Agregar tareas al proyecto
        for (int i = 0; i < titulos.length; i++) {
            Tarea tarea = new Tarea(titulos[i], descripcion[i], dias[i]);
            proyecto.agregarTarea(tarea);
        }

        // Guardar proyecto
        proyectos.put(contadorProyecto, proyecto);
        contadorProyecto++;
    }

    // ═══════════════════════════════════════════════════════════
    // CONSULTAS DE EMPLEADOS
    // ═══════════════════════════════════════════════════════════
    @Override
    public List<Tupla<Integer, String>> empleados() {
        List<Tupla<Integer, String>> lista = new ArrayList<>();
        for (Empleado emp : empleados.values()) {  // FOREACH
            lista.add(new Tupla<>(emp.getLegajo(), emp.getNombre()));
        }
        return lista;
    }

    @Override
    public boolean tieneRestrasos(Integer legajo) {  // ⚠️ Typo en interfaz
        Empleado emp = empleados.get(legajo);
        if (emp == null) return false;
        return emp.getTareasConRetraso() > 0;
    }

    @Override
    public Object[] empleadosNoAsignados() {
        List<Integer> noAsignados = new ArrayList<>();
        for (Empleado emp : empleados.values()) {  // FOREACH
            if (emp.isDisponible()) {
                noAsignados.add(emp.getLegajo());
            }
        }
        return noAsignados.toArray();
    }

    @Override
    public int consultarCantidadRetrasosEmpleado(Integer legajo) {
        Empleado emp = empleados.get(legajo);
        if (emp == null) return 0;
        return emp.getTareasConRetraso();
    }

    // ═══════════════════════════════════════════════════════════
    // CONSULTAS DE PROYECTOS
    // ═══════════════════════════════════════════════════════════
    @Override
    public double costoProyecto(Integer numero) {
        Proyecto p = proyectos.get(numero);
        if (p == null) return 0;
        return p.getCostoFinal();  // O(1)
    }

    @Override
    public boolean estaFinalizado(Integer numero) {
        Proyecto p = proyectos.get(numero);
        if (p == null) return false;
        return p.getEstado().equals(Estado.finalizado);
    }

    @Override
    public String consultarDomicilioProyecto(Integer numero) {
        Proyecto p = proyectos.get(numero);
        if (p == null) return "";
        return p.getDireccion();
    }

    @Override
    public String consultarProyecto(Integer numero) {
        Proyecto p = proyectos.get(numero);
        if (p == null) return "Proyecto no encontrado";
        return p.toString();  // Llama al toString() del proyecto
    }

    @Override
    public List<Tupla<Integer, String>> proyectosFinalizados() {
        List<Tupla<Integer, String>> lista = new ArrayList<>();
        for (Proyecto p : proyectos.values()) {  // FOREACH
            if (p.getEstado().equals(Estado.finalizado)) {
                lista.add(new Tupla<>(p.getNumeroID(), p.getDireccion()));
            }
        }
        return lista;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosActivos() {
        List<Tupla<Integer, String>> lista = new ArrayList<>();
        for (Proyecto p : proyectos.values()) {  // FOREACH
            if (p.getEstado().equals(Estado.activo)) {
                lista.add(new Tupla<>(p.getNumeroID(), p.getDireccion()));
            }
        }
        return lista;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosPendientes() {
        List<Tupla<Integer, String>> lista = new ArrayList<>();
        for (Proyecto p : proyectos.values()) {  // FOREACH
            if (p.getEstado().equals(Estado.pendiente)) {
                lista.add(new Tupla<>(p.getNumeroID(), p.getDireccion()));
            }
        }
        return lista;
    }

    // ═══════════════════════════════════════════════════════════
    // CONSULTAS DE TAREAS
    // ═══════════════════════════════════════════════════════════
    @Override
    public Object[] tareasDeUnProyecto(Integer numero) {
        Proyecto p = proyectos.get(numero);
        if (p == null) return new Object[0];
        
        List<String> titulos = new ArrayList<>();
        for (Tarea t : p.getListaTareas()) {  // FOREACH
            titulos.add(t.toString());  // toString() = solo título
        }
        return titulos.toArray();
    }

    @Override
    public Object[] tareasProyectoNoAsignadas(Integer numero) {
        Proyecto p = proyectos.get(numero);
        if (p == null) return new Object[0];
        
        List<String> noAsignadas = new ArrayList<>();
        for (Tarea t : p.getListaTareas()) {  // FOREACH
            if (!t.tieneResponsable()) {
                noAsignadas.add(t.toString());
            }
        }
        return noAsignadas.toArray();
    }

    @Override
    public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
        Proyecto p = proyectos.get(numero);
        if (p == null) return new ArrayList<>();
        
        List<Tupla<Integer, String>> lista = new ArrayList<>();
        for (Empleado emp : p.obtenerHistorialEmpleados()) {  // FOREACH
            lista.add(new Tupla<>(emp.getLegajo(), emp.getNombre()));
        }
        return lista;
    }

    // ═══════════════════════════════════════════════════════════
    // MÉTODOS RESTANTES (delegación al proyecto/tarea)
    // ═══════════════════════════════════════════════════════════
    @Override
    public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
        // Tu compañero implementa la lógica de asignación
        // Acá solo delegás al proyecto
    }

    @Override
    public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
        // Tu compañero implementa
    }

    @Override
    public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias) 
            throws IllegalArgumentException {
        // Tu compañero implementa
    }

    @Override
    public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias) 
            throws IllegalArgumentException {
        Proyecto p = proyectos.get(numero);
        if (p == null) {
            throw new IllegalArgumentException("Proyecto no existe");
        }
        Tarea nuevaTarea = new Tarea(titulo, descripcion, dias);
        p.agregarTarea(nuevaTarea);
    }

    @Override
    public void finalizarTarea(Integer numero, String titulo) throws Exception {
        // Tu compañero implementa
    }

    @Override
    public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
        Proyecto p = proyectos.get(numero);
        if (p == null) {
            throw new IllegalArgumentException("Proyecto no existe");
        }
        p.finalizarProyecto(fin);
    }

    @Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) 
            throws Exception {
        // Tu compañero implementa
    }

    @Override
    public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
        // Tu compañero implementa
    }
}