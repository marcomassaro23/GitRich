package it.unicam.cs.mpgc.rpg130481.metriche;

public interface Metrica {
    String getNome();
    int getValore();
    int getValoreMinimo();
    int getValoreMassimo();
    boolean isInStatoCritico();

    default boolean isCriticaAlMassimo() {
        return false;
    }

    default boolean isMin() {
        return getValore() <= getValoreMinimo();
    }

    default boolean isMax() {
        return getValore() >= getValoreMassimo();
    }
}
