package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoBancarotta;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

public class RegolaBancarotta implements RegolaStato {

    @Override
    public boolean isRispettata(PartitaBase partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere nulla.");
        }
        return partita.getStatistiche().haMetricheCritiche();
    }

    @Override
    public StatoPartita getStatoAssociato() {
        return new StatoBancarotta();
    }
}
