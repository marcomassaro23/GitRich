package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

public record StatoGiocoDto(
    Turno turno,
    StatisticheAzienda statistiche,
    Evento eventoCorrente,
    StatoPartita statoPartita,
    boolean isTerminata
) {
    public StatoGiocoDto {
        if (turno == null || statistiche == null || statoPartita == null) {
            throw new IllegalArgumentException("I campi obbligatori del DTO non possono essere nulli.");
        }
    }
}
