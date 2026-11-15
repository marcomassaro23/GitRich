package it.unicam.cs.mpgc.rpg130481.controller;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;

public record StatoGiocoDto(
    Turno turno,
    StatisticheAzienda statistiche,
    Evento eventoCorrente,
    StatoPartita statoPartita
) {
    public StatoGiocoDto {
        if (turno == null || statistiche == null || statoPartita == null) {
            throw new IllegalArgumentException("I campi obbligatori del DTO non possono essere nulli.");
        }
    }

    public boolean isTerminata() {
        return statoPartita.isTerminata();
    }
}
