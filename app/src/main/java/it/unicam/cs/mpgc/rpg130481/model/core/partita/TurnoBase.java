package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.Turno;

public record TurnoBase(int numeroCorrente, int numeroMassimo) implements Turno {
    public TurnoBase {
        if (numeroCorrente <= 0) {
            throw new IllegalArgumentException("Il numero del turno corrente deve essere positivo.");
        }
        if (numeroMassimo <= 0 || numeroCorrente > numeroMassimo) {
            throw new IllegalArgumentException("Il turno massimo non può essere minore del turno corrente.");
        }
    }

    public TurnoBase successivo() {
        return new TurnoBase(numeroCorrente + 1, numeroMassimo);
    }

    @Override
    public boolean isUltimoTurno() {
        return numeroCorrente == numeroMassimo;
    }
}
