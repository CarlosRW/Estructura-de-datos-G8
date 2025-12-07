package com.sandwichguy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author grupo 8
 */
// Representa la Mano como una Lista Circular de Cartas utilizando nodos
public class Mano {

    private Nodo cabeza;
    private int tamano;

    // --- Clase interna Nodo para la lista circular ---
    private static class Nodo {

        Carta carta;
        Nodo siguiente;

        public Nodo(Carta carta) {
            this.carta = carta;
            this.siguiente = null; // En una lista circular, se inicializa al agregar
        }
    }
    // --------------------------------------------------

    public Mano() {
        this.cabeza = null;
        this.tamano = 0;
    }

    public void agregar(Carta carta) {
        Nodo nuevoNodo = new Nodo(carta);

        if (cabeza == null) {
            // El primer nodo se apunta a sí mismo (circular)
            cabeza = nuevoNodo;
            cabeza.siguiente = cabeza;
        } else {
            // Recorrer hasta el nodo justo antes de la cabeza (el último nodo)
            Nodo actual = cabeza;
            while (actual.siguiente != cabeza) {
                actual = actual.siguiente;
            }

            // 1. El nuevo nodo apunta a la cabeza
            nuevoNodo.siguiente = cabeza;
            // 2. El último nodo (el actual) apunta al nuevo nodo
            actual.siguiente = nuevoNodo;
        }
        tamano++;
    }

    public void agregarCarta(Carta carta) {
        this.agregar(carta);
    }

    public boolean eliminar(Carta carta) {
        if (cabeza == null) {
            return false;
        }

        // Caso: La lista solo tiene un elemento
        if (cabeza.carta.equals(carta) && cabeza.siguiente == cabeza) {
            cabeza = null;
            tamano--;
            return true;
        }

        Nodo actual = cabeza;
        Nodo anterior = null;

        // Iterar hasta que volvamos a la cabeza
        do {
            if (actual.carta.equals(carta)) {
                // Caso: Eliminando la cabeza
                if (actual == cabeza) {
                    // Encontrar el último nodo (el que apunta a la cabeza)
                    Nodo ultimo = cabeza;
                    while (ultimo.siguiente != cabeza) {
                        ultimo = ultimo.siguiente;
                    }
                    cabeza = cabeza.siguiente; // Mover la cabeza
                    ultimo.siguiente = cabeza; // El último apunta a la nueva cabeza
                } // Caso: Eliminando un nodo intermedio/cola
                else {
                    anterior.siguiente = actual.siguiente;
                }

                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        } while (actual != cabeza); // Condición de parada para la circularidad

        return false; // Carta no encontrada
    }

    // Alias para mantener compatibilidad
    public void removerCarta(Carta carta) {
        this.eliminar(carta);
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    // Alias para mantener compatibilidad
    public boolean isEmpty() {
        return this.estaVacia();
    }

    public int tamano() {
        return tamano;
    }

    // Alias para mantener compatibilidad
    public int size() {
        return this.tamano();
    }

    public void limpiar() {
        cabeza = null;
        tamano = 0;
    }

    // Devuelve una lista (no circular) de las cartas de la Mano
    public List<Carta> obtenerTodasLasCartas() {
        List<Carta> cartasEnMano = new ArrayList<>();
        if (cabeza == null) {
            return cartasEnMano;
        }

        Nodo actual = cabeza;
        do {
            cartasEnMano.add(actual.carta);
            actual = actual.siguiente;
        } while (actual != cabeza); // Recorrer hasta volver a la cabeza

        return cartasEnMano; // Devolvemos copia para evitar mutación externa
    }

    // Alias para mantener compatibilidad
    public List<Carta> obtenerCartas() {
        return this.obtenerTodasLasCartas();
    }

    // Obtiene una carta de la Mano de forma circular Si el índice excede el
    public Carta obtenerCartaCircular(int indice) {
        if (cabeza == null || tamano == 0) {
            return null;
        }

        // Aplicar la lógica circular (índice % tamaño)
        int indiceEfectivo = indice % tamano;

        // Manejar índices negativos
        if (indiceEfectivo < 0) {
            indiceEfectivo += tamano;
        }

        Nodo actual = cabeza;
        for (int i = 0; i < indiceEfectivo; i++) {
            actual = actual.siguiente;
        }

        return actual.carta;
    }

    public void ordenar() {
        if (tamano <= 1) {
            return;
        }

        // 1. Obtener todas las cartas como una lista normal
        List<Carta> cartasAOrdenar = obtenerTodasLasCartas();

        // 2. Ordenar
        Collections.sort(cartasAOrdenar, (c1, c2) -> Integer.compare(c1.getValorParaSandwich(), c2.getValorParaSandwich()));

        // 3. Limpiar la estructura circular
        limpiar();

        // 4. Re-agregar las cartas en el orden correcto
        for (Carta c : cartasAOrdenar) {
            agregar(c);
        }
    }

    public boolean existeSandwichValido() {
        if (tamano < 3) {
            return false;
        }

        // Verificar todas las combinaciones de 3 cartas (C(N, 3))
        List<Carta> todas = this.obtenerTodasLasCartas();
        int n = todas.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    List<Carta> tripleta = new ArrayList<>();
                    tripleta.add(todas.get(i));
                    tripleta.add(todas.get(j));
                    tripleta.add(todas.get(k));

                    // Generar todas las 6 permutaciones de esta tripleta
                    PermutationTree tree = new PermutationTree(tripleta);

                    // Chequear si alguna permutación es válida
                    for (PermutationTree.Node node : tree.getOpciones()) {
                        if (node.isEsSandwichValido()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
