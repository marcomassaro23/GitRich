package it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche;

public interface Metrica {
    String getNome();
    int getValore();
    int getValoreMinimo();
    int getValoreMassimo();

    default boolean isMin() {
        return getValore() <= getValoreMinimo();
    }

    default boolean isMax() {
        return getValore() >= getValoreMassimo();
    }

    boolean isInStatoCritico();
}
