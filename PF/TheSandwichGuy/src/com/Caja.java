package PF.TheSandwichGuy.src.com;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la Caja, que contiene la baraja completa de 52 cartas instanciadas
 * una única vez y ordenadas.
 */
public class Caja {
    
    private final List<Carta> baraja;

    // Constructor: Instancia las 52 cartas por única vez.
    public Caja() {
        this.baraja = new ArrayList<>();
        // Recorrer palos y valores para crear las 52 cartas
        for (Carta.Palo palo : Carta.Palo.values()) {
            for (Carta.Valor valor : Carta.Valor.values()) {
                baraja.add(new Carta(palo, valor));
            }
        }
    }

    /**
     * Obtiene la baraja completa, lista para ser barajada y pasada al Mazo.
     * @return La lista de 52 cartas.
     */
    public List<Carta> obtenerCartas() {
        return new ArrayList<>(baraja); 
    }

    public int size() {
        return baraja.size();
    }

    @Override
    public String toString() {
        // La Caja debe contener las 52 cartas instanciadas, aunque estén en Mazo/Mano/Pozo
        return "Caja contiene: " + size() + " cartas instanciadas (referencias en memoria).";
    }
}