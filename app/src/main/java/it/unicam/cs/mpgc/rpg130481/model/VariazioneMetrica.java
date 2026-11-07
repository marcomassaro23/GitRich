package it.unicam.cs.mpgc.rpg130481.model;

public record VariazioneMetrica(String nomeMetrica, int variazione) implements ModificaMetrica {
    public VariazioneMetrica {
        if (nomeMetrica == null || nomeMetrica.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
    }
}
