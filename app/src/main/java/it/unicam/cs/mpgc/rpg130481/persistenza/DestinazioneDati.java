package it.unicam.cs.mpgc.rpg130481.persistenza;

@FunctionalInterface
public interface DestinazioneDati<T> {
    void salva(T dati);
}
