package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoInCorso;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori.SelettoreEventi;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import java.util.List;
import java.util.Optional;

public class PartitaBase implements Partita {

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
        Evento eventoSalvato,
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
        this.eventoCorrente = ripristinaOSelezionaEvento(eventoSalvato);
    }

    public PartitaBase(
        StatisticheAziendaModificabili statistiche,
        List<EventoEseguibile> mazzoEventi,
        Turno turno,
        SelettoreEventi selettoreEventi
    ) {
        this(statistiche, mazzoEventi, turno, selettoreEventi, null, new StatoInCorso());
    }

    public void applicaScelta(int indiceOpzione) {
        if (eventoCorrente != null) {
            eventoCorrente.eseguiOpzione(indiceOpzione, statistiche);
        }
    }

    public void avanzaTurno() {
        if (!turno.isUltimo()) {
            this.turno = turno.successivo();
            this.eventoCorrente = selezionaProssimoEvento();
        }
    }

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

    public StatoGiocoDto toDto() {
        return new StatoGiocoDto(turno, statistiche, eventoCorrente, stato, stato.isTerminata());
    }

    private EventoEseguibile ripristinaOSelezionaEvento(Evento eventoSalvato) {
        if (eventoSalvato == null) {
            return selezionaProssimoEvento();
        }
        return cercaEventoPerTitolo(eventoSalvato.getTitolo()).orElseGet(this::selezionaProssimoEvento);
    }

    private Optional<EventoEseguibile> cercaEventoPerTitolo(String titolo) {
        return mazzoEventi.stream().filter(evento -> evento.getTitolo().equals(titolo)).findFirst();
    }

    private EventoEseguibile selezionaProssimoEvento() {
        return selettoreEventi.selezionaProssimo(mazzoEventi, statistiche, turno);
    }
}
