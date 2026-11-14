package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import java.util.List;
import java.util.Optional;

public class ValutatoreStatoPartita {

    private final List<RegolaStato> regole;

    public ValutatoreStatoPartita(List<RegolaStato> regole) {
        if (regole == null || regole.isEmpty()) {
            throw new IllegalArgumentException("La lista delle regole non può essere nulla o vuota.");
        }
        this.regole = List.copyOf(regole);
    }

    public Optional<StatoPartita> determinaProssimoStato(Partita partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere nulla.");
        }

        return regole
            .stream()
            .filter(regola -> regola.isSoddisfattaDa(partita))
            .map(RegolaStato::getStatoRisultante)
            .findFirst();
    }
}
