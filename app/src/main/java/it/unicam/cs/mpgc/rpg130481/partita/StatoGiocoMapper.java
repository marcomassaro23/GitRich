package it.unicam.cs.mpgc.rpg130481.partita;

public final class StatoGiocoMapper {

    private StatoGiocoMapper() {}

    public static StatoGiocoDto daPartita(Partita partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere nulla.");
        }
        return new StatoGiocoDto(
            partita.getTurno(),
            partita.getStatistiche(),
            partita.getEventoCorrente(),
            partita.getStato(),
            partita.isTerminata()
        );
    }
}
