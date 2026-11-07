package it.unicam.cs.mpgc.rpg130481.model.core.partita.stati;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stato.StatoPartita;

/**
 * Rappresenta lo stato di una partita in bancarotta.
 * Implementa l'interfaccia StatoPartita e fornisce informazioni sullo stato della partita.
 */
public class StatoBancarotta implements StatoPartita {
    @Override public String getNomeStato() { return "BANCAROTTA"; }
    @Override public boolean isTerminata() { return true; }
}
