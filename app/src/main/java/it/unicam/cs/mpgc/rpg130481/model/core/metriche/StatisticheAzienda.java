package it.unicam.cs.mpgc.rpg130481.model.core.metriche;

import java.util.List;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.MetricaLeggibile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaLeggibili;

/**
 * Implementazione dell'interfaccia {@link StatisticheAziendaLeggibili} che rappresenta le statistiche di un'azienda.
 */
public class StatisticheAzienda implements StatisticheAziendaLeggibili {
    private final List<Metrica> metriche;

    /**
     * Costruisce un nuovo oggetto StatisticheAzienda con la lista di metriche specificata.
     *
     * @param metriche la lista di metriche dell'azienda
     * @throws IllegalArgumentException se la lista delle metriche è nulla o vuota
     */
    public StatisticheAzienda(List<Metrica> metriche) {
        if (metriche == null || metriche.isEmpty()) {
            throw new IllegalArgumentException("La lista delle metriche non può essere nulla o vuota.");
        }
        this.metriche = List.copyOf(metriche);
    }

    @Override
    public List<MetricaLeggibile> getMetriche() {
        return List.copyOf(this.metriche);
    }

    @Override
    public Metrica getMetrica(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
        return this.metriche.stream()
                .filter(m -> m.getNome().equalsIgnoreCase(nome.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Metrica non trovata: " + nome));
    }

    /**
     * Applica una variazione alla metrica specificata dal nome.
     *
     * @param nome  il nome della metrica
     * @param delta la variazione da applicare
     * @return la metrica aggiornata
     * @throws IllegalArgumentException se la metrica non viene trovata
     */
    public Metrica applicaVariazione(String nome, int delta) {
        Metrica metrica = getMetrica(nome);
        metrica.applicaVariazione(delta);
        return metrica;
    }

    @Override
    public boolean haMetricheCritiche() {
        return metriche.stream().anyMatch(MetricaLeggibile::isCritica);
    }
}
