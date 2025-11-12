package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tarea {

	private String titulo;
	private String descripcion;
	private double cantDias;
	private double diasRetraso;
	private boolean terminado;
	private int dias;
	private Empleado responsable;
	private double duracionTotal;
	
	public Tarea (String titulo, String descripcion, double cantDias){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantDias = cantDias; // CORREGIR: estaba sin asignar
        this.diasRetraso = 0;
        this.dias = 0;
        this.terminado = false;
        this.responsable = null;
        this.duracionTotal = cantDias; // CORREGIR: usar el parámetro
    }

    // ============================================================
    // AGREGAR UNA NUEVA TAREA AL HASHMAP
    // ============================================================
    // SUGERENCIA: Este método debería estar en HomeSolution, no en Tarea
    public void agregarTarea(String id, String titulo, String descripcion, double cantdias) throws IllegalArgumentException {
        if (tareas.containsKey(id)) throw new IllegalArgumentException ("Ya existe una tarea con ese ID");
        Tarea nueva= new Tarea(titulo, descripcion, cantdias);
        tareas.put(id, nueva);
    }

    //============================================================
    //MARCA SI LA TAREA FINALIZO
    //============================================================
    // SUGERENCIA: Este método debería estar en HomeSolution, no en Tarea
    public void finalizacion(String id, int dias, boolean terminado) {
        Tarea tarea= tareas.get(id);
        if (tarea == null) throw new IllegalArgumentException ("no existe ninguna tarea con ese ID");
        tarea.dias= dias;
        tarea.terminado= terminado;
        tarea.duracionTotal= tarea.cantDias + tarea.diasRetraso;
    }
    //============================================================
    //ASIGNA RESPONSABLE A LA TAREA
    //============================================================
    // SUGERENCIA: Renombrar a asignarEmpleado() para mayor claridad
    public void responsable(Empleado e) {
        if (e == null) throw new IllegalArgumentException ("Empleado invalido");
        if (!e.getAsignado()) throw new IllegalArgumentException ("Empleado no disponible"); // Usar getter
        this.responsable= e;
        e.setAsignado(false); // Usar setter
        e.agregarTareaAsignada(this); // AGREGAR: registrar la tarea en el empleado
    }

    //============================================================
    //BUSCA EMPLEADO EFICIENTE
    //============================================================
    // SUGERENCIA: Este método debería estar en HomeSolution, no en Tarea
    // Una tarea solo conoce a SU responsable, no busca empleados
    public Empleado buscarEmpleadoEficiente() {
        if (responsable == null) {
            return null;
        }
        return responsable;
    }

    //============================================================
    //REGISTRA DIAS DE RETRASOS DE LA TAREA
    //============================================================
    public void registrarRetraso(int dias) { // CORREGIR: typo "resgistrar"
        if (dias < 0) throw new IllegalArgumentException ("Cantidad de dias de retraso invalidos");
        this.diasRetraso += dias; // Simplificar
        this.duracionTotal = diasRetraso + cantDias;
        if (responsable != null) {
            responsable.incrementarTareasConRetraso(); // CORREGIR: nombre del método
        }
    }

    // ============================================================
    // MÉTODOS QUE FALTAN
    // ============================================================
    
    /**
     * Finaliza la tarea y libera al empleado asignado
     * El empleado queda en el historial del proyecto
     */
    public void finalizarTarea() {
        // TODO: Implementar
        // 1. Marcar terminado = true
        // 2. Liberar al empleado: responsable.setDisponible(true)
    }
    
    /**
     * Reasigna la tarea a un nuevo empleado
     * Debe ser O(1) - el HashMap está en HomeSolution
     */
    public void reasignarEmpleado(Empleado nuevoEmpleado) {
        // TODO: Implementar
        // 1. Si hay responsable anterior, liberarlo
        // 2. Asignar el nuevo empleado usando responsable() o asignarEmpleado()
    }
    
    /**
     * Calcula el costo de esta tarea usando polimorfismo
     * El empleado sabe cómo calcularse según su tipo
     */
    public double calcularCosto() {
    	if (responsable==null) {return 0;}
    	else {return responsable.calcularCostoTarea(cantDias);}
    }
    
    /**
     * Retorna la duración total incluyendo retrasos
     */
    public double getDuracionTotal() {
    	return cantDias + diasRetraso;
    }
    
    /**
     * Verifica si la tarea tiene retrasos
     */
    public boolean tuvoRetrasos() {
    	return diasRetraso > 0;
    }
    
    /**
     * Verifica si tiene empleado asignado
     */
    public boolean tieneEmpleadoAsignado() {
        // TODO: Implementar
        // return responsable != null;
    }

    // ============================================================
    // GETTERS Y SETTERS EXISTENTES
    // ============================================================
    
    
    public void setRetraso(double cantidadDias) {
        this.diasRetraso = cantidadDias;
    }

    public boolean tieneResponsable() {
        return responsable != null;
    }

    public void setResponsable(Empleado e) {
        this.responsable = e;
    }

    public boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(boolean estado) {
        this.terminado = estado; 
    }

    public String getTitulo() {
        return titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public double getCantDias() {
        return cantDias;
    }
    
    public double getDiasRetraso() {
        return diasRetraso;
    }
    
    public Empleado getResponsable() {
        return responsable;
    }
    
    public int getDias() {
        return dias;
    }

    @Override
    public String toString() {
        // TODO: Implementar
        // IMPORTANTE: Solo retornar el título según enunciado parte 3
        // return titulo;
    }
    
    /**
     * Método opcional para debugging con información completa
     */
    
    private boolean verificarIREP() {
    	return (titulo != null && !titulo.isEmpty() &&
    			descripcion != null && !descripcion.isEmpty() &&
    			cantDias >= 0 &&
    			diasRetraso >= 0 &&
    			(responsable == null || !responsable.getAsignado()) &&
    			(!terminado || responsable != null));
    }
}