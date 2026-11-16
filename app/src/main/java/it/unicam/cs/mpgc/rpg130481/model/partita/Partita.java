package it.unicam.cs.mpgc.rpg130481.model.partita;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;

public interface Partita {
    StatisticheAzienda getStatistiche();
    Turno getTurno();
    Evento getEventoCorrente();
    StatoPartita getStato();
    boolean isTerminata();
    void giocaTurnoConScelta(int indiceOpzione);
}
