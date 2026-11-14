package it.unicam.cs.mpgc.rpg130481.repository.interfaces;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import java.util.List;

@FunctionalInterface
public interface EventoRepository {
    List<EventoEseguibile> carica();
}
