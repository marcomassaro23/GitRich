package it.unicam.cs.mpgc.rpg130481.repository.json;

import it.unicam.cs.mpgc.rpg130481.model.core.eventi.EventoBase;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.OpzioneEventoBase;
import it.unicam.cs.mpgc.rpg130481.model.core.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.core.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.Turno;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.MetricaModificabile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.repository.json.DatiSalvataggioPartita.DatiMetrica;
import it.unicam.cs.mpgc.rpg130481.repository.json.DatiSalvataggioPartita.DatiTurno;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SalvataggioPartitaMapper {

    private final List<StatoPartita> statiRegistrati;

    public SalvataggioPartitaMapper(StatoPartita statoDefault, List<RegolaStato> regole) {
        Objects.requireNonNull(statoDefault, "Lo stato di default non può essere nullo.");
        List<StatoPartita> stati = new ArrayList<>();
        stati.add(statoDefault);
        if (regole != null) {
            regole.stream()
                .filter(Objects::nonNull)
                .map(RegolaStato::getStatoRisultante)
                .filter(Objects::nonNull)
                .forEach(stati::add);
        }
        this.statiRegistrati = List.copyOf(stati);
    }

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

    public StatoGiocoDto versoDto(DatiSalvataggioPartita dati) {
        if (dati == null || dati.turno() == null || dati.metriche() == null) {
            throw new IllegalArgumentException("Dati di salvataggio incompleti o corrotti.");
        }

        Turno turno = new Turno(dati.turno().corrente(), dati.turno().massimo());
        List<MetricaModificabile> metriche = dati.metriche().stream()
            .map(this::ricostruisciMetrica)
            .toList();

        StatoPartita stato = risolviStato(dati.codiceStato());
        Evento segnapostoEvento = creaSegnapostoEvento(dati.titoloEventoCorrente());

        return new StatoGiocoDto(
            turno,
            new StatisticheAziendaBase(metriche),
            segnapostoEvento,
            stato,
            stato.isTerminata()
        );
    }

    private MetricaModificabile ricostruisciMetrica(DatiMetrica dati) {
        int min = dati.valoreMassimo() == 0 && dati.valoreMinimo() == 0 ? 0 : dati.valoreMinimo();
        int max = dati.valoreMassimo() == 0 && dati.valoreMinimo() == 0 ? 100 : dati.valoreMassimo();
        return new MetricaBase(dati.nome(), dati.valore(), min, max, dati.criticaAlMassimo());
    }

    private StatoPartita risolviStato(String codiceStato) {
        return statiRegistrati.stream()
            .filter(s -> s.getCodice().equalsIgnoreCase(codiceStato))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Stato partita non riconosciuto: " + codiceStato));
    }

    private Evento creaSegnapostoEvento(String titolo) {
        if (titolo == null || titolo.isBlank()) {
            return null;
        }
        return new EventoBase(titolo, "Evento salvato", List.of(new OpzioneEventoBase("Continua", List.of())));
    }
}
