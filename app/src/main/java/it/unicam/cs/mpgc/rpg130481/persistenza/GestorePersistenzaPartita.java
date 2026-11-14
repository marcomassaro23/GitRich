package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.partita.StatoGiocoDto;

import java.util.List;

public interface GestorePersistenzaPartita {
    boolean hasSalvataggioRiprendibile();
    StatoGiocoDto caricaStatoIniziale();
    StatoGiocoDto caricaStatoSalvato();
    List<EventoEseguibile> caricaMazzoEventi();
    void salvaInBackground(StatoGiocoDto stato);
    void arrestaServizi();
}
