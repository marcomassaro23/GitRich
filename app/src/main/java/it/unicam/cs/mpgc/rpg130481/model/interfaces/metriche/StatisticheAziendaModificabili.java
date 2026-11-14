package it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche;

public interface StatisticheAziendaModificabili extends StatisticheAzienda {
    Metrica applicaVariazione(String nome, int variazione);
}
