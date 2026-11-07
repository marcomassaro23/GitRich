package it.unicam.cs.mpgc.rpg130481.model;

public interface Metrica extends MetricaLeggibile {
    int applicaVariazione(int delta);
    void setValoreAssoluto(int nuovoValore);
}
