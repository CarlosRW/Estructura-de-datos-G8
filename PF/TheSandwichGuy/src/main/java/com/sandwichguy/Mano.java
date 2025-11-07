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

    public void agregarCarta(Carta carta) {
        // Añadir una carta al final de la lista
        this.cartas.add(carta);
    }

    public void removerCarta(Carta carta) {
        // Remover una carta
        this.cartas.remove(carta);
    }

    public boolean isEmpty() {
        return cartas.isEmpty();
    }

    public int size() {
        return cartas.size();
    }

    public void limpiar() {
        cartas.clear();
    }

    //Devuelve las cartas de la Mano.
    public List<Carta> obtenerCartas() {
        return this.cartas;
    }
}
