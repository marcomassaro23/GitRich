package it.unicam.cs.mpgc.rpg130481.partita.stati;

public interface StatoPartita {
    String getCodice();
    boolean isTerminata();

    default boolean isVittoria() {
        return false;
    }
}
