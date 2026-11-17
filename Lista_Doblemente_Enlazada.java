package Estructuras;

public class Lista_Doblemente_Enlazada {

    private static class NodoClienteDoble {

        String dni;
        String nombres;
        NodoClienteDoble siguiente;
        NodoClienteDoble anterior;

        public NodoClienteDoble(String dni, String nombres) {
            this.dni = dni;
            this.nombres = nombres;
            this.siguiente = null;
            this.anterior = null;
        }
    }

    private NodoClienteDoble cabeza;
    private NodoClienteDoble cola;

    public Lista_Doblemente_Enlazada() {
        this.cabeza = null;
        this.cola = null;
    }

    public void insertarInicio(String dni, String nombres) {
        NodoClienteDoble nuevo = new NodoClienteDoble(dni, nombres);
        if (cabeza == null) {
            cabeza = cola = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
            cabeza = nuevo;
        }
    }

    public String mostrarAdelante() {
        if (cabeza == null) {
            return "La lista de clientes esta vacia.\n";
        }
        StringBuilder sb = new StringBuilder();
        NodoClienteDoble actual = cabeza;
        while (actual != null) {
            sb.append("DNI: ").append(actual.dni).append(" - Cliente: ").append(actual.nombres).append("\n");
            actual = actual.siguiente;
        }
        return sb.toString();
    }
}
