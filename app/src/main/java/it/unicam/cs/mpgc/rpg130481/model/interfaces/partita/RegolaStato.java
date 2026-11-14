package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

/**
 * Interfaccia che rappresenta una regola di stato per una partita.
 * Contiene metodi per verificare se la regola è rispettata e ottenere lo stato associato.
 */
public interface RegolaStato {
    /**
     * Verifica se la regola è rispettata per la partita specificata.
     *
     * @param partita la partita da verificare
     * @return true se la regola è rispettata, false altrimenti
     */
    boolean isRispettata(PartitaBase partita);

    /**
     * Restituisce lo stato associato alla regola.
     *
     * @return lo stato associato alla regola
     */
    StatoPartita getStatoAssociato();
}
