package it.unicam.cs.mpgc.rpg130481.model.metriche;

import java.util.List;

public class StatisticheAziendaBase implements StatisticheAzienda {

    private final List<MetricaBase> metriche;

    public StatisticheAziendaBase(List<MetricaBase> metriche) {
        if (metriche == null || metriche.isEmpty()) {
            throw new IllegalArgumentException("La lista delle metriche non può essere nulla o vuota.");
        }
        this.metriche = List.copyOf(metriche);
    }

    @Override
    public List<Metrica> getMetriche() {
        return List.copyOf(this.metriche);
    }

    @Override
    public boolean hasMetricheCritiche() {
        return metriche.stream().anyMatch(Metrica::isInStatoCritico);
    }

    public Metrica applicaVariazione(String nome, int delta) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
        MetricaBase metrica = metriche.stream()
                .filter(m -> m.getNome().equals(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Metrica con nome '" + nome + "' non trovata."));
        metrica.applicaVariazione(delta);
        return metrica;
    }
}
