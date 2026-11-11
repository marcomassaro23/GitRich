package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.selettori.SelettoreEventiDeterministico;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoInCorso;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori.SelettoreEventi;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

import java.util.List;

/**
 * Rappresenta una partita del gioco, gestendo lo stato della partita, le statistiche dell'azienda e il mazzo di eventi.
 * Fornisce metodi per eseguire scelte, avanzare i turni e ottenere informazioni sullo stato della partita.
 */
public class Partita {
    private final StatisticheAziendaModificabili statistiche;
    private final List<Evento> mazzoEventi;
    private final SelettoreEventi selettoreEventi;
    private Turno turno;
    private Evento eventoCorrente;
    private StatoPartita stato;

    /**
     * Crea una partita specificando il turno iniziale e il selettore di eventi.
     *
     * @param statistiche     le statistiche dell'azienda
     * @param mazzoEventi     il mazzo di eventi disponibili
     * @param turno           il turno di partenza della partita
     * @param selettoreEventi il selettore di eventi
     * @throws IllegalArgumentException se le statistiche o il mazzo eventi sono null o vuoti
     */
    public Partita(StatisticheAziendaModificabili statistiche, List<Evento> mazzoEventi, Turno turno, SelettoreEventi selettoreEventi) {
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

    /**
     * Costruisce una nuova partita iniziando dal primo turno (Turno 1 di turniMassimi).
     */
    public Partita(StatisticheAziendaModificabili statistiche, List<Evento> mazzoEventi, int turniMassimi, SelettoreEventi selettoreEventi) {
        this(statistiche, mazzoEventi, new Turno(1, turniMassimi), selettoreEventi);
    }

    /**
     * Costruisce una nuova partita con le statistiche dell'azienda, il mazzo di eventi e il numero massimo di turni.
     * Utilizza un selettore di eventi deterministico per la selezione degli eventi.
     *
     * @param statistiche le statistiche dell'azienda
     * @param mazzoEventi il mazzo di eventi
     * @param turniMassimi il numero massimo di turni
     * @throws IllegalArgumentException se le statistiche o il mazzo eventi sono null o vuoti
     */
    public Partita(StatisticheAziendaModificabili statistiche, List<Evento> mazzoEventi, int turniMassimi) {
        this(statistiche, mazzoEventi, turniMassimi, new SelettoreEventiDeterministico());
    }

    /**
     * Esegue la scelta dell'opzione specificata per l'evento corrente.
     *
     * @param indiceOpzione l'indice dell'opzione da eseguire
     */
    public void eseguiScelta(int indiceOpzione) {
        if (eventoCorrente != null) {
            eventoCorrente.eseguiOpzione(indiceOpzione, statistiche);
        }
    }

    /**
     * Avanza al turno successivo della partita, selezionando un nuovo evento corrente.
     * Se il turno corrente è l'ultimo turno, non viene effettuato alcun avanzamento.
     */
    public void avanzaTurno() {
        if (!turno.isUltimoTurno()) {
            this.turno = turno.successivo();
            aggiornaEventoCorrente();
        }
    }

    /**
     * Seleziona l'evento corrente dalla lista degli eventi disponibili in base alle statistiche dell'azienda.
     * Se non ci sono eventi attivabili, seleziona un evento casuale dal mazzo.
     */
    private void aggiornaEventoCorrente() {
        this.eventoCorrente = selettoreEventi.selezionaProssimo(mazzoEventi, statistiche, turno);
    }

    /**
     * Imposta lo stato della partita al nuovo stato specificato.
     *
     * @param nuovoStato il nuovo stato della partita
     * @throws IllegalArgumentException se il nuovo stato è nullo
     */
    public void setStato(StatoPartita nuovoStato) {
        if (nuovoStato == null) {
            throw new IllegalArgumentException("Lo stato della partita non può essere nullo.");
        }
        this.stato = nuovoStato;
    }

    public StatisticheAziendaModificabili getStatistiche() { return statistiche; }
    public Turno getTurno() { return turno; }
    public Evento getEventoCorrente() { return eventoCorrente; }
    public boolean isTerminata() { return stato.isTerminata(); }

    /**
     * Converte lo stato della partita in un oggetto DTO (Data Transfer Object) per la visualizzazione.
     *
     * @return un oggetto StatoGiocoDto contenente le informazioni sullo stato della partita
     */
    public StatoGiocoDto toDto() {
        return new StatoGiocoDto(
                turno,
                statistiche,
                eventoCorrente,
                stato,
                stato.isTerminata()
        );
    }
}
