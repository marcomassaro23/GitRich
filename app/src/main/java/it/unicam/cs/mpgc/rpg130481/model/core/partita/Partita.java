package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoInCorso;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stato.StatoPartita;

import java.util.List;

/**
 * Rappresenta una partita del gioco, gestendo lo stato della partita, le statistiche dell'azienda e il mazzo di eventi.
 * Fornisce metodi per eseguire scelte, avanzare i turni e ottenere informazioni sullo stato della partita.
 */
public class Partita {
    private final StatisticheAzienda statistiche;
    private final List<Evento> mazzoEventi;
    private Turno turno;
    private Evento eventoCorrente;
    private StatoPartita stato;

    /**
     * Costruisce una nuova partita con le statistiche dell'azienda, il mazzo di eventi e il numero massimo di turni.
     *
     * @param statistiche le statistiche dell'azienda
     * @param mazzoEventi il mazzo di eventi
     * @param turniMassimi il numero massimo di turni
     * @throws IllegalArgumentException se le statistiche o il mazzo eventi sono null o vuoti
     */
    public Partita(StatisticheAzienda statistiche, List<Evento> mazzoEventi, int turniMassimi) {
        if (statistiche == null || mazzoEventi == null || mazzoEventi.isEmpty()) {
            throw new IllegalArgumentException("Statistiche e mazzo eventi devono essere validi e non vuoti.");
        }
        this.statistiche = statistiche;
        this.mazzoEventi = List.copyOf(mazzoEventi);
        this.turno = new Turno(1, turniMassimi);
        this.stato = new StatoInCorso();
        selezionaEventoCorrente();
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
            selezionaEventoCorrente();
        }
    }

    /**
     * Seleziona l'evento corrente dalla lista degli eventi disponibili in base alle statistiche dell'azienda.
     * Se non ci sono eventi attivabili, seleziona un evento casuale dal mazzo.
     */
    private void selezionaEventoCorrente() {
        this.eventoCorrente = mazzoEventi.stream()
                .filter(e -> e.isAttivabile(statistiche))
                .findFirst()
                .orElse(mazzoEventi.get((turno.numeroCorrente() - 1) % mazzoEventi.size()));
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

    public StatisticheAzienda getStatistiche() { return statistiche; }
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
