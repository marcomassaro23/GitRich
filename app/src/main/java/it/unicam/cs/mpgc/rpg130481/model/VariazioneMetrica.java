package it.unicam.cs.mpgc.rpg130481.model;

/**
 * Record che rappresenta una variazione di una metrica.
 * Contiene il nome della metrica e la variazione da applicare.
 */
public record VariazioneMetrica(String nomeMetrica, int variazione) implements ModificaMetrica {
    /**
     * Costruttore per creare una variazione di una metrica.
     *
     * @param nomeMetrica il nome della metrica da modificare
     * @param variazione   la variazione da applicare alla metrica
     * @throws IllegalArgumentException se il nome della metrica è nullo o vuoto
     */
    public VariazioneMetrica {
        if (nomeMetrica == null || nomeMetrica.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
    }
}
