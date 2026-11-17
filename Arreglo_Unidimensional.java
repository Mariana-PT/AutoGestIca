package Estructuras;

public class Arreglo_Unidimensional {

    private String[] repuestos;
    private int contador;

    public Arreglo_Unidimensional(int capacidad) {
        repuestos = new String[capacidad];
        contador = 0;
    }

    public boolean insertar(String nombreRepuesto) {
        if (contador < repuestos.length) {
            repuestos[contador] = nombreRepuesto;
            contador++;
            return true;
        }
        return false;
    }

    public int buscar(String nombre) {
        for (int i = 0; i < contador; i++) {
            if (repuestos[i].equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public String mostrar() {
        StringBuilder sb = new StringBuilder("Catálogo de Repuestos\n");
        for (int i = 0; i < contador; i++) {
            sb.append("[").append(i).append("] ").append(repuestos[i]).append("\n");
        }
        return sb.toString();
    }

}
