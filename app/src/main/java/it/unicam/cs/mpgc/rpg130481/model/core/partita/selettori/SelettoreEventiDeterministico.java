package it.unicam.cs.mpgc.rpg130481.model.core.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.TurnoBase;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.selettori.SelettoreEventi;
import java.util.List;

public class SelettoreEventiDeterministico implements SelettoreEventi {

    @Override
    public EventoEseguibile selezionaProssimo(
        List<EventoEseguibile> mazzoEventi,
        StatisticheAzienda statistiche,
        TurnoBase turno
    ) {
        if (mazzoEventi == null || mazzoEventi.isEmpty() || statistiche == null || turno == null) {
            throw new IllegalArgumentException(
                "I parametri per la selezione dell'evento non possono essere nulli o vuoti."
            );
        }

        List<EventoEseguibile> eventiAttivabili = mazzoEventi
            .stream()
            .filter(evento -> evento.isAttivabile(statistiche))
            .toList();

        List<EventoEseguibile> listaRiferimento = eventiAttivabili.isEmpty() ? mazzoEventi : eventiAttivabili;
        int indiceSelezionato = (turno.numeroCorrente() - 1) % listaRiferimento.size();
        return listaRiferimento.get(indiceSelezionato);
    }
}
