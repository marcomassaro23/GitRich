package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.core.partita.regole.ValutatoreStatoPartita;

/**
 * La classe GestoreStatoPartita gestisce lo stato della partita e le transizioni tra gli stati.
 * Utilizza un ValutatoreStatoPartita per determinare se la partita è terminata o meno.
 */
public class GestoreStatoPartita {
    private final PartitaBase partita;
    private final ValutatoreStatoPartita valutatoreStato;

    /**
     * Costruisce un nuovo GestoreStatoPartita con la partita e il valutatore di stato specificati.
     *
     * @param partita la partita da gestire
     * @param valutatoreStato il valutatore di stato da utilizzare per determinare lo stato della partita
     * @throws IllegalArgumentException se la partita o il valutatore di stato sono nulli
     */
    public GestoreStatoPartita(PartitaBase partita, ValutatoreStatoPartita valutatoreStato) {
        if (partita == null || valutatoreStato == null) {
            throw new IllegalArgumentException("Partita e ValutatoreStato non possono essere nulli.");
        }
        this.partita = partita;
        this.valutatoreStato = valutatoreStato;
    }

    /**
     * Esegue un turno della partita, eseguendo la scelta dell'opzione specificata e valutando lo stato della partita.
     * Se la partita è terminata, viene lanciata un'eccezione.
     *
     * @param indiceOpzione l'indice dell'opzione da eseguire
     * @throws IllegalStateException se la partita è già terminata
     */
    public void eseguiTurno(int indiceOpzione) {
        if (partita.isTerminata()) {
            throw new IllegalStateException("Impossibile avanzare: la partita è terminata.");
        }

        partita.eseguiScelta(indiceOpzione);
        valutatoreStato.valutaStatoPartita(partita);

        if (!partita.isTerminata()) {
            partita.avanzaTurno();
        }
    }

    /**
     * Restituisce lo stato corrente della partita in un formato leggibile.
     *
     * @return un oggetto StatoGiocoDto contenente le informazioni sullo stato della partita
     */
    public StatoGiocoDto getStatoCorrente() {
        return partita.toDto();
    }
}
