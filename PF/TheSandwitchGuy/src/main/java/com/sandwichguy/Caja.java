package com.sandwichguy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */

// Representa la Caja (Lista Doble), contiene las 52 instancias únicas de Carta.
public class Caja {

    // Almacena las 52 cartas
    private final List<Carta> baraja;

    // Constructor
    public Caja() {
        this.baraja = new ArrayList<>();
        // Crea las 52 cartas, 13 por cada palo
        for (Carta.Palo palo : Carta.Palo.values()) {
            for (Carta.Valor valor : Carta.Valor.values()) {
                baraja.add(new Carta(palo, valor));
            }
        }
    }

    // Devuelve la lista de cartas
    public List<Carta> obtenerCartas() {
        return new ArrayList<>(baraja);
    }

    public int size() {
        return baraja.size();
    }

    // Muestra la baraja en orden para verificar la instanciación.
    public void mostrarBarajaCompleta() {
        int count = 0;
        for (Carta carta : baraja) {
            System.out.printf("%s ", carta.toString());
            count++;
            if (count % 13 == 0) { // Salto de línea por cada palo
                System.out.println();
            }
        }
        System.out.println("-----------------------------------------------------");
    }

    @Override
    public String toString() {
        return "Caja: 52 cartas (listas para ser movidas).";
    }
}
