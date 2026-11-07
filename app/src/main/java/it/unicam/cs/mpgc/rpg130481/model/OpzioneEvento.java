package it.unicam.cs.mpgc.rpg130481.model;

import java.util.List;

public class OpzioneEvento implements OpzioneEventoLeggibile {
    private final String testo;
    private final List<VariazioneMetrica> modifiche;

    public OpzioneEvento(String testo, List<VariazioneMetrica> modifiche) {
        if (testo == null || testo.isBlank()) {
            throw new IllegalArgumentException("Il testo dell'opzione non può essere nullo o vuoto.");
        }
        this.testo = testo;
        if (modifiche == null) {
            throw new IllegalArgumentException("La lista delle modifiche non può essere nulla.");
        }
        this.modifiche = List.copyOf(modifiche);
    }

    @Override
    public String getTesto() {
        return testo;
    }

    @Override
    public List<ModificaMetrica> getModifiche() {
        return List.copyOf(this.modifiche);
    }
}
