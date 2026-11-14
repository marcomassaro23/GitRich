package it.unicam.cs.mpgc.rpg130481.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.partita.Turno;

import java.util.List;

public interface SelettoreEventi {
    EventoEseguibile selezionaProssimo(
        List<EventoEseguibile> mazzoEventi,
        StatisticheAzienda statistiche,
        Turno turno
    );
}
