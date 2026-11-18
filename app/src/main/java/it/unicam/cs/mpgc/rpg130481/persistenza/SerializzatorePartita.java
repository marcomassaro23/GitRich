package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.PartitaBase;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import it.unicam.cs.mpgc.rpg130481.persistenza.ContenitoreDati.MetricaDati;
import it.unicam.cs.mpgc.rpg130481.persistenza.ContenitoreDati.TurnoDati;
import java.util.List;
import java.util.Objects;

public class SerializzatorePartita {

    public ContenitoreDati serializza(Partita partita) {
        Objects.requireNonNull(partita, "La partita non può essere nulla.");

        TurnoDati turno = new TurnoDati(
                partita.getTurno().corrente(),
                partita.getTurno().massimo());

        List<MetricaDati> metriche = partita.getStatistiche().getMetriche().stream()
                .map(m -> new MetricaDati(
                        m.getNome(),
                        m.getValore(),
                        m.getValoreMinimo(),
                        m.getValoreMassimo(),
                        m.isCriticaAlMassimo()))
                .toList();

        String titoloEvento = null;
        if (partita.getEventoCorrente() != null) {
            titoloEvento = partita.getEventoCorrente().getTitolo();
        }

        return new ContenitoreDati(turno, metriche, titoloEvento, partita.getStato().name());
    }

    public Partita deserializza(ContenitoreDati dati, List<Evento> mazzoEventi) {
        try {
            Objects.requireNonNull(dati, "I dati della partita non possono essere nulli.");
            Objects.requireNonNull(mazzoEventi, "Il mazzo eventi non può essere nullo.");

            Turno turno = new Turno(dati.turno().corrente(), dati.turno().massimo());

            List<MetricaBase> metriche = dati.metriche().stream()
                    .map(m -> new MetricaBase(
                            m.nome(),
                            m.valore(),
                            m.valoreMinimo(),
                            m.valoreMassimo(),
                            m.criticaAlMassimo()))
                    .toList();

            Evento evento = null;
            String titoloEvento = dati.titoloEventoCorrente();
            if (titoloEvento != null && !titoloEvento.isBlank()) {
                evento = mazzoEventi.stream()
                        .filter(e -> Objects.equals(e.getTitolo(), titoloEvento))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Evento corrente non trovato nel mazzo: " + titoloEvento));
            }

            StatoPartita stato = StatoPartita.IN_CORSO;
            if (dati.codiceStato() != null) {
                stato = StatoPartita.valueOf(dati.codiceStato());
            }

            return new PartitaBase(new StatisticheAziendaBase(metriche), mazzoEventi, turno, evento, stato);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Dati di salvataggio non validi.", e);
        }
    }
}
