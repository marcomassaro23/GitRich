package it.unicam.cs.mpgc.rpg130481.partita.stati;

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
