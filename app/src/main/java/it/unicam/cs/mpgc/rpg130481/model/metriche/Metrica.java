package it.unicam.cs.mpgc.rpg130481.model.metriche;

public interface Metrica {
    String getNome();
    int getValore();
    int getValoreMinimo();
    int getValoreMassimo();
    boolean isCriticaAlMassimo();
    boolean isInStatoCritico();
}
