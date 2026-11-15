package it.unicam.cs.mpgc.rpg130481.model.partita;

import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.partita.selettori.SelettoreEventi;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoInCorso;
import it.unicam.cs.mpgc.rpg130481.model.partita.stati.StatoPartita;
import java.util.List;

public class PartitaBase implements PartitaModificabile {

    private final StatisticheAziendaModificabili statistiche;
    private final List<EventoEseguibile> mazzoEventi;
    private final SelettoreEventi selettoreEventi;
    private Turno turno;
    private EventoEseguibile eventoCorrente;
    private StatoPartita stato;

    public PartitaBase(
        StatisticheAziendaModificabili statistiche,
        List<EventoEseguibile> mazzoEventi,
        Turno turno,
        SelettoreEventi selettoreEventi,
        EventoEseguibile eventoIniziale,
        StatoPartita statoIniziale
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
        this.stato = statoIniziale != null ? statoIniziale : new StatoInCorso();
        this.eventoCorrente = eventoIniziale != null ? eventoIniziale : selezionaProssimoEvento();
    }

    public PartitaBase(
        StatisticheAziendaModificabili statistiche,
        List<EventoEseguibile> mazzoEventi,
        Turno turno,
        SelettoreEventi selettoreEventi
    ) {
        this(statistiche, mazzoEventi, turno, selettoreEventi, null, new StatoInCorso());
    }

    @Override
    public void applicaScelta(int indiceOpzione) {
        if (eventoCorrente != null) {
            eventoCorrente.eseguiOpzione(indiceOpzione, statistiche);
        }
    }

    @Override
    public void avanzaTurno() {
        if (!turno.isUltimo()) {
            this.turno = turno.successivo();
            this.eventoCorrente = selezionaProssimoEvento();
        }
    }

    @Override
    public void setStato(StatoPartita nuovoStato) {
        if (nuovoStato == null) {
            throw new IllegalArgumentException("Lo stato della partita non può essere nullo.");
        }
        this.stato = nuovoStato;
    }

    @Override
    public StatisticheAzienda getStatistiche() {
        return statistiche;
    }

    @Override
    public Turno getTurno() {
        return turno;
    }

    @Override
    public EventoEseguibile getEventoCorrente() {
        return eventoCorrente;
    }

    @Override
    public boolean isTerminata() {
        return stato.isTerminata();
    }

    @Override
    public StatoPartita getStato() {
        return stato;
    }

    private EventoEseguibile selezionaProssimoEvento() {
        return selettoreEventi.selezionaProssimo(mazzoEventi, statistiche, turno);
    }
}
