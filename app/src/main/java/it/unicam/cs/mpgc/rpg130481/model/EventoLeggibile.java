package it.unicam.cs.mpgc.rpg130481.model;

import java.util.List;

public interface EventoLeggibile {
    String getTitolo();
    String getDescrizione();
    List<OpzioneEventoLeggibile> getOpzioniLeggibili();
}
