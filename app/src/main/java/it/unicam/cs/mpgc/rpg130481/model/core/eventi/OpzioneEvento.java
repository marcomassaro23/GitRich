package it.unicam.cs.mpgc.rpg130481.model.core.eventi;

import java.util.List;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.ModificaMetrica;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.OpzioneEventoLeggibile;

/**
 * Classe che rappresenta un'opzione di un evento nel gioco.
 * Contiene il testo dell'opzione e le modifiche alle metriche associate all'opzione.
 */
public class OpzioneEvento implements OpzioneEventoLeggibile {
    private final String testo;
    private final List<VariazioneMetrica> modifiche;

    /**
     * Costruttore per creare un'opzione di un evento.
     *
     * @param testo     il testo dell'opzione
     * @param modifiche le modifiche alle metriche associate all'opzione
     * @throws IllegalArgumentException se il testo è nullo o vuoto, o se la lista delle modifiche è nulla
     */
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
