package it.unicam.cs.mpgc.rpg130481.persistenza;

import java.util.List;

import it.unicam.cs.mpgc.rpg130481.eventi.EventoEseguibile;

@FunctionalInterface
public interface EventoRepository {
    List<EventoEseguibile> carica();
}
