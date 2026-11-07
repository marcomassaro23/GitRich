package it.unicam.cs.mpgc.rpg134081.model;

public interface Metrica extends MetricaLeggibile {
    int applicaVariazione(int delta);
    void setValoreAssoluto(int nuovoValore);
}
