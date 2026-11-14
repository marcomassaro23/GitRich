package it.unicam.cs.mpgc.rpg130481.model.core.eventi;

public record VariazioneMetrica(String nomeMetrica, int variazione) {
    public VariazioneMetrica {
        if (nomeMetrica == null || nomeMetrica.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
    }
}
