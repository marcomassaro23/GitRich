package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

public interface RegolaStato {
    boolean isRispettata(PartitaBase partita);
    StatoPartita getStatoAssociato();
}
