package it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche;

import java.util.List;

/**
 * Interfaccia che rappresenta le statistiche di un'azienda.
 */
public interface StatisticheAziendaLeggibili {
    /**
     * Restituisce la lista delle metriche dell'azienda.
     *
     * @return la lista delle metriche
     */
    List<MetricaLeggibile> getMetriche();

    /**
     * Restituisce la metrica corrispondente al nome specificato.
     *
     * @param nome il nome della metrica
     * @return la metrica corrispondente al nome
     * @throws IllegalArgumentException se la metrica non viene trovata
     */
    MetricaLeggibile getMetrica(String nome);

    /**
     * Verifica se l'azienda ha metriche critiche.
     *
     * @return true se l'azienda ha metriche critiche, false altrimenti
     */
    boolean haMetricheCritiche();
}
