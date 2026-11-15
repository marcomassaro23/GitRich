package it.unicam.cs.mpgc.rpg130481.persistenza.json;

import it.unicam.cs.mpgc.rpg130481.controller.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.persistenza.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.DatiSalvataggioPartita.DatiMetrica;
import it.unicam.cs.mpgc.rpg130481.persistenza.DatiSalvataggioPartita.DatiTurno;

import java.util.List;
import java.util.Objects;

public class SalvataggioPartitaMapper {

    public DatiSalvataggioPartita daDto(StatoGiocoDto dto) {
        Objects.requireNonNull(dto, "Lo StatoGiocoDto non può essere nullo.");

        DatiTurno turno = new DatiTurno(dto.turno().corrente(), dto.turno().massimo());
        List<DatiMetrica> metriche = dto.statistiche().getMetriche().stream()
            .map(m -> new DatiMetrica(
                m.getNome(),
                m.getValore(),
                m.getValoreMinimo(),
                m.getValoreMassimo(),
                m.isCriticaAlMassimo()
            ))
            .toList();

        String titoloEvento = dto.eventoCorrente() != null ? dto.eventoCorrente().getTitolo() : null;
        return new DatiSalvataggioPartita(
            turno,
            metriche,
            titoloEvento,
            dto.statoPartita().getCodice(),
            dto.isTerminata()
        );
    }

    public DatiSalvataggioPartita valida(DatiSalvataggioPartita dati) {
        if (dati == null || dati.turno() == null || dati.metriche() == null) {
            throw new IllegalArgumentException("Dati di salvataggio incompleti o corrotti.");
        }
        return dati;
    }
}
