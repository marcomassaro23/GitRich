package it.unicam.cs.mpgc.rpg130481.persistenza;

import java.util.List;

public record ContenitoreDati(
        TurnoDati turno,
        List<MetricaDati> metriche,
        String titoloEventoCorrente,
        String codiceStato) {

    public record TurnoDati(int corrente, int massimo) {}

    public record MetricaDati(
            String nome,
            int valore,
            int valoreMinimo,
            int valoreMassimo,
            boolean criticaAlMassimo) {}
}
