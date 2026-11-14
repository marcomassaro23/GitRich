package it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche;

public interface MetricaModificabile extends Metrica {
    int applicaVariazione(int variazione);
    void setValoreAssoluto(int nuovoValore);
}
