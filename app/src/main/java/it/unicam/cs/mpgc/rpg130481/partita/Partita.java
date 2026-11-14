package it.unicam.cs.mpgc.rpg130481.partita;

import it.unicam.cs.mpgc.rpg130481.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.partita.stati.StatoPartita;

public interface Partita {
    StatisticheAzienda getStatistiche();
    Turno getTurno();
    Evento getEventoCorrente();
    StatoPartita getStato();
    boolean isTerminata();
}
