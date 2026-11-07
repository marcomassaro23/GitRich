package it.unicam.cs.mpgc.rpg130481.repository.interfaces;

/**
 * Interfaccia funzionale che rappresenta un oggetto caricabile.
 * Contiene un metodo per caricare un oggetto di tipo T.
 *
 * @param <T> il tipo dell'oggetto da caricare
 */
@FunctionalInterface
public interface Caricabile<T> {
    T carica();
}
