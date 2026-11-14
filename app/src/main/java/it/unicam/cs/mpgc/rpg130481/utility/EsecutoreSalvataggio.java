package it.unicam.cs.mpgc.rpg130481.utility;

public interface EsecutoreSalvataggio {
    void accodaSalvataggio(Runnable operazioneSalvataggio);
    void arrestaEAttendi();
}
