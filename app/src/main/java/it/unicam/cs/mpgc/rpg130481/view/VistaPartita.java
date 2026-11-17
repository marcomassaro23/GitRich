package it.unicam.cs.mpgc.rpg130481.view;

import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;

public interface VistaPartita {
    void mostraMenu(boolean salvataggioDisponibile);
    void mostraSchermataGioco(StatoGiocoDto stato);
    void mostraFinePartita(StatoGiocoDto statoFinale);
    void mostraMessaggio(String messaggio);
}
