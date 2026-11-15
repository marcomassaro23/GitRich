package it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio;

import it.unicam.cs.mpgc.rpg130481.controller.StatoGiocoDto;

public interface StatoPartitaRepository extends SorgenteStatoPartita {
    void salva(StatoGiocoDto stato);
}
