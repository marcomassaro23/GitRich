package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.core.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.regole.ValutatoreStatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.MetricaModificabile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.InizializzatorePartita;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori.SelettoreEventi;
import java.util.List;
import java.util.Objects;

public class InizializzatorePartitaDaDto implements InizializzatorePartita {

    private static final int TURNI_MASSIMI_DEFAULT = 24;
    private final ValutatoreStatoPartita valutatore;
    private final SelettoreEventi selettoreEventi;

    public InizializzatorePartitaDaDto(ValutatoreStatoPartita valutatore, SelettoreEventi selettoreEventi) {
        this.valutatore = Objects.requireNonNull(valutatore, "Il valutatore non può essere nullo.");
        this.selettoreEventi = Objects.requireNonNull(selettoreEventi, "Il selettore eventi non può essere nullo.");
    }

    @Override
    public GestoreStatoPartita creaNuovaPartita(StatoGiocoDto statoIniziale, List<EventoEseguibile> mazzoEventi) {
        Objects.requireNonNull(statoIniziale, "Lo stato iniziale non può essere nullo.");
        int turniMassimi = statoIniziale.turno() != null
            ? statoIniziale.turno().massimo()
            : TURNI_MASSIMI_DEFAULT;

        PartitaBase partita = new PartitaBase(
            clonaStatisticheModificabili(statoIniziale.statistiche()),
            mazzoEventi,
            new Turno(1, turniMassimi),
            selettoreEventi
        );
        return new GestoreStatoPartita(partita, valutatore);
    }

    @Override
    public GestoreStatoPartita ripristinaPartita(StatoGiocoDto statoSalvato, List<EventoEseguibile> mazzoEventi) {
        Objects.requireNonNull(statoSalvato, "Lo stato salvato non può essere nullo.");
        Turno turnoSalvato = statoSalvato.turno() != null
            ? statoSalvato.turno()
            : new Turno(1, TURNI_MASSIMI_DEFAULT);

        EventoEseguibile eventoRipristinato = trovaEventoNelMazzo(statoSalvato.eventoCorrente(), mazzoEventi);

        PartitaBase partita = new PartitaBase(
            clonaStatisticheModificabili(statoSalvato.statistiche()),
            mazzoEventi,
            turnoSalvato,
            selettoreEventi,
            eventoRipristinato,
            statoSalvato.statoPartita()
        );
        return new GestoreStatoPartita(partita, valutatore);
    }

    private EventoEseguibile trovaEventoNelMazzo(Evento eventoSalvato, List<EventoEseguibile> mazzoEventi) {
        if (eventoSalvato == null || mazzoEventi == null) {
            return null;
        }
        return mazzoEventi.stream()
            .filter(evento -> evento.getTitolo().equals(eventoSalvato.getTitolo()))
            .findFirst()
            .orElse(null);
    }

    private StatisticheAziendaModificabili clonaStatisticheModificabili(StatisticheAzienda sorgente) {
        List<MetricaModificabile> metricheClonate = sorgente.getMetriche().stream().map(this::clonaMetrica).toList();
        return new StatisticheAziendaBase(metricheClonate);
    }

    private MetricaModificabile clonaMetrica(Metrica sorgente) {
        return new MetricaBase(
            sorgente.getNome(),
            sorgente.getValore(),
            sorgente.getValoreMinimo(),
            sorgente.getValoreMassimo(),
            sorgente.isCriticaAlMassimo()
        );
    }
}
