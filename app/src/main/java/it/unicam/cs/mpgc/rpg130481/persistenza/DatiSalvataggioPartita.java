package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import java.util.List;
import java.util.Objects;

record DatiSalvataggioPartita(
        Turno turno,
        List<DatiMetrica> metriche,
        String titoloEventoCorrente,
        StatoPartita codiceStato) {

    record DatiMetrica(
            String nome,
            int valore,
            int valoreMinimo,
            int valoreMassimo,
            boolean criticaAlMassimo) {

        static DatiMetrica daMetrica(Metrica metrica) {
            Objects.requireNonNull(metrica, "La metrica non può essere nulla.");
            return new DatiMetrica(
                    metrica.getNome(),
                    metrica.getValore(),
                    metrica.getValoreMinimo(),
                    metrica.getValoreMassimo(),
                    metrica.isCriticaAlMassimo());
        }

        Metrica toMetrica() {
            return new MetricaBase(nome, valore, valoreMinimo, valoreMassimo, criticaAlMassimo);
        }

        boolean isValida() {
            return nome != null && !nome.isBlank() && valoreMinimo < valoreMassimo;
        }
    }

    List<Metrica> toMetriche() {
        return metriche.stream().map(DatiMetrica::toMetrica).toList();
    }

    boolean isTerminata() {
        return codiceStato != null && codiceStato.isTerminata();
    }

    boolean isValido() {
        return turno != null
                && metriche != null
                && !metriche.isEmpty()
                && metriche.stream().allMatch(metrica -> metrica != null && metrica.isValida());
    }
}
