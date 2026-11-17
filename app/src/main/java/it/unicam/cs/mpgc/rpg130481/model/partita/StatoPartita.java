package it.unicam.cs.mpgc.rpg130481.model.partita;

public enum StatoPartita {
    IN_CORSO(false),
    VITTORIA(true),
    BANCAROTTA(true);

    private final boolean terminata;

    StatoPartita(boolean terminata) {
        this.terminata = terminata;
    }

    public boolean isTerminata() {
        return terminata;
    }
}
