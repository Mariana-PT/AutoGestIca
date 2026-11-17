
package Estructuras;


public class ColaPrioridadAtencion {
    public static class NodoColaPrioridad{
        String placaVehiculo;
        String servicio;
        int prioridad;
        NodoColaPrioridad siguiente;

        public NodoColaPrioridad(String placaVehiculo, String servicio, int prioridad) {
            this.placaVehiculo = placaVehiculo;
            this.servicio = servicio;
            this.prioridad = prioridad;
            this.siguiente = null;
        } 
    }
    
    private NodoColaPrioridad frente;
    
    public ColaPrioridadAtencion(){
        this.frente = null;
    }
    
    public void encolar(String placa, String servicio, int prioridad) {
        NodoColaPrioridad nuevo = new NodoColaPrioridad(placa, servicio, prioridad);
        
        if (frente == null || frente.prioridad > prioridad) {
            nuevo.siguiente = frente;
            frente = nuevo;
        } else {
            NodoColaPrioridad actual = frente;
            while (actual.siguiente != null && actual.siguiente.prioridad <= prioridad) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
    }
    
    public String desencolar() {
        if (estaVacia()) {
            return null;
        }
        String atendido = "Vehiculo: " + frente.placaVehiculo + " | Servicio: " + frente.servicio + " | Prioridad: " + frente.prioridad;
        frente = frente.siguiente;
        return atendido;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public String mostrarCola() {
        if (estaVacia())
        return "Cola de atencion vacia.\n";
        StringBuilder sb = new StringBuilder();
        NodoColaPrioridad actual = frente;
        int pos = 1;
        while (actual != null) {
            sb.append(pos++).append(". Placa: ").append(actual.placaVehiculo)
              .append(" | Servicio: ").append(actual.servicio)
              .append(" | Prioridad: ").append(actual.prioridad == 1 ? "Alta" : "Normal").append("\n");
            actual = actual.siguiente;
        }
        return sb.toString();
    }
    
}
