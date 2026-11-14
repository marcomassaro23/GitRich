package it.unicam.cs.mpgc.rpg130481.partita.stati;

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
