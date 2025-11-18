package com.sandwichguy;

import java.util.ArrayDeque;
import java.util.ArrayList;
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

    public void agregar(Carta carta) {
        cola.addLast(carta); // Agregar carta al final de la cola (por donde se descarta)
    }
    
    // Alias para mantener compatibilidad
    public void enqueue(Carta carta) {
        this.agregar(carta);
    }

    public Carta sacar() {
        return cola.pollFirst(); // Tomar carta del frente de la cola
    }
    
    // Alias para mantener compatibilidad
    public Carta dequeue() {
        return this.sacar();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }
    
    // Alias para mantener compatibilidad
    public boolean isEmpty() {
        return this.estaVacia();
    }

    public int tamano() {
        return cola.size();
    }
    
    // Alias para mantener compatibilidad
    public int size() {
        return this.tamano();
    }

    public void limpiar() {
        cola.clear();
    }

    public List<Carta> obtenerTodasLasCartas() {
        return new ArrayList<>(cola);
    }
    
    /**
     * Obtiene la última carta agregada al pozo (la más reciente)
     * @return La última carta o null si está vacío
     */
    public Carta obtenerUltimaCarta() {
        return cola.peekLast();
    }
    
    /**
     * Devuelve la carta superior del pozo sin eliminarla
     * @return La carta superior o null si el pozo está vacío
     */
    public Carta peek() {
        return cola.peekFirst();
    }
}
