package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import java.util.List;
import java.util.Objects;

public record DatiSalvataggioPartita(
        Turno turno,
        List<MetricaBase> metriche,
        String titoloEventoCorrente,
        StatoPartita codiceStato) {

    public DatiSalvataggioPartita {
        Objects.requireNonNull(turno, "Il turno non può essere nullo.");
        if (metriche == null || metriche.isEmpty()) {
            throw new IllegalArgumentException("Dati di salvataggio incompleti o corrotti.");
        }
        metriche = metriche.stream().map(DatiSalvataggioPartita::copia).toList();
        codiceStato = Objects.requireNonNullElse(codiceStato, StatoPartita.IN_CORSO);
    }

    public boolean isTerminata() {
        return codiceStato.isTerminata();
    }

    static MetricaBase copia(Metrica m) {
        Objects.requireNonNull(m, "La metrica non può essere nulla.");
        return new MetricaBase(
                m.getNome(), m.getValore(), m.getValoreMinimo(), m.getValoreMassimo(), m.isCriticaAlMassimo());
    }
}
