package entidades;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoContratado extends Empleado {
    private double valorHora;
    
    public EmpleadoContratado(String nombre, int legajo, double valorHora) {
        super(nombre, legajo);
        this.valorHora = valorHora;
    }
   
// IMPLEMENTAR
    public double calcularCostoTarea(double dias) {
    	return 0;
// FALTA EL RESTO DE METODOS, COMO SETTER Y GETTERS
    }
}