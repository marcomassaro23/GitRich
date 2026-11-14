package it.unicam.cs.mpgc.rpg130481.partita;

import it.unicam.cs.mpgc.rpg130481.partita.regole.ValutatoreStatoPartita;

public class GestoreStatoPartita {

    private final PartitaBase partita;
    private final ValutatoreStatoPartita valutatoreStato;

    public GestoreStatoPartita(PartitaBase partita, ValutatoreStatoPartita valutatoreStato) {
        if (partita == null || valutatoreStato == null) {
            throw new IllegalArgumentException("Partita e ValutatoreStato non possono essere nulli.");
        }
        this.partita = partita;
        this.valutatoreStato = valutatoreStato;
    }

    public void giocaTurnoConScelta(int indiceOpzione) {
        if (partita.isTerminata()) {
            throw new IllegalStateException("Impossibile avanzare: la partita è terminata.");
        }

        partita.applicaScelta(indiceOpzione);
        valutatoreStato.determinaProssimoStato(partita).ifPresent(partita::setStato);

        if (!partita.isTerminata()) {
            partita.avanzaTurno();
        }
    }

    public StatoGiocoDto getStatoAttuale() {
        return StatoGiocoMapper.daPartita(partita);
    }
}
