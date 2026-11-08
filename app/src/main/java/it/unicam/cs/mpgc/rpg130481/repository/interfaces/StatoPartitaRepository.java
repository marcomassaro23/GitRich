package it.unicam.cs.mpgc.rpg130481.repository.interfaces;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;

/**
 * Interfaccia che rappresenta un repository per la gestione dello stato della partita.
 * Estende l'interfaccia Persistibile per consentire il salvataggio e il caricamento dello stato della partita.
 */
public interface StatoPartitaRepository extends Persistibile<StatoGiocoDto> {}
