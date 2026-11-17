
package Estructuras;


public class ArbolBinarioServicios {
    public static class NodoArbol{
        int codigoFalla;
        String descripcionFalla;
        NodoArbol izquierdo, derecho;

        public NodoArbol(int codigoFalla, String descripcionFalla) {
            this.codigoFalla = codigoFalla;
            this.descripcionFalla = descripcionFalla;
            this.izquierdo = null;
            this.derecho = null;
        }
    }
    
    private NodoArbol raiz;
    public ArbolBinarioServicios() {
        this.raiz = null;
    }

    public void insertar(int codigo, String descripcion) {
        raiz = insertarRecursivo(raiz, codigo, descripcion);
    }

    private NodoArbol insertarRecursivo(NodoArbol actual, int codigo, String descripcion) {
        if (actual == null) {
            return new NodoArbol(codigo, descripcion);
        }
        if (codigo < actual.codigoFalla) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, codigo, descripcion);
        } else if (codigo > actual.codigoFalla) {
            actual.derecho = insertarRecursivo(actual.derecho, codigo, descripcion);
        }
        return actual;
    }
    
    public void preorden(NodoArbol nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append("[").append(nodo.codigoFalla).append(" - ").append(nodo.descripcionFalla).append("] ");
            preorden(nodo.izquierdo, sb);
            preorden(nodo.derecho, sb);
        }
    }
    
    public void inorden(NodoArbol nodo, StringBuilder sb) {
        if (nodo != null) {
            inorden(nodo.izquierdo, sb);
            sb.append("[").append(nodo.codigoFalla).append(" - ").append(nodo.descripcionFalla).append("] ");
            inorden(nodo.derecho, sb);
        }
    }
    
    public void postorden(NodoArbol nodo, StringBuilder sb) {
        if (nodo != null) {
            postorden(nodo.izquierdo, sb);
            postorden(nodo.derecho, sb);
            sb.append("[").append(nodo.codigoFalla).append(" - ").append(nodo.descripcionFalla).append("] ");
        }
    }
    
    public String obtenerPreorden() {
        StringBuilder sb = new StringBuilder();
        preorden(raiz, sb);
        return sb.toString();
    }

    public String obtenerInorden() {
        StringBuilder sb = new StringBuilder();
        inorden(raiz, sb);
        return sb.toString();
    }

    public String obtenerPostorden() {
        StringBuilder sb = new StringBuilder();
        postorden(raiz, sb);
        return sb.toString();
    }
    
}
