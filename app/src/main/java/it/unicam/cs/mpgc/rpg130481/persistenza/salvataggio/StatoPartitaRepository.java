package it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio;

public interface StatoPartitaRepository extends SorgenteStatoPartita {
    void salva(DatiSalvataggioPartita dati);
}
