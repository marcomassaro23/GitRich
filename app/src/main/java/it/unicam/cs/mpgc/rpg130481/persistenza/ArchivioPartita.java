package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;

public interface ArchivioPartita extends AutoCloseable {
    boolean esistePartitaRiprendibile();
    Partita creaNuovaPartita();
    Partita riprendiPartitaSalvata();
    void salvaStato(StatoGiocoDto stato);

    @Override
    default void close() throws Exception {}
}
