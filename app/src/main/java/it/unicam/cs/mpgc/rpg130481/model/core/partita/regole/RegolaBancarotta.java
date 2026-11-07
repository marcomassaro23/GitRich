package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.Partita;

/**
 * La classe RegolaBancarotta rappresenta una regola di gioco che determina se una partita è in bancarotta.
 * La regola verifica se le statistiche della partita hanno metriche critiche, indicando che la partita è terminata.
 */
public class RegolaBancarotta {
    /**
     * Verifica se la regola di bancarotta è rispettata per la partita specificata.
     *
     * @param partita la partita da verificare
     * @return true se la regola di bancarotta è rispettata, false altrimenti
     */
    public boolean isRispettata(Partita partita) {
        return partita.getStatistiche().haMetricheCritiche();
    }
}
