package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoBancarotta;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

/**
 * La classe RegolaBancarotta rappresenta una regola di gioco che determina se una partita è in bancarotta.
 * La regola verifica se le statistiche della partita hanno metriche critiche, indicando che la partita è terminata.
 */
public class RegolaBancarotta implements RegolaStato {

    @Override
    public boolean isRispettata(Partita partita) {
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
