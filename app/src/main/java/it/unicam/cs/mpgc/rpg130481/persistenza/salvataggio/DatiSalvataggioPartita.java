package it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio;

import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import java.util.List;
import java.util.Objects;

public record DatiSalvataggioPartita(
        DatiTurno turno,
        List<DatiMetrica> metriche,
        String titoloEventoCorrente,
        String codiceStato,
        boolean terminata) {
    public DatiSalvataggioPartita {
        if (turno == null || metriche == null || metriche.isEmpty()) {
            throw new IllegalArgumentException("Dati di salvataggio incompleti o corrotti.");
        }
        metriche = List.copyOf(metriche);
    }

    public static DatiSalvataggioPartita daDto(StatoGiocoDto dto) {
        Objects.requireNonNull(dto, "Lo StatoGiocoDto non può essere nullo.");

        DatiTurno turno = new DatiTurno(dto.turno().corrente(), dto.turno().massimo());
        List<DatiMetrica> metriche = dto.statistiche().getMetriche().stream()
                .map(m -> new DatiMetrica(
                        m.getNome(),
                        m.getValore(),
                        m.getValoreMinimo(),
                        m.getValoreMassimo(),
                        m.isCriticaAlMassimo()))
                .toList();

        String titoloEvento = dto.eventoCorrente() != null ? dto.eventoCorrente().getTitolo() : null;
        return new DatiSalvataggioPartita(
                turno,
                metriche,
                titoloEvento,
                dto.statoPartita().getCodice(),
                dto.isTerminata());
    }

    public record DatiTurno(int corrente, int massimo) {
    }

    public record DatiMetrica(
            String nome,
            int valore,
            int valoreMinimo,
            int valoreMassimo,
            boolean criticaAlMassimo) {
    }
}
