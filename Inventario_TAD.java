package Estructuras;

public class Inventario_TAD {

    private String nombreItem;
    private int stockMinimo;
    private int stockActual;

    public Inventario_TAD(String nombreItem, int stockMinimo, int stockActual) {
        this.nombreItem = nombreItem;
        this.stockMinimo = stockMinimo;
        this.stockActual = stockActual;
    }

    public void actualizar_Stock(int cantidad) {
        this.stockActual += cantidad;
    }

    public boolean verificar_Abastecimiento() {
        return this.stockActual <= this.stockMinimo;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public int getStockActual() {
        return stockActual;
    }
}
