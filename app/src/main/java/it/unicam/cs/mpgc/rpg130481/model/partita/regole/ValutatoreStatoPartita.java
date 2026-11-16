package it.unicam.cs.mpgc.rpg130481.model.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoPartita;
import java.util.List;
import java.util.Objects;

public class ValutatoreStatoPartita {

    private final List<RegolaStato> regole;

    /** L'ordine delle regole è la loro priorità: vale la prima soddisfatta. */
    public ValutatoreStatoPartita(List<RegolaStato> regole) {
        if (regole == null || regole.isEmpty()) {
            throw new IllegalArgumentException("La lista delle regole non può essere nulla o vuota.");
        }
        this.regole = List.copyOf(regole);
    }

    public StatoPartita determinaProssimoStato(Partita partita) {
        Objects.requireNonNull(partita, "La partita non può essere nulla.");
        for (RegolaStato regola : regole) {
            if (regola.isSoddisfattaDa(partita)) {
                return regola.getStatoRisultante();
            }
        }
        return StatoPartita.IN_CORSO;
    }
}
