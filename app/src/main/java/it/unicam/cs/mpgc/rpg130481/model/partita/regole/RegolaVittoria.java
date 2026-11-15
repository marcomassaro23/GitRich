package it.unicam.cs.mpgc.rpg130481.model.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoPartita;
import java.util.Objects;

public class RegolaVittoria implements RegolaStato {

    @Override
    public boolean isSoddisfattaDa(Partita partita) {
        Objects.requireNonNull(partita, "La partita non può essere nulla.");
        return partita.getTurno().isUltimo() && !partita.getStatistiche().hasMetricheCritiche();
    }

    @Override
    public StatoPartita getStatoRisultante() {
        return StatoPartita.VITTORIA;
    }
}
