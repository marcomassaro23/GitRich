package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati;

/**
 * Interfaccia che rappresenta lo stato di una partita.
 * Contiene metodi per ottenere il nome dello stato e verificare se la partita è terminata.
 */
public interface StatoPartita {
    /**
     * Restituisce il nome dello stato della partita.
     *
     * @return il nome dello stato della partita
     */
    String getNomeStato();

    /**
     * Verifica se la partita è terminata.
     *
     * @return true se la partita è terminata, false altrimenti
     */
    boolean isTerminata();
}
