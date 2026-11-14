package it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;

public interface EventoEseguibile extends Evento {
    boolean isAttivabile(StatisticheAzienda statistiche);
    void eseguiOpzione(int indiceOpzione, StatisticheAziendaModificabili statistiche);
}
