package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.TurnoBase;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;
import java.util.List;

public interface SelettoreEventi {
    EventoEseguibile selezionaProssimo(
        List<EventoEseguibile> mazzoEventi,
        StatisticheAzienda statistiche,
        TurnoBase turno
    );
}
