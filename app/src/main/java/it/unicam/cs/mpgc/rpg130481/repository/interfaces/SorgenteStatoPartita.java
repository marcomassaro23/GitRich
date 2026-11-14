package it.unicam.cs.mpgc.rpg130481.repository.interfaces;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;

@FunctionalInterface
public interface SorgenteStatoPartita {
    StatoGiocoDto carica();
}
