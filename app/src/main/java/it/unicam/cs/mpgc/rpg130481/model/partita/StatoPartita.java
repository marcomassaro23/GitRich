package it.unicam.cs.mpgc.rpg130481.model.partita;

public enum StatoPartita {
    IN_CORSO(false),
    VITTORIA(true),
    BANCAROTTA(true);

    private final boolean terminata;

    StatoPartita(boolean terminata) {
        this.terminata = terminata;
    }

    public String getCodice() {
        return name();
    }

    public boolean isTerminata() {
        return terminata;
    }

    public static StatoPartita daCodice(String codice) {
        if (codice == null || codice.isBlank()) {
            throw new IllegalArgumentException("Il codice stato non può essere nullo o vuoto.");
        }
        try {
            return valueOf(codice.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Stato partita non riconosciuto: " + codice, e);
        }
    }
}
