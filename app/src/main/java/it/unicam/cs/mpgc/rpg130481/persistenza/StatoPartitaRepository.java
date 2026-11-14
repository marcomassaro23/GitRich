package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.partita.StatoGiocoDto;

public interface StatoPartitaRepository extends SorgenteStatoPartita {
    void salva(StatoGiocoDto stato);
}
