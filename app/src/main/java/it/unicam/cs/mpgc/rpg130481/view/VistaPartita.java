package it.unicam.cs.mpgc.rpg130481.view;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;

public interface VistaPartita {
    void mostraMenu(boolean salvataggioDisponibile);
    void mostraSchermataGioco(Partita partita);
    void mostraFinePartita(Partita partita);
    void mostraMessaggio(String messaggio);
}
