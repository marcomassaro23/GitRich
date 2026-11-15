package it.unicam.cs.mpgc.rpg130481.model.metriche;

public interface MetricaModificabile extends Metrica {
    int applicaVariazione(int variazione);
    void setValore(int nuovoValore);
}
