
package Estructuras;


public class PilaHistorialAcciones {
    private static class NodoPila{
        String accion;
        NodoPila siguiente;
        
        public NodoPila(String accion){
            this.accion = accion;
            this.siguiente = null;
        }
    }
    
    private NodoPila cima;
    public PilaHistorialAcciones(){
        this.cima = null;
    }
    
    public void push(String accion){
        NodoPila nuevo = new NodoPila(accion);
        nuevo.siguiente = cima;
        cima = nuevo;
    }
    
    public String pop(){
        if(estaVacia()){
            return null;
        }
        String accionRemovida = cima.accion;
        cima = cima.siguiente;
        return accionRemovida;
    }
    
    public String peek(){
        if(estaVacia()){
            return "Pila Vacia";
        }
        return cima.accion;
    }
    
    public boolean estaVacia(){
        return cima == null;
    }
    
    public String mostrarPila(){
        if(estaVacia())
            return "Historial de pila vacio. \n";
        StringBuilder sb = new StringBuilder();
        NodoPila actual = cima;
        while(actual != null){
            sb.append("[Accion]: ").append(actual.accion).append("\n");
            actual = actual.siguiente;
        }
        return sb.toString();
    }
}
