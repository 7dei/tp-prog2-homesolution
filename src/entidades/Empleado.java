package entidades;

import java.util.ArrayList;
import java.util.List;

public abstract class Empleado {
    // protected en este caso para que las clases que heredan puedan acceder.
    protected String nombre;
    protected int legajo;
    protected boolean asignado;  
    protected int tareasConRetraso;
    protected List<Tarea> tareasAsignadas;
    
    // constructor protegido para las clases hijas
    protected Empleado(String nombre, int legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
        this.asignado = false;
        this.tareasAsignadas = new ArrayList<>();
        this.tareasConRetraso = 0;
    }
    
    public abstract double calcularCostoTarea(double dias);
    
    public boolean tuvoRetrasos() {
        return tareasConRetraso > 0;
    }

    public void agregarTareaAsignada(Tarea tarea) {
    	tareasAsignadas.add(tarea);
    }

    public void registrarRetraso() {
        tareasConRetraso++;
    }

    public int getLegajo() {
        return legajo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public boolean getAsignado() {
        return asignado;
    }
    
    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }
    
    public int getTareasConRetraso() {
        return tareasConRetraso;
    }
    
    public List<Tarea> getTareasAsignadas() {
        return tareasAsignadas;
    }

    @Override
    public String toString() {
    	return String.valueOf(legajo);
    }
    
    protected boolean verificarIREP() {
        return (nombre != null && !nombre.isEmpty() && legajo > 0 && tareasAsignadas 
        != null &&tareasConRetraso >= 0);
    }
}