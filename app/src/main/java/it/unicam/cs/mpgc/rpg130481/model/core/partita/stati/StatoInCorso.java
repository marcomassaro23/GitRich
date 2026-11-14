package it.unicam.cs.mpgc.rpg130481.model.core.partita.stati;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

public class StatoInCorso implements StatoPartita {

    @Override
    public String getCodice() {
        return "IN_CORSO";
    }

    @Override
    public boolean isTerminata() {
        return false;
    }
}
