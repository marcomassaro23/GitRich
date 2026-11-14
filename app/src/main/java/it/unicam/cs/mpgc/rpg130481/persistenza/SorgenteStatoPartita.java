package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.partita.StatoGiocoDto;

@FunctionalInterface
public interface SorgenteStatoPartita {
    StatoGiocoDto carica();
}
