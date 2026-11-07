package it.unicam.cs.mpgc.rpg130481.repository;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.repository.interfaces.Caricabile;

import java.util.List;

/**
 * Interfaccia che rappresenta un repository per la gestione degli eventi.
 * Estende l'interfaccia Caricabile per consentire il caricamento di una lista di eventi.
 */
public interface EventoRepository extends Caricabile<List<Evento>> {}
