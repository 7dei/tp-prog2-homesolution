package entidades;

public class EmpleadoPlanta extends Empleado {
    private double valorDia;
    private String categoria;

    public EmpleadoPlanta(String nombre, int legajo, double valorDia, String categoria) {
        super(nombre, legajo);
        this.valorDia = valorDia;
        this.categoria = categoria;
    }

    @Override
    public double calcularCostoTarea(double dias) {
        double diasACalcular = (dias == 0.5) ? 1.0 : dias;
        double costoBase = diasACalcular * valorDia;

        if (tareasConRetraso > 0) {
            return costoBase;
        } else {
            return costoBase * 1.02;
        }
    }
    
    

    public double getValorDia() {
        return valorDia;
    }
    
    public void setValorDia(double valorDia) {
        this.valorDia = valorDia;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
    	return String.valueOf(legajo);
    }
    
    @Override
    protected boolean verificarIREP() {
        return super.verificarIREP() && 
               valorDia >= 0 &&
               categoria != null &&
               (categoria.equals("INICIAL") || 
                categoria.equals("TÉCNICO") || 
                categoria.equals("EXPERTO"));
    }
}