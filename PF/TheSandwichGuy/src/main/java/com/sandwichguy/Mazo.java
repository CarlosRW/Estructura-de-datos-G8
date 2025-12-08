package com.sandwichguy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author grupo 8
 */

// Representa el Mazo (Pila de Cartas) LIFO
public class Mazo {

    private final Deque<Carta> pila; // Usando Deque como Pila

    public Mazo() {
        // LIFO: Las cartas se añaden y se sacan del "frente" o "cima"
        this.pila = new ArrayDeque<>();
    }

    public void push(Carta carta) {
        pila.addFirst(carta); // Poner carta encima (cima de la pila)
    }

    public Carta pop() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.removeFirst(); // Tomar carta de encima
    }

    public Carta peek() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.getFirst(); // Ver carta de encima
    }

    public boolean estaVacio() {
        return pila.isEmpty();
    }

    // Alias para mantener compatibilidad
    public boolean isEmpty() {
        return this.estaVacio();
    }

    public int tamano() {
        return pila.size();
    }

    // Alias para mantener compatibilidad
    public int size() {
        return this.tamano();
    }

    public void limpiar() {
        pila.clear();
    }

    public List<Carta> obtenerTodasLasCartas() {
        // Devuelve una copia de las cartas para la visualización
        return new ArrayList<>(pila);
    }

    /**
     * Obtiene la carta superior del mazo sin eliminarla
     *
     * @return La carta superior o null si el mazo está vacío
     */
    public Carta verCartaSuperior() {
        return pila.peek();
    }
}
