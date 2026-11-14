package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.Turno;

/**
 * Rappresenta un turno di gioco in una partita.
 * Contiene informazioni sul numero del turno corrente e sul numero massimo di turni.
 *
 * @param numeroCorrente il numero del turno corrente
 * @param numeroMassimo il numero massimo di turni nella partita
 */
public record TurnoBase(int numeroCorrente, int numeroMassimo) implements Turno {
    /**
     * Costruisce un nuovo turno.
     *
     * @param numeroCorrente il numero del turno corrente
     * @param numeroMassimo il numero massimo di turni nella partita
     */
    public TurnoBase {
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
    public TurnoBase successivo() {
        return new TurnoBase(numeroCorrente + 1, numeroMassimo);
    }

    @Override
    public boolean isUltimoTurno() {
        return numeroCorrente == numeroMassimo;
    }
}
