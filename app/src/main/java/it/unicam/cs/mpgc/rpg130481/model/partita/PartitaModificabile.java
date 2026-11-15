package it.unicam.cs.mpgc.rpg130481.model.partita;

import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;

public interface PartitaModificabile extends Partita {
    void applicaScelta(int indiceOpzione);
    void avanzaTurno();
    void setStato(StatoPartita nuovoStato);
}
