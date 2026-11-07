package it.unicam.cs.mpgc.rpg134081.model;

import java.util.List;

public interface StatisticheAziendaLeggibili {
    List<MetricaLeggibile> getMetriche();
    MetricaLeggibile getMetrica(String nome);
    boolean haMetricheCritiche();
}
