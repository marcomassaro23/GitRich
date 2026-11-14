package it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche;

/**
 * Interfaccia per le metriche leggibili.
 */
public interface Metrica {
    /**
     * Restituisce il nome della metrica.
     * @return il nome della metrica
     */
    String getNome();

    /**
     * Restituisce il valore della metrica.
     * @return il valore della metrica
     */
    int getValore();

    /**
     * Restituisce il valore minimo della metrica.
     * @return il valore minimo della metrica
     */
    int getValoreMinimo();

    /**
     * Restituisce il valore massimo della metrica.
     * @return il valore massimo della metrica
     */
    int getValoreMassimo();

    /**
     * Verifica se il valore della metrica è al minimo.
     * @return true se il valore è al minimo, false altrimenti
     */
    default boolean isMin() { return getValore() <= getValoreMinimo(); }

    /**
     * Verifica se il valore della metrica è al massimo.
     * @return true se il valore è al massimo, false altrimenti
     */
    default boolean isMax() { return getValore() >= getValoreMassimo(); }

    /**
     * Verifica se la metrica è critica.
     * @return true se la metrica è critica, false altrimenti
     */
    boolean isCritica();
}
