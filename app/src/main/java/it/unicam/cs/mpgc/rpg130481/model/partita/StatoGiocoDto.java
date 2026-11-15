package it.unicam.cs.mpgc.rpg130481.model.partita;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;

import java.util.Objects;

public record StatoGiocoDto(
    Turno turno,
    StatisticheAzienda statistiche,
    Evento eventoCorrente,
    StatoPartita statoPartita
) {
    public StatoGiocoDto {
        Objects.requireNonNull(turno, "Il turno non può essere nullo.");
        Objects.requireNonNull(statistiche, "Le statistiche non possono essere nulle.");
        Objects.requireNonNull(statoPartita, "Lo stato della partita non può essere nullo.");
    }

    public static StatoGiocoDto daPartita(Partita partita) {
        Objects.requireNonNull(partita, "La partita non può essere nulla.");
        return new StatoGiocoDto(
            partita.getTurno(),
            partita.getStatistiche(),
            partita.getEventoCorrente(),
            partita.getStato()
        );
    }

    public boolean isTerminata() {
        return statoPartita.isTerminata();
    }
}
