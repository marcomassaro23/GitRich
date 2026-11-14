package it.unicam.cs.mpgc.rpg130481.repository.interfaces;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;

public interface StatoPartitaRepository {
    StatoGiocoDto carica();
    void salva(StatoGiocoDto stato);
}
