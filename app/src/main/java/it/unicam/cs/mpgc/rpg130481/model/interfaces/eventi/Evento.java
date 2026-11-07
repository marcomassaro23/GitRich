package it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi;

import it.unicam.cs.mpgc.rpg130481.model.core.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaLeggibili;

/**
 * Rappresenta un evento nel gioco di ruolo. Un evento può avere un testo descrittivo e una lista di opzioni tra cui il giocatore può scegliere.
 * Ogni opzione può avere delle modifiche alle metriche del giocatore.
 */
public interface Evento extends EventoLeggibile {
    /**
     * Verifica se l'evento è attivabile in base alle statistiche dell'azienda.
     *
     * @param statistiche le statistiche dell'azienda
     * @return true se l'evento è attivabile, false altrimenti
     */
    boolean isAttivabile(StatisticheAziendaLeggibili statistiche);

    /**
     * Esegue l'opzione selezionata dall'utente, applicando le modifiche alle statistiche dell'azienda.
     *
     * @param indiceOpzione l'indice dell'opzione selezionata
     * @param statistiche le statistiche dell'azienda
     */
    void eseguiOpzione(int indiceOpzione, StatisticheAzienda statistiche);
}
