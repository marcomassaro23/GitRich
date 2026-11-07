package it.unicam.cs.mpgc.rpg130481.model.core.partita.regole;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoBancarotta;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.stati.StatoVittoria;

/**
 * La classe ValutatoreStatoPartita valuta lo stato di una partita in base alle regole di bancarotta e vittoria.
 * Utilizza le regole specificate per determinare se la partita è terminata e aggiorna lo stato della partita di conseguenza.
 */
public class ValutatoreStatoPartita {
    private final RegolaBancarotta regolaBancarotta;
    private final RegolaVittoria regolaVittoria;

    /**
     * Costruisce un nuovo ValutatoreStatoPartita con le regole di bancarotta e vittoria specificate.
     *
     * @param regolaBancarotta la regola di bancarotta da utilizzare per valutare lo stato della partita
     * @param regolaVittoria la regola di vittoria da utilizzare per valutare lo stato della partita
     */
    public ValutatoreStatoPartita(RegolaBancarotta regolaBancarotta, RegolaVittoria regolaVittoria) {
        this.regolaBancarotta = regolaBancarotta;
        this.regolaVittoria = regolaVittoria;
    }

    /**
     * Valuta lo stato della partita specificata e aggiorna lo stato della partita di conseguenza.
     * Se la regola di bancarotta è rispettata, imposta lo stato della partita a "Bancarotta".
     * Se la regola di vittoria è rispettata, imposta lo stato della partita a "Vittoria".
     *
     * @param partita la partita da valutare e aggiornare
     */
    public void valutaStatoPartita(Partita partita) {
        if (regolaBancarotta.isRispettata(partita)) {
            partita.setStato(new StatoBancarotta());
        } else if (regolaVittoria.isRispettata(partita)) {
            partita.setStato(new StatoVittoria());
        }
    }
}
