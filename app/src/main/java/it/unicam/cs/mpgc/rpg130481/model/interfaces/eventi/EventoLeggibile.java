package it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi;

import java.util.List;

/**
 * Interfaccia che rappresenta un evento leggibile nel gioco.
 * Contiene metodi per ottenere il titolo, la descrizione e le opzioni disponibili per l'evento.
 */
public interface EventoLeggibile {
    /**
     * Restituisce il titolo dell'evento.
     *
     * @return il titolo dell'evento
     */
    String getTitolo();

    /**
     * Restituisce la descrizione dell'evento.
     *
     * @return la descrizione dell'evento
     */
    String getDescrizione();

    /**
     * Restituisce la lista delle opzioni disponibili per l'evento.
     *
     * @return la lista delle opzioni disponibili per l'evento
     */
    List<OpzioneEventoLeggibile> getOpzioniLeggibili();
}
