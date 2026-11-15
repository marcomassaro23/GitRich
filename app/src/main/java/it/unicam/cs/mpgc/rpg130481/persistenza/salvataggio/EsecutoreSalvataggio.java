package it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio;

public interface EsecutoreSalvataggio {
    void accodaSalvataggio(Runnable operazioneSalvataggio);
    void arrestaEAttendi();
}
