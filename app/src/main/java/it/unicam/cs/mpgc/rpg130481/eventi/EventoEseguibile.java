package it.unicam.cs.mpgc.rpg130481.eventi;

import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAziendaModificabili;

public interface EventoEseguibile extends Evento {
    boolean isAttivabileCon(StatisticheAzienda statistiche);
    void eseguiOpzione(int indiceOpzione, StatisticheAziendaModificabili statistiche);
}
