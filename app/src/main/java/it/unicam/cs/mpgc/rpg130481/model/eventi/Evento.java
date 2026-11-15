package it.unicam.cs.mpgc.rpg130481.model.eventi;

import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAziendaBase;
import java.util.List;

public interface Evento {
    String getTitolo();
    String getDescrizione();
    List<OpzioneEvento> getOpzioni();
    boolean isAttivabileCon(StatisticheAzienda statistiche);
    void eseguiOpzione(int indiceOpzione, StatisticheAziendaBase statistiche);
}
