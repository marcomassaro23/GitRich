package it.unicam.cs.mpgc.rpg130481.model;

public interface Evento extends EventoLeggibile {
    boolean isAttivabile(StatisticheAziendaLeggibili statistiche);
    void eseguiOpzione(int indiceOpzione, StatisticheAzienda statistiche);
}
