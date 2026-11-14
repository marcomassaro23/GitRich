package it.unicam.cs.mpgc.rpg130481.model.core.partita;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

/**
 * Rappresenta lo stato della partita in un formato leggibile.
 * Contiene informazioni sul turno corrente, le statistiche dell'azienda, l'evento corrente,
 * lo stato della partita e se la partita è terminata.
 */
public record StatoGiocoDto(
        TurnoBase turno,
        StatisticheAziendaModificabili statistiche,
        Evento eventoCorrente,
        StatoPartita statoPartita,
        boolean isTerminata
) {
    /**
     * Costruisce un nuovo StatoGiocoDto con le informazioni specificate.
     *
     * @param turno il turno corrente della partita
     * @param statistiche le statistiche dell'azienda
     * @param eventoCorrente l'evento corrente della partita
     * @param statoPartita lo stato della partita
     * @param isTerminata true se la partita è terminata, false altrimenti
     * @throws IllegalArgumentException se uno dei campi obbligatori è nullo
     */
    public StatoGiocoDto {
        if (turno == null || statistiche == null || statoPartita == null) {
            throw new IllegalArgumentException("I campi obbligatori del DTO non possono essere nulli.");
        }
    }
}
