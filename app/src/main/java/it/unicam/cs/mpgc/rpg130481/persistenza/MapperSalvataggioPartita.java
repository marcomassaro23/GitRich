package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.partita.FabbricaPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import java.util.List;
import java.util.Objects;

public class MapperSalvataggioPartita {

    private final FabbricaPartita fabbricaPartita;

    public MapperSalvataggioPartita(FabbricaPartita fabbricaPartita) {
        this.fabbricaPartita = Objects.requireNonNull(fabbricaPartita, "La fabbrica partita non può essere nulla.");
    }

    public DatiSalvataggioPartita versoDatiSalvataggio(StatoGiocoDto stato) {
        Objects.requireNonNull(stato, "Lo stato di gioco non può essere nullo.");
        return new DatiSalvataggioPartita(
                stato.turno(),
                stato.metriche().stream().map(DatiSalvataggioPartita::copia).toList(),
                stato.eventoCorrente().getTitolo(),
                stato.statoPartita());
    }

    public Partita versoPartita(DatiSalvataggioPartita dati, List<Evento> mazzoEventi) {
        Objects.requireNonNull(dati, "I dati di salvataggio non possono essere nulli.");
        return fabbricaPartita.crea(
                dati.metriche(),
                mazzoEventi,
                dati.turno(),
                trovaEventoPerTitolo(dati.titoloEventoCorrente(), mazzoEventi),
                dati.codiceStato());
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
