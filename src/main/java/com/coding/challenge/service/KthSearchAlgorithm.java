package com.coding.challenge.service;

import org.springframework.stereotype.Service;

/**
 * Diese Klasse gibt die Kth Zahl eines Arrays zurück.
 *
 * @author Dhalia, und Internetrecherche
 */
@Service
public class KthSearchAlgorithm {

    /**
     * Eine Hilfsfunktion um zwei Elemente in einem Array zu tauschen
     *
     * @param arr Array in welchem die beiden Elemente getauscht werden sollen
     * @param i   erstes Element
     * @param j   zweites Element
     */
    private static void swap(Long[] arr, int i, int j) {
        Long temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Dies ist eine Quicksort Sortierung um die Kth höchste Zahl zu ermitteln,
     * wobei der Pivot Punkt die zu ermittelnde Kthe Zahl ist.
     *
     * @param arr        zu durchsuchendes Array vom Typ Long[]
     * @param low        niedrigster Index im Array
     * @param high       höchster Index im Array
     * @param kthHighest die zu findende Kth höchste Zahl (bsp.: die 6. höchste Zahl wäre kthHighest = 6)
     * @return die Zahl an kth höchster Stelle im Array, vom Typ Long
     */
    public Long getKthHighest(Long[] arr, int low, int high, int kthHighest) {

        int pivot = kthHighest;

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is smaller
            // than the pivot
            if (arr[j] < pivot) {
                // Increment index of
                // smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);

        return arr[kthHighest];
    }
}
