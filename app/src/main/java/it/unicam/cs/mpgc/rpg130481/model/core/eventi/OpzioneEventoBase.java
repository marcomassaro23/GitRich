package it.unicam.cs.mpgc.rpg130481.model.core.eventi;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.OpzioneEvento;
import java.util.List;

public class OpzioneEventoBase implements OpzioneEvento {

    private final String testo;
    private final List<VariazioneMetrica> variazioni;

    public OpzioneEventoBase(String testo, List<? extends VariazioneMetrica> variazioni) {
        if (testo == null || testo.isBlank()) {
            throw new IllegalArgumentException("Il testo dell'opzione non può essere nullo o vuoto.");
        }
        if (variazioni == null) {
            throw new IllegalArgumentException("La lista delle variazioni non può essere nulla.");
        }
        this.testo = testo;
        this.variazioni = List.copyOf(variazioni);
    }

    @Override
    public String getTesto() {
        return testo;
    }

    @Override
    public List<VariazioneMetrica> getVariazioni() {
        return List.copyOf(this.variazioni);
    }
}
