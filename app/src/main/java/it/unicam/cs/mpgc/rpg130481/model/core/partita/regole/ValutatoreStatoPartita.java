package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import java.util.List;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stato.RegolaStato;

/**
 * La classe ValutatoreStatoPartita valuta lo stato di una partita in base alle regole di bancarotta e vittoria.
 * Utilizza le regole specificate per determinare se la partita è terminata e aggiorna lo stato della partita di conseguenza.
 */
public class ValutatoreStatoPartita {
    private final List<RegolaStato> regole;

    /**
     * Costruisce un ValutatoreStatoPartita con le regole specificate.
     *
     * @param regole la lista delle regole di stato da utilizzare per valutare la partita
     * @throws IllegalArgumentException se la lista delle regole è nulla o vuota
     */
    public ValutatoreStatoPartita(List<RegolaStato> regole) {
        if (regole == null || regole.isEmpty()) {
            throw new IllegalArgumentException("La lista delle regole non può essere nulla o vuota.");
        }
        this.regole = List.copyOf(regole);
    }

    /**
     * Valuta lo stato della partita specificata in base alle regole di stato.
     * Se una regola è rispettata, aggiorna lo stato della partita con lo stato associato alla regola.
     *
     * @param partita la partita da valutare
     * @throws IllegalArgumentException se la partita è nulla
     */
    public void valutaStatoPartita(Partita partita) {
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
