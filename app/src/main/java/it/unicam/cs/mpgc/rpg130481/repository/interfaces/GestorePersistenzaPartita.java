package it.unicam.cs.mpgc.rpg130481.repository.interfaces;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import java.util.List;

public interface GestorePersistenzaPartita {
    boolean hasSalvataggioRiprendibile();
    StatoGiocoDto caricaStatoIniziale();
    StatoGiocoDto caricaStatoSalvato();
    List<EventoEseguibile> caricaMazzoEventi();
    void salvaInBackground(StatoGiocoDto stato);
    void arrestaServizi();
}
