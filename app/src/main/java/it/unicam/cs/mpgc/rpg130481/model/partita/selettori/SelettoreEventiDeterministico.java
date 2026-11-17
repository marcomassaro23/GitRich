package it.unicam.cs.mpgc.rpg130481.model.partita.selettori;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import java.util.List;

public class SelettoreEventiDeterministico implements SelettoreEventi {

    @Override
    public Evento selezionaProssimo(List<Evento> mazzoEventi, Turno turno) {
        if (mazzoEventi == null || mazzoEventi.isEmpty() || turno == null) {
            throw new IllegalArgumentException(
                    "I parametri per la selezione dell'evento non possono essere nulli o vuoti.");
        }
        return mazzoEventi.get((turno.corrente() - 1) % mazzoEventi.size());
    }
}
