package it.unicam.cs.mpgc.rpg130481.persistenza;

public interface EsecutoreSalvataggio {
    void accodaSalvataggio(Runnable operazioneSalvataggio);
    void arrestaEAttendi();
}
