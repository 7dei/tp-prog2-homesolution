package entidades;

public class EmpleadoContratado extends Empleado {
    private double valorHora;
    
    public EmpleadoContratado(String nombre, int legajo, double valorHora) {
        super(nombre, legajo);
        this.valorHora = valorHora;
    }
   
// SE DEBERIA AGREGAR EN TAREA UN METODO QUE CALCULE SI LAS HORAS SON 8 == 1DIA.   
    
// IMPLEMENTAR
// AJUSTAR DIAS A 8 HORAS/4.
    public double calcularCostoTarea(double dias) {
    	// medio dia = 4 horas, si no cada dia tiene 8 horas.
    	double horas = (dias == 0.5) ? 4 : dias * 8;
    	return horas * valorHora;
    }
// FALTA EL RESTO DE METODOS, COMO SETTER Y GETTERS
    public double getValorHora() {
    	return valorHora;
    }
    
    public void setValorHora(double valorHora) {
    	this.valorHora = valorHora;
    }
    
    @Override
    public String toString() {
    	return String.valueOf(legajo);
    }

    @Override
    protected boolean verificarIREP() {
        return super.verificarIREP() && valorHora >= 0;
    }
}
