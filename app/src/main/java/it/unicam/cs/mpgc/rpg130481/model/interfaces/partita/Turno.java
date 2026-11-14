package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita;

/**
 * Interfaccia che rappresenta un turno leggibile di una partita.
 * Contiene metodi per ottenere il numero corrente del turno, il numero massimo di turni e verificare se è l'ultimo turno.
 */
public interface Turno {
    /**
     * Restituisce il numero corrente del turno.
     *
     * @return il numero corrente del turno
     */
    int numeroCorrente();

    /**
     * Restituisce il numero massimo di turni della partita.
     *
     * @return il numero massimo di turni della partita
     */
    int numeroMassimo();

    /**
     * Verifica se il turno corrente è l'ultimo turno della partita.
     *
     * @return true se il turno corrente è l'ultimo turno, false altrimenti
     */
    boolean isUltimoTurno();
}
