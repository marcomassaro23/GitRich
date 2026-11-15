package it.unicam.cs.mpgc.rpg130481.model.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;

import java.util.List;

public interface SelettoreEventi {
    EventoEseguibile selezionaProssimo(
        List<EventoEseguibile> mazzoEventi,
        StatisticheAzienda statistiche,
        Turno turno
    );
}
