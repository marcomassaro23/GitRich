package it.unicam.cs.mpgc.rpg130481.model.eventi;

import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import java.util.List;

public interface Evento {
    String getTitolo();
    String getDescrizione();
    List<OpzioneEvento> getOpzioni();
    void eseguiOpzione(int indiceOpzione, StatisticheAzienda statistiche);
}
