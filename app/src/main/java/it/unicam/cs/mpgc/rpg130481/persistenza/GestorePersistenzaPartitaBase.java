package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.controller.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoEseguibile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GestorePersistenzaPartitaBase implements GestorePersistenzaPartita {

    private final StatoPartitaRepository salvataggioRepository;
    private final SorgenteStatoPartita sorgenteDefault;
    private final EventoRepository eventoRepository;
    private final EsecutoreSalvataggio esecutoreSalvataggio;

    public GestorePersistenzaPartitaBase(
        StatoPartitaRepository salvataggioRepository,
        SorgenteStatoPartita sorgenteDefault,
        EventoRepository eventoRepository,
        EsecutoreSalvataggio esecutoreSalvataggio
    ) {
        this.salvataggioRepository = Objects.requireNonNull(salvataggioRepository);
        this.sorgenteDefault = Objects.requireNonNull(sorgenteDefault);
        this.eventoRepository = Objects.requireNonNull(eventoRepository);
        this.esecutoreSalvataggio = Objects.requireNonNull(esecutoreSalvataggio);
    }

    @Override
    public boolean hasSalvataggioRiprendibile() {
        try {
            DatiSalvataggioPartita dati = salvataggioRepository.carica();
            return dati != null && dati.codiceStato() != null && !dati.terminata();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public DatiSalvataggioPartita caricaStatoIniziale() {
        return sorgenteDefault.carica();
    }

    @Override
    public DatiSalvataggioPartita caricaStatoSalvato() {
        return salvataggioRepository.carica();
    }

    @Override
    public List<EventoEseguibile> caricaMazzoEventi() {
        List<EventoEseguibile> eventi = eventoRepository.carica();
        return eventi != null ? eventi : Collections.emptyList();
    }

    @Override
    public void salvaInBackground(StatoGiocoDto stato) {
        if (stato != null) {
            esecutoreSalvataggio.accodaSalvataggio(() -> salvataggioRepository.salva(stato));
        }
    }

    @Override
    public void arrestaServizi() {
        esecutoreSalvataggio.arrestaEAttendi();
    }
}
