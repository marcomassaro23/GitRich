package it.unicam.cs.mpgc.rpg130481.partita;

import it.unicam.cs.mpgc.rpg130481.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.partita.stati.StatoPartita;

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
