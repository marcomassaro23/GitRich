package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoPartita;
import java.util.List;
import java.util.Objects;

public class MapperSalvataggioPartita {

    public DatiSalvataggioPartita versoDatiSalvataggio(StatoGiocoDto stato) {
        Objects.requireNonNull(stato, "Lo stato di gioco non può essere nullo.");
        List<DatiSalvataggioPartita.DatiMetrica> metriche = stato.statistiche().getMetriche().stream()
                .map(DatiSalvataggioPartita.DatiMetrica::daMetrica)
                .toList();
        return new DatiSalvataggioPartita(
                stato.turno(),
                metriche,
                stato.eventoCorrente().getTitolo(),
                stato.statoPartita());
    }

    public Partita versoPartita(DatiSalvataggioPartita dati, List<Evento> mazzoEventi) {
        Objects.requireNonNull(dati, "I dati di salvataggio non possono essere nulli.");
        Objects.requireNonNull(mazzoEventi, "Il mazzo eventi non può essere nullo.");
        StatoPartita stato = Objects.requireNonNullElse(dati.codiceStato(), StatoPartita.IN_CORSO);
        return new PartitaBase(
                new StatisticheAziendaBase(dati.toMetriche()),
                mazzoEventi,
                dati.turno(),
                trovaEventoPerTitolo(dati.titoloEventoCorrente(), mazzoEventi),
                stato);
    }

    private Evento trovaEventoPerTitolo(String titolo, List<Evento> mazzoEventi) {
        if (titolo == null || titolo.isBlank()) {
            return null;
        }
        return mazzoEventi.stream()
                .filter(evento -> evento.getTitolo().equals(titolo))
                .findFirst()
                .orElse(null);
    }
}
