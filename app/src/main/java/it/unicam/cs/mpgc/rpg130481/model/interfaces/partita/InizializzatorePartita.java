package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import java.util.List;

public interface InizializzatorePartita {
    PartitaBase creaPartita(
        StatisticheAziendaModificabili statisticheIniziali,
        List<EventoEseguibile> mazzoEventi,
        int turniMassimi
    );
}
