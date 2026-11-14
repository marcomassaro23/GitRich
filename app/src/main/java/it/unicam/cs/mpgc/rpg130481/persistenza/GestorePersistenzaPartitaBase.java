package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.partita.StatoGiocoDto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorePersistenzaPartitaBase implements GestorePersistenzaPartita {

    private static final Logger LOGGER = Logger.getLogger(GestorePersistenzaPartitaBase.class.getName());

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
            StatoGiocoDto stato = salvataggioRepository.carica();
            return stato != null && stato.statoPartita() != null && !stato.isTerminata();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Nessuna partita salvata valida trovata", e);
            return false;
        }
    }

    @Override
    public StatoGiocoDto caricaStatoIniziale() {
        return sorgenteDefault.carica();
    }

    @Override
    public StatoGiocoDto caricaStatoSalvato() {
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
