package it.unicam.cs.mpgc.rpg130481.model.core.partita.stati;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

/**
 * Rappresenta lo stato di una partita in corso.
 * Implementa l'interfaccia StatoPartita e fornisce informazioni sullo stato della partita.
 */
public class StatoInCorso implements StatoPartita {
    @Override public String getNomeStato() { return "IN_CORSO"; }
    @Override public boolean isTerminata() { return false; }
}
