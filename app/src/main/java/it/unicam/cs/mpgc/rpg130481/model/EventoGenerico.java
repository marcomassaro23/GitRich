package it.unicam.cs.mpgc.rpg130481.model;

import java.util.List;

/**
 * Classe che rappresenta un evento generico nel gioco.
 * Estende la classe EventoBase e fornisce un'implementazione concreta per gli eventi generici.
 */
public class EventoGenerico extends EventoBase {
    /**
     * Costruttore per creare un evento generico.
     *
     * @param titolo      il titolo dell'evento
     * @param descrizione la descrizione dell'evento
     * @param opzioni     le opzioni disponibili per l'evento
     * @throws IllegalArgumentException se titolo o descrizione sono nulli o vuoti, o se la lista delle opzioni è nulla o vuota
     */
    public EventoGenerico(String titolo, String descrizione, List<OpzioneEvento> opzioni) {
        super(titolo, descrizione, opzioni);
    }
}
