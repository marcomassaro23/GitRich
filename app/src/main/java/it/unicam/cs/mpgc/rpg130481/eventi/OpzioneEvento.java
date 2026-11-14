package it.unicam.cs.mpgc.rpg130481.eventi;

import java.util.List;

public record OpzioneEvento(String testo, List<VariazioneMetrica> variazioni) {
    public OpzioneEvento {
        if (testo == null || testo.isBlank()) {
            throw new IllegalArgumentException("Il testo dell'opzione non può essere nullo o vuoto.");
        }
        if (variazioni == null) {
            throw new IllegalArgumentException("La lista delle variazioni non può essere nulla.");
        }
        variazioni = List.copyOf(variazioni);
    }
}
