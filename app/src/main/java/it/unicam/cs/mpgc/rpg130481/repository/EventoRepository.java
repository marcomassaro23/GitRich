package it.unicam.cs.mpgc.rpg130481.repository;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import java.util.List;

/**
 * Interfaccia che rappresenta un repository per gli eventi.
 * Contiene un metodo per caricare e restituire tutti gli eventi disponibili.
 */
public interface EventoRepository {
    /**
     * Carica e restituisce tutti gli eventi disponibili.
     *
     * @return Lista di oggetti Evento pronti all'uso
     */
    List<Evento> caricaTuttiGliEventi();
}
