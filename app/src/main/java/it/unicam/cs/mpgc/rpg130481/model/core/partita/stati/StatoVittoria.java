package it.unicam.cs.mpgc.rpg130481.model.core.partita.stati;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

/**
 * Rappresenta lo stato di una partita vinta.
 * Implementa l'interfaccia StatoPartita e fornisce informazioni sullo stato della partita.
 */
public class StatoVittoria implements StatoPartita {
    @Override public String getNomeStato() { return "VITTORIA"; }
    @Override public boolean isTerminata() { return true; }
}
