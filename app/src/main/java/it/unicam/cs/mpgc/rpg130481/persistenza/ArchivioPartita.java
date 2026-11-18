package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;

public interface ArchivioPartita {
    boolean esistePartitaRiprendibile();
    Partita creaNuovaPartita();
    Partita riprendiPartitaSalvata();
    void salvaPartita(Partita partita);
}
