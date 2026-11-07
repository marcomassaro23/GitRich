package it.unicam.cs.mpgc.rpg130481.model;

/**
 * Interfaccia che rappresenta una metrica del gioco.
 */
public interface Metrica extends MetricaLeggibile {
    /**
     * Applica una variazione alla metrica.
     *
     * @param delta la variazione da applicare
     * @return il nuovo valore della metrica dopo l'applicazione della variazione
     */
    int applicaVariazione(int delta);

    /**
     * Imposta un nuovo valore assoluto per la metrica.
     *
     * @param nuovoValore il nuovo valore da impostare
     */
    void setValoreAssoluto(int nuovoValore);
}
