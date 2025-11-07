package com.sandwichguy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author grupo 8
 */

// Representa el Pozo (Cola de Cartas) FIFO
public class Pozo {

    private final Deque<Carta> cola; // Usando Deque como Cola

    public Pozo() {
        // FIFO: Se añaden al final (addLast) y se sacan del principio (removeFirst)
        this.cola = new ArrayDeque<>();
    }

    public void enqueue(Carta carta) {
        cola.addLast(carta); // Agregar carta al final de la cola (por donde se descarta)
    }

    public Carta dequeue() {
        return cola.pollFirst(); // Tomar carta del frente de la cola
    }

    public boolean isEmpty() {
        return cola.isEmpty();
    }

    public int size() {
        return cola.size();
    }

    public void limpiar() {
        cola.clear();
    }

    public List<Carta> obtenerTodasLasCartas() {
        return List.copyOf(cola);
    }
}
