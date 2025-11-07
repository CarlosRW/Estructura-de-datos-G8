package com.sandwichguy;

import java.util.ArrayDeque;
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

    public boolean isEmpty() {
        return pila.isEmpty();
    }

    public int size() {
        return pila.size();
    }

    public void limpiar() {
        pila.clear();
    }

    public List<Carta> obtenerTodasLasCartas() {
        // Devuelve las cartas para la visualización
        return List.copyOf(pila);
    }
}
