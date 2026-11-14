package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.core.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.regole.ValutatoreStatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.MetricaModificabile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.InizializzatorePartita;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori.SelettoreEventi;
import java.util.List;

public class InizializzatorePartitaDaDto implements InizializzatorePartita {

    private static final int TURNI_MASSIMI_DEFAULT = 24;
    private final ValutatoreStatoPartita valutatore;
    private final SelettoreEventi selettoreEventi;

    public InizializzatorePartitaDaDto(ValutatoreStatoPartita valutatore, SelettoreEventi selettoreEventi) {
        if (valutatore == null || selettoreEventi == null) {
            throw new IllegalArgumentException("Valutatore e SelettoreEventi non possono essere nulli.");
        }
        this.valutatore = valutatore;
        this.selettoreEventi = selettoreEventi;
    }

    @Override
    public GestoreStatoPartita creaNuovaPartita(StatoGiocoDto statoIniziale, List<EventoEseguibile> mazzoEventi) {
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
        Turno turnoSalvato = statoSalvato.turno() != null
            ? statoSalvato.turno()
            : new Turno(1, TURNI_MASSIMI_DEFAULT);

        PartitaBase partita = new PartitaBase(
            clonaStatisticheModificabili(statoSalvato.statistiche()),
            mazzoEventi,
            turnoSalvato,
            selettoreEventi,
            statoSalvato.eventoCorrente(),
            statoSalvato.statoPartita()
        );
        return new GestoreStatoPartita(partita, valutatore);
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
            sorgente.getValoreMassimo()
        );
    }
}
