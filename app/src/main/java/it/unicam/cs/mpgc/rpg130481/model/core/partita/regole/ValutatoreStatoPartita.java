package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import java.util.List;

public class ValutatoreStatoPartita {

    private final List<RegolaStato> regole;

    public ValutatoreStatoPartita(List<RegolaStato> regole) {
        if (regole == null || regole.isEmpty()) {
            throw new IllegalArgumentException("La lista delle regole non può essere nulla o vuota.");
        }
        this.regole = List.copyOf(regole);
    }

    public void valutaStatoPartita(PartitaBase partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere nulla.");
        }

        for (RegolaStato regola : regole) {
            if (regola.isRispettata(partita)) {
                partita.setStato(regola.getStatoAssociato());
                return;
            }
        }
    }
}
