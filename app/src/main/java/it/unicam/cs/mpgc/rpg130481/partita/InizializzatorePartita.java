package it.unicam.cs.mpgc.rpg130481.partita;

import it.unicam.cs.mpgc.rpg130481.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.metriche.MetricaModificabile;
import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.partita.regole.ValutatoreStatoPartita;
import it.unicam.cs.mpgc.rpg130481.partita.selettori.SelettoreEventi;
import it.unicam.cs.mpgc.rpg130481.partita.stati.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.DatiSalvataggioPartita.DatiMetrica;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InizializzatorePartita {

    private static final int TURNI_MASSIMI_DEFAULT = 24;

    private final ValutatoreStatoPartita valutatore;
    private final SelettoreEventi selettoreEventi;
    private final StatoPartita statoDefault;
    private final List<StatoPartita> statiDisponibili;

    public InizializzatorePartita(
        ValutatoreStatoPartita valutatore,
        SelettoreEventi selettoreEventi,
        StatoPartita statoDefault,
        List<RegolaStato> regole
    ) {
        this.valutatore = Objects.requireNonNull(valutatore, "Il valutatore non può essere nullo.");
        this.selettoreEventi = Objects.requireNonNull(selettoreEventi, "Il selettore eventi non può essere nullo.");
        this.statoDefault = Objects.requireNonNull(statoDefault, "Lo stato di default non può essere nullo.");

        List<StatoPartita> stati = new ArrayList<>();
        stati.add(statoDefault);
        if (regole != null) {
            regole.stream()
                .filter(Objects::nonNull)
                .map(RegolaStato::getStatoRisultante)
                .filter(Objects::nonNull)
                .forEach(stati::add);
        }
        this.statiDisponibili = List.copyOf(stati);
    }

    public GestoreStatoPartita creaNuovaPartita(
        DatiSalvataggioPartita datiIniziali,
        List<EventoEseguibile> mazzoEventi
    ) {
        Objects.requireNonNull(datiIniziali, "I dati iniziali non possono essere nulli.");
        int turniMassimi = datiIniziali.turno() != null
            ? datiIniziali.turno().massimo()
            : TURNI_MASSIMI_DEFAULT;

        PartitaBase partita = new PartitaBase(
            creaStatistiche(datiIniziali.metriche()),
            mazzoEventi,
            new Turno(1, turniMassimi),
            selettoreEventi,
            null,
            statoDefault
        );
        return new GestoreStatoPartita(partita, valutatore);
    }

    public GestoreStatoPartita ripristinaPartita(
        DatiSalvataggioPartita datiSalvati,
        List<EventoEseguibile> mazzoEventi
    ) {
        Objects.requireNonNull(datiSalvati, "I dati salvati non possono essere nulli.");
        Turno turnoSalvato = datiSalvati.turno() != null
            ? new Turno(datiSalvati.turno().corrente(), datiSalvati.turno().massimo())
            : new Turno(1, TURNI_MASSIMI_DEFAULT);

        EventoEseguibile eventoRipristinato = trovaEventoNelMazzo(datiSalvati.titoloEventoCorrente(), mazzoEventi);
        StatoPartita statoRipristinato = risolviStato(datiSalvati.codiceStato());

        PartitaBase partita = new PartitaBase(
            creaStatistiche(datiSalvati.metriche()),
            mazzoEventi,
            turnoSalvato,
            selettoreEventi,
            eventoRipristinato,
            statoRipristinato
        );
        return new GestoreStatoPartita(partita, valutatore);
    }

    private EventoEseguibile trovaEventoNelMazzo(String titoloEvento, List<EventoEseguibile> mazzoEventi) {
        if (titoloEvento == null || titoloEvento.isBlank() || mazzoEventi == null) {
            return null;
        }
        return mazzoEventi.stream()
            .filter(evento -> evento.getTitolo().equals(titoloEvento))
            .findFirst()
            .orElse(null);
    }

    private StatoPartita risolviStato(String codiceStato) {
        return statiDisponibili.stream()
            .filter(s -> s.getCodice().equalsIgnoreCase(codiceStato))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Stato partita non riconosciuto: " + codiceStato));
    }

    private StatisticheAziendaModificabili creaStatistiche(List<DatiMetrica> datiMetriche) {
        if (datiMetriche == null || datiMetriche.isEmpty()) {
            throw new IllegalArgumentException("La lista delle metriche salvate non può essere nulla o vuota.");
        }
        List<MetricaModificabile> metriche = datiMetriche.stream().map(this::creaMetrica).toList();
        return new StatisticheAziendaBase(metriche);
    }

    private MetricaModificabile creaMetrica(DatiMetrica dati) {
        int min = dati.valoreMassimo() == 0 && dati.valoreMinimo() == 0 ? 0 : dati.valoreMinimo();
        int max = dati.valoreMassimo() == 0 && dati.valoreMinimo() == 0 ? 100 : dati.valoreMassimo();
        return new MetricaBase(dati.nome(), dati.valore(), min, max, dati.criticaAlMassimo());
    }
}
