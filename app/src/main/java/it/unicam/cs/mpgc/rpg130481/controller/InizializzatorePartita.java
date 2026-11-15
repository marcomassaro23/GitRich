package it.unicam.cs.mpgc.rpg130481.controller;

import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.metriche.MetricaModificabile;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.GestoreStatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import it.unicam.cs.mpgc.rpg130481.model.partita.regole.ValutatoreStatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.selettori.SelettoreEventi;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.DatiSalvataggioPartita.DatiMetrica;

import java.util.List;
import java.util.Objects;

public class InizializzatorePartita {

    private final ValutatoreStatoPartita valutatore;
    private final SelettoreEventi selettoreEventi;
    private final List<StatoPartita> statiDisponibili;

    public InizializzatorePartita(
        ValutatoreStatoPartita valutatore,
        SelettoreEventi selettoreEventi,
        List<StatoPartita> statiDisponibili
    ) {
        this.valutatore = Objects.requireNonNull(valutatore, "Il valutatore non può essere nullo.");
        this.selettoreEventi = Objects.requireNonNull(selettoreEventi, "Il selettore eventi non può essere nullo.");
        if (statiDisponibili == null || statiDisponibili.isEmpty()) {
            throw new IllegalArgumentException("Gli stati disponibili non possono essere nulli o vuoti.");
        }
        this.statiDisponibili = List.copyOf(statiDisponibili);
    }

    public GestoreStatoPartita creaPartita(
        DatiSalvataggioPartita dati,
        List<EventoEseguibile> mazzoEventi
    ) {
        Objects.requireNonNull(dati, "I dati della partita non possono essere nulli.");
        Objects.requireNonNull(dati.turno(), "I dati del turno non possono essere nulli.");
        if (dati.metriche() == null || dati.metriche().isEmpty()) {
            throw new IllegalArgumentException("Le metriche non possono essere nulle o vuote.");
        }

        List<MetricaModificabile> metriche = dati.metriche().stream()
            .map(this::creaMetrica)
            .toList();

        PartitaBase partita = new PartitaBase(
            new StatisticheAziendaBase(metriche),
            mazzoEventi,
            new Turno(dati.turno().corrente(), dati.turno().massimo()),
            selettoreEventi,
            trovaEvento(dati.titoloEventoCorrente(), mazzoEventi),
            risolviStato(dati.codiceStato())
        );
        return new GestoreStatoPartita(partita, valutatore);
    }

    private MetricaModificabile creaMetrica(DatiMetrica dati) {
        return new MetricaBase(
            dati.nome(),
            dati.valore(),
            dati.valoreMinimo(),
            dati.valoreMassimo(),
            dati.criticaAlMassimo()
        );
    }

    private EventoEseguibile trovaEvento(String titolo, List<EventoEseguibile> mazzoEventi) {
        if (titolo == null || titolo.isBlank() || mazzoEventi == null) {
            return null;
        }
        return mazzoEventi.stream()
            .filter(evento -> evento.getTitolo().equals(titolo))
            .findFirst()
            .orElse(null);
    }

    private StatoPartita risolviStato(String codiceStato) {
        return statiDisponibili.stream()
            .filter(stato -> stato.getCodice().equalsIgnoreCase(codiceStato))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Stato partita non riconosciuto: " + codiceStato));
    }
}
