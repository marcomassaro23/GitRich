package it.unicam.cs.mpgc.rpg130481.model.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import java.util.List;

@FunctionalInterface
public interface SelettoreEventi {
    Evento selezionaProssimo(List<Evento> mazzoEventi, StatisticheAzienda statistiche, Turno turno);
}
