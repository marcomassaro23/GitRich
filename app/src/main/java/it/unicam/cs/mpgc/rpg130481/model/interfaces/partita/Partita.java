package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.Turno;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;

public interface Partita {
    StatisticheAzienda getStatistiche();
    Turno getTurno();
    Evento getEventoCorrente();
    boolean isTerminata();
}
