package it.unicam.cs.mpgc.rpg130481.persistenza;

@FunctionalInterface
public interface SorgenteDati<T> {
    T carica();
}
