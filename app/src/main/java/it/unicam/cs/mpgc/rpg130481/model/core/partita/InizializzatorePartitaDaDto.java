package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori.SelettoreEventi;

import java.util.List;

/**
 * La classe InizializzatorePartitaDaDto è responsabile della creazione di una nuova partita a partire dai dati forniti.
 * Fornisce un metodo per creare un'istanza di Partita utilizzando le statistiche iniziali, il mazzo di eventi e il numero massimo di turni.
 */
public class InizializzatorePartitaDaDto {
    /**
     * Crea una nuova partita utilizzando le statistiche iniziali, il mazzo di eventi e il numero massimo di turni specificati.
     *
     * @param statisticheIniziali le statistiche iniziali dell'azienda
     * @param mazzoEventi il mazzo di eventi disponibili per la partita
     * @param turniMassimi il numero massimo di turni per la partita
     * @return un'istanza di Partita creata con i dati forniti
     */
    public PartitaBase creaPartita(StatisticheAziendaModificabili statisticheIniziali, List<EventoEseguibile> mazzoEventi, int turniMassimi) {
        return new PartitaBase(statisticheIniziali, mazzoEventi, turniMassimi);
    }

    /**
     * Crea una nuova partita utilizzando le statistiche iniziali, il mazzo di eventi, il numero massimo di turni e un selettore di eventi specificato.
     *
     * @param statisticheIniziali le statistiche iniziali dell'azienda
     * @param mazzoEventi il mazzo di eventi disponibili per la partita
     * @param turniMassimi il numero massimo di turni per la partita
     * @param selettoreEventi il selettore di eventi da utilizzare per determinare l'evento corrente
     * @return un'istanza di Partita creata con i dati forniti
     */
    public PartitaBase creaPartita(StatisticheAziendaModificabili statisticheIniziali, List<EventoEseguibile> mazzoEventi, int turniMassimi, SelettoreEventi selettoreEventi) {
        return new PartitaBase(statisticheIniziali, mazzoEventi, turniMassimi, selettoreEventi);
    }
}
