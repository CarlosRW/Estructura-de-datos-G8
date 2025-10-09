package PF.TheSandwichGuy.src.com;

/**
 * Clase que representa una Carta individual del mazo inglés.
 * Implementa Enums para asegurar valores consistentes para Palo y Valor.
 */
public class Carta {
    
    // --- Enums para las propiedades de la Carta ---

    public enum Palo {
        CORAZONES('♥', "Corazones", "Rojo"),
        DIAMANTES('♦', "Diamantes", "Rojo"),
        PICAS('♠', "Picas", "Negro"),
        TREBOLES('♣', "Tréboles", "Negro");

        private final char simbolo;
        private final String nombre;
        private final String color;

        Palo(char simbolo, String nombre, String color) {
            this.simbolo = simbolo;
            this.nombre = nombre;
            this.color = color;
        }

        public char getSimbolo() { return simbolo; }
        public String getColor() { return color; }
        public String getNombre() { return nombre; }
    }

    public enum Valor {
        AS("A", "As", 1),
        DOS("2", "Dos", 2),
        TRES("3", "Tres", 3),
        CUATRO("4", "Cuatro", 4),
        CINCO("5", "Cinco", 5),
        SEIS("6", "Seis", 6),
        SIETE("7", "Siete", 7),
        OCHO("8", "Ocho", 8),
        NUEVE("9", "Nueve", 9),
        DIEZ("10", "Diez", 10),
        JOTA("J", "Jota", 11),
        QUINA("Q", "Quina", 12),
        KA("K", "Ka", 13);

        private final String simbolo;
        private final String nombre;
        private final int valorNumerico;

        Valor(String simbolo, String nombre, int valorNumerico) {
            this.simbolo = simbolo;
            this.nombre = nombre;
            this.valorNumerico = valorNumerico;
        }

        public String getSimbolo() { return simbolo; }
        public int getValorNumerico() { return valorNumerico; }
        public String getNombre() { return nombre; }
    }

    // --- Atributos de la Clase Carta ---
    private final Palo palo;
    private final Valor valor;

    // Constructor para crear una instancia de Carta.
    public Carta(Palo palo, Valor valor) {
        this.palo = palo;
        this.valor = valor;
    }

    // --- Métodos Getters ---
    public Palo getPalo() { return palo; }
    public Valor getValor() { return valor; }

    /**
     * Obtiene el valor numérico de la carta (1-13), utilizado para validar combinaciones tipo "sándwich".
     */
    public int getValorParaSandwich() {
        return valor.getValorNumerico();
    }

    /**
     * Determina si esta carta comparte el mismo color (Rojo/Negro) con otra carta.
     * @param otra La carta con la que se compara.
     * @return true si ambas cartas son del mismo color, false en caso contrario.
     */
    public boolean mismoColor(Carta otra) {
        return this.palo.getColor().equals(otra.palo.getColor());
    }

    /**
     * Representación en String de la carta (ej: [A|♣]).
     */
    @Override
    public String toString() {
        return String.format("[%s|%c]", valor.getSimbolo(), palo.getSimbolo());
    }
}