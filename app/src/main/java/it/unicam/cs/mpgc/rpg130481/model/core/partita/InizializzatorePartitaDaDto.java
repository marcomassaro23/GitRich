package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori.SelettoreEventi;
import java.util.List;

public class InizializzatorePartitaDaDto {

    public PartitaBase creaPartita(
        StatisticheAziendaModificabili statisticheIniziali,
        List<EventoEseguibile> mazzoEventi,
        int turniMassimi
    ) {
        return new PartitaBase(statisticheIniziali, mazzoEventi, turniMassimi);
    }

    public PartitaBase creaPartita(
        StatisticheAziendaModificabili statisticheIniziali,
        List<EventoEseguibile> mazzoEventi,
        int turniMassimi,
        SelettoreEventi selettoreEventi
    ) {
        return new PartitaBase(statisticheIniziali, mazzoEventi, turniMassimi, selettoreEventi);
    }
}
