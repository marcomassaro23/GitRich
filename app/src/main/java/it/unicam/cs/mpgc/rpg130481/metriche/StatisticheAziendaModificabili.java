package it.unicam.cs.mpgc.rpg130481.metriche;

public interface StatisticheAziendaModificabili extends StatisticheAzienda {
    Metrica applicaVariazione(String nome, int variazione);
}
