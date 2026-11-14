package it.unicam.cs.mpgc.rpg130481.model.core.eventi;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.OpzioneEvento;
import java.util.List;

public class OpzioneEventoBase implements OpzioneEvento {

    private final String testo;
    private final List<VariazioneMetrica> modifiche;

    public OpzioneEventoBase(String testo, List<? extends VariazioneMetrica> modifiche) {
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
    public List<VariazioneMetrica> getModifiche() {
        return List.copyOf(this.modifiche);
    }
}
