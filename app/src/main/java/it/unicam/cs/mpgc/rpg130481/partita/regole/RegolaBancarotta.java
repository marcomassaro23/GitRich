package it.unicam.cs.mpgc.rpg130481.partita.regole;

import it.unicam.cs.mpgc.rpg130481.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.partita.stati.StatoBancarotta;
import it.unicam.cs.mpgc.rpg130481.partita.stati.StatoPartita;

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
