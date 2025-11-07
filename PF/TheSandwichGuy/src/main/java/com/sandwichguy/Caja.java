package com.sandwichguy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grupo 8
 */

// Representa la Caja (Lista Doble), contiene las 52 instancias únicas de Carta.
public class Caja {

    // Almacena las 52 cartas
    private final List<Carta> baraja;

    public Caja(List<Carta> cartasIniciales) {
        this.baraja = new ArrayList<>(cartasIniciales);
    }

    public List<Carta> moverTodoElContenido() {
        List<Carta> contenido = new ArrayList<>(this.baraja);
        this.baraja.clear();
        return contenido;
    }

    public int size() {
        return baraja.size();
    }

    public List<Carta> obtenerCartas() {
        return new ArrayList<>(baraja);
    }

    public void agregarCartas(List<Carta> cartas) {
        this.baraja.addAll(cartas);
    }

    // Método para cumplir con la visualización de la Caja
    public String getResumen() {
        if (baraja.isEmpty()) {
            return "La Caja está vacía.";
        }
        return String.format("Caja contiene %d cartas. (Ejemplo: %s)",
                baraja.size(),
                baraja.get(0).getCaracteristicas());
    }
}
