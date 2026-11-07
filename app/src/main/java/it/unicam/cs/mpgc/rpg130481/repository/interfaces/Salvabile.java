package it.unicam.cs.mpgc.rpg130481.repository.interfaces;

/**
 * Interfaccia funzionale che rappresenta un oggetto salvabile.
 * Contiene un metodo per salvare un oggetto di tipo T.
 *
 * @param <T> il tipo dell'oggetto da salvare
 */
@FunctionalInterface
public interface Salvabile<T> {
    void salva(T oggetto);
}
