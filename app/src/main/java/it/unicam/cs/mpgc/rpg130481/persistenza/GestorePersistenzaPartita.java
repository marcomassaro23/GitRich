package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.controller.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoEseguibile;

import java.util.List;

public interface GestorePersistenzaPartita {
    boolean hasSalvataggioRiprendibile();
    DatiSalvataggioPartita caricaStatoIniziale();
    DatiSalvataggioPartita caricaStatoSalvato();
    List<EventoEseguibile> caricaMazzoEventi();
    void salvaInBackground(StatoGiocoDto stato);
    void arrestaServizi();
}
