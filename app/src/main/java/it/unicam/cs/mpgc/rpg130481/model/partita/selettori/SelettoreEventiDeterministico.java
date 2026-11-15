package it.unicam.cs.mpgc.rpg130481.model.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;

import java.util.List;

public class SelettoreEventiDeterministico implements SelettoreEventi {

    @Override
    public EventoEseguibile selezionaProssimo(
        List<EventoEseguibile> mazzoEventi,
        StatisticheAzienda statistiche,
        Turno turno
    ) {
        if (mazzoEventi == null || mazzoEventi.isEmpty() || statistiche == null || turno == null) {
            throw new IllegalArgumentException(
                "I parametri per la selezione dell'evento non possono essere nulli o vuoti."
            );
        }

        List<EventoEseguibile> eventiAttivabili = mazzoEventi
            .stream()
            .filter(evento -> evento.isAttivabileCon(statistiche))
            .toList();

        List<EventoEseguibile> candidati = eventiAttivabili.isEmpty() ? mazzoEventi : eventiAttivabili;
        int indiceEvento = calcolaIndiceCiclico(turno, candidati.size());
        return candidati.get(indiceEvento);
    }

    private int calcolaIndiceCiclico(Turno turno, int dimensioneMazzo) {
        return (turno.corrente() - 1) % dimensioneMazzo;
    }
}
