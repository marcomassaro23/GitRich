package it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche;

/**
 * Interfaccia per le statistiche dell'azienda che possono essere modificate.
 */
public interface StatisticheAziendaModificabili extends StatisticheAziendaLeggibili {
    /**
     * Applica una variazione a una metrica specifica dell'azienda.
     *
     * @param nome il nome della metrica da modificare
     * @param delta la variazione da applicare (positiva o negativa)
     * @return la metrica aggiornata dopo l'applicazione della variazione
     */
    MetricaLeggibile applicaVariazione(String nome, int delta);
}
