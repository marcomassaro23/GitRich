package it.unicam.cs.mpgc.rpg130481.model.partita;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.partita.regole.RegolaBancarotta;
import it.unicam.cs.mpgc.rpg130481.model.partita.regole.RegolaVittoria;
import it.unicam.cs.mpgc.rpg130481.model.partita.regole.ValutatoreStatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.selettori.SelettoreEventi;
import it.unicam.cs.mpgc.rpg130481.model.partita.selettori.SelettoreEventiDeterministico;
import java.util.List;
import java.util.Objects;

public class PartitaBase implements Partita {

    private final StatisticheAzienda statistiche;
    private final List<Evento> mazzoEventi;
    private final SelettoreEventi selettoreEventi;
    private final ValutatoreStatoPartita valutatoreStato;
    private Turno turno;
    private Evento eventoCorrente;
    private StatoPartita stato;

    public PartitaBase(
            StatisticheAzienda statistiche,
            List<Evento> mazzoEventi,
            Turno turno,
            SelettoreEventi selettoreEventi,
            ValutatoreStatoPartita valutatoreStato,
            Evento eventoIniziale,
            StatoPartita statoIniziale) {
        this.statistiche = Objects.requireNonNull(statistiche, "Le statistiche non possono essere nulle.");
        if (mazzoEventi == null || mazzoEventi.isEmpty()) {
            throw new IllegalArgumentException("Il mazzo eventi non può essere nullo o vuoto.");
        }
        this.mazzoEventi = List.copyOf(mazzoEventi);
        this.turno = Objects.requireNonNull(turno, "Il turno non può essere nullo.");
        this.selettoreEventi = Objects.requireNonNull(selettoreEventi, "Il selettore eventi non può essere nullo.");
        this.valutatoreStato = Objects.requireNonNull(valutatoreStato, "Il valutatore stato non può essere nullo.");
        this.stato = statoIniziale != null ? statoIniziale : StatoPartita.IN_CORSO;
        this.eventoCorrente = eventoIniziale != null ? eventoIniziale : selezionaProssimoEvento();
    }

    public PartitaBase(
            StatisticheAzienda statistiche,
            List<Evento> mazzoEventi,
            Turno turno,
            Evento eventoIniziale,
            StatoPartita statoIniziale) {
        this(
                statistiche,
                mazzoEventi,
                turno,
                new SelettoreEventiDeterministico(),
                new ValutatoreStatoPartita(List.of(new RegolaBancarotta(), new RegolaVittoria())),
                eventoIniziale,
                statoIniziale);
    }

    public PartitaBase(
            StatisticheAzienda statistiche,
            List<Evento> mazzoEventi,
            Turno turno,
            SelettoreEventi selettoreEventi,
            ValutatoreStatoPartita valutatoreStato) {
        this(statistiche, mazzoEventi, turno, selettoreEventi, valutatoreStato, null, StatoPartita.IN_CORSO);
    }

    @Override
    public void giocaTurnoConScelta(int indiceOpzione) {
        if (isTerminata()) {
            throw new IllegalStateException("Impossibile avanzare: la partita è terminata.");
        }
        if (eventoCorrente != null) {
            eventoCorrente.eseguiOpzione(indiceOpzione, statistiche);
        }
        this.stato = valutatoreStato.determinaProssimoStato(this);
        if (!isTerminata() && !turno.isUltimo()) {
            this.turno = turno.successivo();
            this.eventoCorrente = selezionaProssimoEvento();
        }
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
    public Evento getEventoCorrente() {
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

    private Evento selezionaProssimoEvento() {
        return selettoreEventi.selezionaProssimo(mazzoEventi, turno);
    }
}
