package it.unicam.cs.mpgc.rpg130481.model.eventi;

import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAziendaModificabili;

public interface EventoEseguibile extends Evento {
    boolean isAttivabileCon(StatisticheAzienda statistiche);
    void eseguiOpzione(int indiceOpzione, StatisticheAziendaModificabili statistiche);
}
