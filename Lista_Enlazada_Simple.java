package Estructuras;

public class Lista_Enlazada_Simple {

    private static class NodoOrden {

        int codigoOrden;
        String placaVehiculo;
        NodoOrden siguiente;

        public NodoOrden(int codigoOrden, String placaVehiculo) {
            this.codigoOrden = codigoOrden;
            this.placaVehiculo = placaVehiculo;
            this.siguiente = null;
        }
    }

    private NodoOrden cabeza;

    public Lista_Enlazada_Simple() {
        this.cabeza = null;
    }

    public void insertar(int codigo, String placa) {
        NodoOrden nuevo = new NodoOrden(codigo, placa);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoOrden actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    public boolean eliminar(int codigo) {
        if (cabeza == null) {
            return false;
        }
        if (cabeza.codigoOrden == codigo) {
            cabeza = cabeza.siguiente;
            return true;
        }
        NodoOrden actual = cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.codigoOrden == codigo) {
                actual.siguiente = actual.siguiente.siguiente;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean buscar(int codigo) {
        NodoOrden actual = cabeza;
        while (actual != null) {
            if (actual.codigoOrden == codigo) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void ordenarPorCodigo() {
        if (cabeza == null || cabeza.siguiente == null) {
            return;
        }
        boolean cambiado;
        do {
            cambiado = false;
            NodoOrden actual = cabeza;
            while (actual.siguiente != null) {
                if (actual.codigoOrden > actual.siguiente.codigoOrden) {
                    int auxCod = actual.codigoOrden;
                    String auxPlaca = actual.placaVehiculo;

                    actual.codigoOrden = actual.siguiente.codigoOrden;
                    actual.placaVehiculo = actual.siguiente.placaVehiculo;
                    actual.siguiente.codigoOrden = auxCod;
                    actual.siguiente.placaVehiculo = auxPlaca;
                    cambiado = true;
                }
                actual = actual.siguiente;
            }
        } while (cambiado);
    }

    public String mostrar() {
        if (cabeza == null) {
            return "La lista de ordenes esta vacia.\n";
        }
        StringBuilder sb = new StringBuilder();
        NodoOrden actual = cabeza;
        while (actual != null) {
            sb.append("Orden ID: ").append(actual.codigoOrden)
              .append(" | Placa: ").append(actual.placaVehiculo).append("\n");
            actual = actual.siguiente;
        }
        return sb.toString();
    }
}
