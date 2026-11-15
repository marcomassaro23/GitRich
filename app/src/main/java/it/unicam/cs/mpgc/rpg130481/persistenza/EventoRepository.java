package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import java.util.List;

@FunctionalInterface
public interface EventoRepository {
    List<Evento> carica();
}
