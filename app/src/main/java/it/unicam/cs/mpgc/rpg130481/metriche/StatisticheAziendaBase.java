package it.unicam.cs.mpgc.rpg130481.metriche;

import java.util.List;

public class StatisticheAziendaBase implements StatisticheAziendaModificabili {

    private final List<MetricaModificabile> metriche;

    public StatisticheAziendaBase(List<MetricaModificabile> metriche) {
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
    public Metrica getMetrica(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
        return cercaMetricaPerNome(nome);
    }

    @Override
    public Metrica applicaVariazione(String nome, int delta) {
        MetricaModificabile metrica = cercaMetricaPerNome(nome);
        metrica.applicaVariazione(delta);
        return metrica;
    }

    @Override
    public boolean hasMetricheCritiche() {
        return metriche.stream().anyMatch(Metrica::isInStatoCritico);
    }

    private MetricaModificabile cercaMetricaPerNome(String nome) {
        return metriche
            .stream()
            .filter(metrica -> metrica.getNome().equals(nome))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Metrica con nome '" + nome + "' non trovata."));
    }
}
