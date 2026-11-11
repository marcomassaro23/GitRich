package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaLeggibili;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.Turno;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;

import java.util.List;

/**
 * Interfaccia che rappresenta un selettore di eventi per una partita.
 * Fornisce un metodo per selezionare il prossimo evento da un mazzo di eventi in base alle statistiche dell'azienda e al turno corrente.
 */
public interface SelettoreEventi {
    /**
     * Seleziona il prossimo evento da un mazzo di eventi in base alle statistiche dell'azienda e al turno corrente.
     * Se non ci sono eventi attivabili, seleziona un evento basato sul numero del turno corrente.
     *
     * @param mazzoEventi il mazzo di eventi disponibili
     * @param statistiche le statistiche dell'azienda
     * @param turno il turno corrente della partita
     * @return l'evento selezionato
     * @throws IllegalArgumentException se i parametri sono nulli o vuoti
     */
    Evento selezionaProssimo(List<Evento> mazzoEventi, StatisticheAziendaLeggibili statistiche, Turno turno);
}
