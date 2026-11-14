package it.unicam.cs.mpgc.rpg130481.model.interfaces.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.GestoreStatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import java.util.List;

public interface InizializzatorePartita {
    GestoreStatoPartita creaNuovaPartita(StatoGiocoDto statoIniziale, List<EventoEseguibile> mazzoEventi);
    GestoreStatoPartita ripristinaPartita(StatoGiocoDto statoSalvato, List<EventoEseguibile> mazzoEventi);
}
