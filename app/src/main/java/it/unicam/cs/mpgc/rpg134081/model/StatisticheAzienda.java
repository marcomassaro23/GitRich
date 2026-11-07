package it.unicam.cs.mpgc.rpg134081.model;

import java.util.List;

public class StatisticheAzienda implements StatisticheAziendaLeggibili {
    private final List<Metrica> metriche;

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
