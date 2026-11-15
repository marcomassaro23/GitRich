package it.unicam.cs.mpgc.rpg130481.controller;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import it.unicam.cs.mpgc.rpg130481.model.partita.regole.ValutatoreStatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.selettori.SelettoreEventi;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.DatiSalvataggioPartita.DatiMetrica;

import java.util.List;
import java.util.Objects;

public class InizializzatorePartita {

    private final ValutatoreStatoPartita valutatore;
    private final SelettoreEventi selettoreEventi;

    public InizializzatorePartita(
            ValutatoreStatoPartita valutatore,
            SelettoreEventi selettoreEventi) {
        this.valutatore = Objects.requireNonNull(valutatore, "Il valutatore non può essere nullo.");
        this.selettoreEventi = Objects.requireNonNull(selettoreEventi, "Il selettore eventi non può essere nullo.");
    }

    public PartitaBase creaPartita(DatiSalvataggioPartita dati, List<Evento> mazzoEventi) {
        Objects.requireNonNull(dati, "I dati della partita non possono essere nulli.");

        List<MetricaBase> metriche = dati.metriche().stream()
                .map(this::creaMetrica)
                .toList();

        return new PartitaBase(
                new StatisticheAziendaBase(metriche),
                mazzoEventi,
                new Turno(dati.turno().corrente(), dati.turno().massimo()),
                selettoreEventi,
                valutatore,
                trovaEvento(dati.titoloEventoCorrente(), mazzoEventi),
                StatoPartita.daCodice(dati.codiceStato()));
    }

    private MetricaBase creaMetrica(DatiMetrica dati) {
        return new MetricaBase(
                dati.nome(),
                dati.valore(),
                dati.valoreMinimo(),
                dati.valoreMassimo(),
                dati.criticaAlMassimo());
    }

    private Evento trovaEvento(String titolo, List<Evento> mazzoEventi) {
        if (titolo == null || titolo.isBlank() || mazzoEventi == null) {
            return null;
        }
        return mazzoEventi.stream()
                .filter(evento -> evento.getTitolo().equals(titolo))
                .findFirst()
                .orElse(null);
    }
}
