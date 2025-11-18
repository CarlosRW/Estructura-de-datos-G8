package com.sandwichguy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author grupo 8
 */

// Representa la Mano (Lista Circular de Cartas)
public class Mano {

    private final List<Carta> cartas;
    
    public Mano() {
        // Inicializamos con ArrayList
        this.cartas = new ArrayList<>();
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
        // Remover una carta
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
        return new ArrayList<>(this.cartas);
    }
    
    // Alias para mantener compatibilidad
    public List<Carta> obtenerCartas() {
        return this.obtenerTodasLasCartas();
    }
}
