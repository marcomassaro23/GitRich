package it.unicam.cs.mpgc.rpg130481.model.core.partita;

/**
 * Rappresenta un turno di gioco in una partita.
 * Contiene informazioni sul numero del turno corrente e sul numero massimo di turni.
 *
 * @param numeroCorrente il numero del turno corrente
 * @param numeroMassimo il numero massimo di turni nella partita
 */
public record Turno(int numeroCorrente, int numeroMassimo) {
    /**
     * Costruisce un nuovo turno.
     *
     * @param numeroCorrente il numero del turno corrente
     * @param numeroMassimo il numero massimo di turni nella partita
     */
    public Turno {
        if (numeroCorrente <= 0) {
            throw new IllegalArgumentException("Il numero del turno corrente deve essere positivo.");
        }
        if (numeroMassimo <= 0 || numeroCorrente > numeroMassimo) {
            throw new IllegalArgumentException("Il turno massimo non può essere minore del turno corrente.");
        }
    }

    /**
     * Restituisce il turno successivo.
     *
     * @return il turno successivo
     */
    public Turno successivo() {
        return new Turno(numeroCorrente + 1, numeroMassimo);
    }

    /**
     * Verifica se il turno corrente è l'ultimo turno della partita.
     *
     * @return true se il turno corrente è l'ultimo turno, false altrimenti
     */
    public boolean isUltimoTurno() {
        return numeroCorrente == numeroMassimo;
    }
}
