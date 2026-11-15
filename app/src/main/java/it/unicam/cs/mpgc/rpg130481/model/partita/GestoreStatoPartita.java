package it.unicam.cs.mpgc.rpg130481.model.partita;

import it.unicam.cs.mpgc.rpg130481.model.partita.regole.ValutatoreStatoPartita;

import java.util.Objects;

public class GestoreStatoPartita {

    private final PartitaModificabile partita;
    private final ValutatoreStatoPartita valutatoreStato;

    public GestoreStatoPartita(PartitaModificabile partita, ValutatoreStatoPartita valutatoreStato) {
        this.partita = Objects.requireNonNull(partita, "La partita non può essere nulla.");
        this.valutatoreStato = Objects.requireNonNull(valutatoreStato, "Il valutatore stato non può essere nullo.");
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
        return StatoGiocoDto.daPartita(partita);
    }
}
