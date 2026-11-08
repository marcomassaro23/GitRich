package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoVittoria;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

/**
 * La classe RegolaVittoria rappresenta la regola di vittoria per una partita.
 * La regola stabilisce che una partita è vinta se si è raggiunto l'ultimo turno e non ci sono metriche critiche.
 */
public class RegolaVittoria implements RegolaStato {

    @Override
    public boolean isRispettata(Partita partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere nulla.");
        }
        return partita.getTurno().isUltimoTurno() && !partita.getStatistiche().haMetricheCritiche();
    }

    @Override
    public StatoPartita getStatoAssociato() {
        return new StatoVittoria();
    }
}
