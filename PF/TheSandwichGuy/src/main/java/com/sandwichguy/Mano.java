package com.sandwichguy;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections; // Necesario para la eliminación si no se usa la eliminación por objeto

/**
 *
 * @author grupo 8
 */

// Representa la Mano (Lista Circular de Cartas)
// Se usa LinkedList, una lista doblemente enlazada, para simular la estructura
// y la circularidad se garantiza mediante la aritmética modular en los accesos
public class Mano {

    private final List<Carta> cartas;
    
    public Mano() {
        // Usamos LinkedList para simular la estructura enlazada
        this.cartas = new LinkedList<>();
    }

    public void agregar(Carta carta) {
        // Añadir una carta al final de la lista
        this.cartas.add(carta);
    }
    
    // Alias para mantener compatibilidad
    public void agregarCarta(Carta carta) {
        this.agregar(carta);
    }

    public boolean eliminar(Carta carta) {
        // Remover una carta. La implementación de LinkedList lo hace eficientemente.
        return this.cartas.remove(carta);
    }
    
    // Alias para mantener compatibilidad
    public void removerCarta(Carta carta) {
        this.eliminar(carta);
    }

    public boolean estaVacia() {
        return cartas.isEmpty();
    }
    
    // Alias para mantener compatibilidad
    public boolean isEmpty() {
        return this.estaVacia();
    }

    public int tamano() {
        return cartas.size();
    }
    
    // Alias para mantener compatibilidad
    public int size() {
        return this.tamano();
    }

    public void limpiar() {
        cartas.clear();
    }

    // Devuelve una copia de las cartas de la Mano
    public List<Carta> obtenerTodasLasCartas() {
        return new LinkedList<>(this.cartas); // Devolvemos copia
    }
    
    // Alias para mantener compatibilidad
    public List<Carta> obtenerCartas() {
        return this.obtenerTodasLasCartas();
    }
    
    /**
     * Obtiene una carta de la Mano de forma circular
     * Si el índice excede el tamaño, vuelve al inicio (índice % tamaño).
     */
    public Carta obtenerCartaCircular(int indice) {
        if (cartas.isEmpty()) {
            return null;
        }
        
        int size = cartas.size();
        // Aplica la lógica circular (índice % tamaño)
        int indiceCircular = indice % size;
        
        // Maneja índices negativos (necesario si se quiere iterar hacia atrás)
        if (indiceCircular < 0) {
            indiceCircular += size;
        }
        
        return cartas.get(indiceCircular);
    }
}