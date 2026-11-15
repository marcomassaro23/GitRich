package it.unicam.cs.mpgc.rpg130481.model.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoBancarotta;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;

public class RegolaBancarotta implements RegolaStato {

    @Override
    public boolean isSoddisfattaDa(Partita partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere nulla.");
        }
        return partita.getStatistiche().hasMetricheCritiche();
    }

    @Override
    public StatoPartita getStatoRisultante() {
        return new StatoBancarotta();
    }
}
