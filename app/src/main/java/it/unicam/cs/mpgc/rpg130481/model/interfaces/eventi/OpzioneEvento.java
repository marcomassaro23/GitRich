package it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi;

import it.unicam.cs.mpgc.rpg130481.model.core.eventi.VariazioneMetrica;
import java.util.List;

public interface OpzioneEvento {
    String getTesto();
    List<VariazioneMetrica> getModifiche();
}
