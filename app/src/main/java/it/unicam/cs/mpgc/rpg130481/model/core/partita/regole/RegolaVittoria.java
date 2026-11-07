package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.Partita;

/**
 * La classe RegolaVittoria rappresenta la regola di vittoria per una partita.
 * La regola stabilisce che una partita è vinta se si è raggiunto l'ultimo turno e non ci sono metriche critiche.
 */
public class RegolaVittoria {
    /**
     * Verifica se la regola di vittoria è rispettata per la partita specificata.
     *
     * @param partita la partita da verificare
     * @return true se la regola di vittoria è rispettata, false altrimenti
     */
    public boolean isRispettata(Partita partita) {
        return partita.getTurno().isUltimoTurno() && !partita.getStatistiche().haMetricheCritiche();
    }
}
