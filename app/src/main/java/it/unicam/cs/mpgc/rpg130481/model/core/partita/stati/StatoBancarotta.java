package it.unicam.cs.mpgc.rpg130481.model.core.partita.stati;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

public class StatoBancarotta implements StatoPartita {

    @Override
    public String getCodice() {
        return "BANCAROTTA";
    }

    @Override
    public boolean isTerminata() {
        return true;
    }
}
