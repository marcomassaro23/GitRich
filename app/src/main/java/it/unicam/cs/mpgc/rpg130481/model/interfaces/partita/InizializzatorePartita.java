package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import java.util.List;

/**
 * Interfaccia che rappresenta un inizializzatore di partita.
 * Contiene un metodo per creare una nuova partita con le statistiche iniziali, il mazzo di eventi e il numero massimo di turni.
 */
public interface InizializzatorePartita {
    /**
     * Crea una nuova partita con le statistiche iniziali, il mazzo di eventi e il numero massimo di turni.
     *
     * @param statisticheIniziali le statistiche iniziali dell'azienda
     * @param mazzoEventi il mazzo di eventi della partita
     * @param turniMassimi il numero massimo di turni della partita
     * @return la nuova partita creata
     */
    Partita creaPartita(StatisticheAzienda statisticheIniziali, List<Evento> mazzoEventi, int turniMassimi);
}
