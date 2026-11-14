package it.unicam.cs.mpgc.rpg130481.repository.json;

import java.util.List;

public record DatiSalvataggioPartita(
    DatiTurno turno,
    List<DatiMetrica> metriche,
    String titoloEventoCorrente,
    String codiceStato,
    boolean terminata
) {
    public record DatiTurno(int corrente, int massimo) {}

    public record DatiMetrica(
        String nome,
        int valore,
        int valoreMinimo,
        int valoreMassimo,
        boolean criticaAlMassimo
    ) {}
}
