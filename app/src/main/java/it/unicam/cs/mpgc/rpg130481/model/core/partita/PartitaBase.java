package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.selettori.SelettoreEventiDeterministico;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoInCorso;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori.SelettoreEventi;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import java.util.List;

public class PartitaBase {

    private final StatisticheAziendaModificabili statistiche;
    private final List<EventoEseguibile> mazzoEventi;
    private final SelettoreEventi selettoreEventi;
    private TurnoBase turno;
    private EventoEseguibile eventoCorrente;
    private StatoPartita stato;

    public PartitaBase(
        StatisticheAziendaModificabili statistiche,
        List<EventoEseguibile> mazzoEventi,
        TurnoBase turno,
        SelettoreEventi selettoreEventi
    ) {
        if (statistiche == null || mazzoEventi == null || mazzoEventi.isEmpty()) {
            throw new IllegalArgumentException("Statistiche e mazzo eventi non possono essere nulli o vuoti.");
        }
        if (turno == null || selettoreEventi == null) {
            throw new IllegalArgumentException("Turno e SelettoreEventi non possono essere nulli.");
        }

        this.statistiche = statistiche;
        this.mazzoEventi = List.copyOf(mazzoEventi);
        this.turno = turno;
        this.selettoreEventi = selettoreEventi;
        this.stato = new StatoInCorso();
        aggiornaEventoCorrente();
    }

    public PartitaBase(
        StatisticheAziendaModificabili statistiche,
        List<EventoEseguibile> mazzoEventi,
        int turniMassimi,
        SelettoreEventi selettoreEventi
    ) {
        this(statistiche, mazzoEventi, new TurnoBase(1, turniMassimi), selettoreEventi);
    }

    public PartitaBase(
        StatisticheAziendaModificabili statistiche,
        List<EventoEseguibile> mazzoEventi,
        int turniMassimi
    ) {
        this(statistiche, mazzoEventi, turniMassimi, new SelettoreEventiDeterministico());
    }

    public void eseguiScelta(int indiceOpzione) {
        if (eventoCorrente != null) {
            eventoCorrente.eseguiOpzione(indiceOpzione, statistiche);
        }
    }

    public void avanzaTurno() {
        if (!turno.isUltimoTurno()) {
            this.turno = turno.successivo();
            aggiornaEventoCorrente();
        }
    }

    private void aggiornaEventoCorrente() {
        this.eventoCorrente = selettoreEventi.selezionaProssimo(mazzoEventi, statistiche, turno);
    }

    public void setStato(StatoPartita nuovoStato) {
        if (nuovoStato == null) {
            throw new IllegalArgumentException("Lo stato della partita non può essere nullo.");
        }
        this.stato = nuovoStato;
    }

    public StatisticheAziendaModificabili getStatistiche() {
        return statistiche;
    }

    public TurnoBase getTurno() {
        return turno;
    }

    public EventoEseguibile getEventoCorrente() {
        return eventoCorrente;
    }

    public boolean isTerminata() {
        return stato.isTerminata();
    }

    public StatoGiocoDto toDto() {
        return new StatoGiocoDto(turno, statistiche, eventoCorrente, stato, stato.isTerminata());
    }
}
