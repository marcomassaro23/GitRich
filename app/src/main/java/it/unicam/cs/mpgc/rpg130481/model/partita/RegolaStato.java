package it.unicam.cs.mpgc.rpg130481.model.partita;

import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;

public interface RegolaStato {
    boolean isSoddisfattaDa(Partita partita);
    StatoPartita getStatoRisultante();
}
