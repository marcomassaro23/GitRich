package it.unicam.cs.mpgc.rpg130481.model.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;

public interface RegolaStato {
    boolean isSoddisfattaDa(Partita partita);
    StatoPartita getStatoRisultante();
}
