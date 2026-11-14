package it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche;

import java.util.List;

public interface StatisticheAzienda {
    List<Metrica> getMetriche();
    Metrica getMetrica(String nome);
    boolean hasMetricheCritiche();
}
