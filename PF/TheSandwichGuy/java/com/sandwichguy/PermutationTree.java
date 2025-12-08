package com.sandwichguy;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author grupo 8
 */

// Estructura de árbol para manejar las permutaciones de las cartas seleccionadas
public class PermutationTree {

    public static class Node {
        private final List<Carta> permutacion;
        private final int cartasATomar;
        private final boolean esSandwichValido;
        private final String descripcion;

        public Node(List<Carta> permutacion, int cartasATomar, boolean esSandwichValido, String descripcion) {
            this.permutacion = permutacion;
            this.cartasATomar = cartasATomar;
            this.esSandwichValido = esSandwichValido;
            this.descripcion = descripcion;
        }

        public List<Carta> getPermutacion() {
            return permutacion;
        }

        public int getCartasATomar() {
            return cartasATomar;
        }

        public boolean isEsSandwichValido() {
            return esSandwichValido;
        }

        public String getDescripcion() {
            return descripcion;
        }

        @Override
        public String toString() {
            // Formato para mostrar en el diálogo
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (Carta c : permutacion)
                sb.append(c).append(" ");
            sb.setLength(sb.length() - 1); // remove last space
            sb.append("] -> ");
            if (esSandwichValido) {
                sb.append("VÁLIDO. Tomar ").append(cartasATomar).append(" carta(s).");
            } else {
                sb.append("No es sándwich.");
            }
            return sb.toString();
        }
    }

    private final List<Node> hojas;

    public PermutationTree(List<Carta> seleccion) {
        this.hojas = new ArrayList<>();
        if (seleccion != null && seleccion.size() == 3) {
            generarPermutaciones(seleccion);
        }
    }

    public List<Node> getOpciones() {
        return hojas;
    }

    private void generarPermutaciones(List<Carta> input) {
        // Generar las 6 permutaciones manuales de 3 cartas (0,1,2)
        // Indices: 012, 021, 102, 120, 201, 210
        int[][] combinaciones = {
                { 0, 1, 2 }, { 0, 2, 1 },
                { 1, 0, 2 }, { 1, 2, 0 },
                { 2, 0, 1 }, { 2, 1, 0 }
        };

        for (int[] idx : combinaciones) {
            List<Carta> p = new ArrayList<>();
            p.add(input.get(idx[0]));
            p.add(input.get(idx[1]));
            p.add(input.get(idx[2]));
            evaluarPermutacion(p);
        }
    }

    private void evaluarPermutacion(List<Carta> triplet) {
        Carta c1 = triplet.get(0);
        Carta c2 = triplet.get(1);
        Carta c3 = triplet.get(2);

        int v1 = c1.getValorParaSandwich();
        int v2 = c2.getValorParaSandwich();
        int v3 = c3.getValorParaSandwich();

        int diff1 = calcularDiferencia(v1, v2);
        int diff2 = calcularDiferencia(v2, v3);

        boolean valido = (diff1 == diff2);
        int puntos = 0;
        String desc = "Inválido";

        if (valido) {
            // Verificar criterio de puntos
            if (c1.esMismoPalo(c2) && c2.esMismoPalo(c3)) {
                puntos = 4;
                desc = "Mismo Palo";
            } else if (c1.esMismoColor(c2) && c2.esMismoColor(c3)) {
                puntos = 3;
                desc = "Mismo Color";
            } else {
                // Distinto color
                puntos = 2;
                desc = "Distinto Color";
            }
        }

        hojas.add(new Node(triplet, puntos, valido, desc));
    }

    private int calcularDiferencia(int a, int b) {
        int diff = Math.abs(a - b);

        int d = Math.abs(a - b);
        if (d > 6) { // 13/2 = 6.5
            return 13 - d;
        }
        return d;
    }
}
