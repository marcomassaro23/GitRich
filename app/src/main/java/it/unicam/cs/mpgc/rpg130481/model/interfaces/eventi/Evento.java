package it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi;

import java.util.List;

public interface Evento {
    String getTitolo();
    String getDescrizione();
    List<OpzioneEvento> getOpzioni();
}
