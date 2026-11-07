package it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi;

import java.util.List;

/**
 * Interfaccia che rappresenta un'opzione di un evento leggibile nel gioco.
 * Contiene metodi per ottenere il testo dell'opzione e le modifiche alle metriche associate all'opzione.
 */
public interface OpzioneEventoLeggibile {
    /**
     * Restituisce il testo dell'opzione.
     *
     * @return il testo dell'opzione
     */
    String getTesto();

    /**
     * Restituisce la lista delle modifiche alle metriche associate all'opzione.
     *
     * @return la lista delle modifiche alle metriche associate all'opzione
     */
    List<ModificaMetrica> getModifiche();
}
