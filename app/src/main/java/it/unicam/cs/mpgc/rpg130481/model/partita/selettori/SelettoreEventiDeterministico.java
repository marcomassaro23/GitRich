package it.unicam.cs.mpgc.rpg130481.model.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import java.util.List;

public class SelettoreEventiDeterministico implements SelettoreEventi {

    @Override
    public Evento selezionaProssimo(
            List<Evento> mazzoEventi,
            StatisticheAzienda statistiche,
            Turno turno) {
        if (mazzoEventi == null || mazzoEventi.isEmpty() || statistiche == null || turno == null) {
            throw new IllegalArgumentException(
                    "I parametri per la selezione dell'evento non possono essere nulli o vuoti.");
        }

        List<Evento> eventiAttivabili = mazzoEventi.stream()
                .filter(evento -> evento.isAttivabileCon(statistiche))
                .toList();

        List<Evento> candidati = eventiAttivabili.isEmpty() ? mazzoEventi : eventiAttivabili;
        int indiceEvento = (turno.corrente() - 1) % candidati.size();
        return candidati.get(indiceEvento);
    }
}
