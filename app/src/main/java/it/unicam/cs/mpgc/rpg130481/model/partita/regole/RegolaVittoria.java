package it.unicam.cs.mpgc.rpg130481.model.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoVittoria;

public class RegolaVittoria implements RegolaStato {

    @Override
    public boolean isSoddisfattaDa(Partita partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere nulla.");
        }
        return partita.getTurno().isUltimo() && !partita.getStatistiche().hasMetricheCritiche();
    }

    @Override
    public StatoPartita getStatoRisultante() {
        return new StatoVittoria();
    }
}
