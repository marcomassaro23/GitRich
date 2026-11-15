package it.unicam.cs.mpgc.rpg130481.model.partita.stati;

public class StatoVittoria implements StatoPartita {

    @Override
    public String getCodice() {
        return "VITTORIA";
    }

    @Override
    public boolean isTerminata() {
        return true;
    }

    @Override
    public boolean isVittoria() {
        return true;
    }
}
