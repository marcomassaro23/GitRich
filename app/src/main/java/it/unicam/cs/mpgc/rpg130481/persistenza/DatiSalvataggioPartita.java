package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
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

    static DatiSalvataggioPartita daStatoGioco(StatoGiocoDto stato) {
        Objects.requireNonNull(stato, "Lo stato di gioco non può essere nullo.");
        List<DatiMetrica> lista = stato.statistiche().getMetriche().stream()
                .map(DatiMetrica::daMetrica)
                .toList();
        return new DatiSalvataggioPartita(
                stato.turno(),
                lista,
                stato.eventoCorrente().getTitolo(),
                stato.statoPartita());
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
