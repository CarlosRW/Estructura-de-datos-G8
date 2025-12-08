package com.sandwichguy;

import javax.swing.ImageIcon;

/**
 *
 * @author Grupo 8
 */

// Clase Carta: define su valor, nombre, símbolo y color
public class Carta {

    // --- ENUMS ---
    public enum Palo {
        CORAZONES('♥', "Rojo"),
        DIAMANTES('♦', "Rojo"),
        PICAS('♠', "Negro"),
        TREBOLES('♣', "Negro");

        private final char simbolo;
        private final String color;

        Palo(char simbolo, String color) {
            this.simbolo = simbolo;
            this.color = color;
        }

        public char getSimbolo() {
            return simbolo;
        }

        public String getColor() {
            return color;
        }
    }

    public enum Valor {
        AS("A", 1), DOS("2", 2), TRES("3", 3), CUATRO("4", 4), CINCO("5", 5),
        SEIS("6", 6), SIETE("7", 7), OCHO("8", 8), NUEVE("9", 9), DIEZ("10", 10),
        JOTA("J", 11), QUINA("Q", 12), KA("K", 13);

        private final String simbolo;
        private final int valorNumerico;

        Valor(String simbolo, int valorNumerico) {
            this.simbolo = simbolo;
            this.valorNumerico = valorNumerico;
        }

        public String getSimbolo() {
            return simbolo;
        }

        public int getValorNumerico() {
            return valorNumerico;
        }
    }

    // Atributos
    private final Palo palo;
    private final Valor valor;
    private ImageIcon imagen;

    // --- CONSTRUCTOR ---
    public Carta(Palo palo, Valor valor, ImageIcon imagen) {
        this.palo = palo;
        this.valor = valor;
        this.imagen = imagen;
    }

    // --- GETTERS ---
    public Palo getPalo() {
        return palo;
    }

    public Valor getValor() {
        return valor;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    // Obtiene el valor numérico para la lógica del sándwich.
    public int getValorParaSandwich() {
        return valor.getValorNumerico();
    }

    // Método para mostrar las características
    public String getCaracteristicas() {
        return String.format("%s de %s (%c) | Valor: %d | Color: %s",
                valor.toString(),
                palo.toString(),
                palo.getSimbolo(),
                getValorParaSandwich(),
                palo.getColor());
    }

    // Formato de visualización: [SímboloValorSímboloPalo] (ej: [A♥]).
    @Override
    public String toString() {
        return String.format("[%s%c]", valor.getSimbolo(), palo.getSimbolo());
    }

    public boolean esMismoColor(Carta otra) {
        return this.palo.getColor().equals(otra.getPalo().getColor());
    }

    public boolean esMismoPalo(Carta otra) {
        return this.palo == otra.getPalo();
    }
}
