package it.unicam.cs.mpgc.rpg130481.partita;

import it.unicam.cs.mpgc.rpg130481.partita.stati.StatoPartita;

public interface RegolaStato {
    boolean isSoddisfattaDa(Partita partita);
    StatoPartita getStatoRisultante();
}
