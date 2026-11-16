package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.EsecutoreSalvataggioAsincrono;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.SorgenteStatoPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.StatoPartitaRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GestorePersistenzaPartita {

    private final StatoPartitaRepository salvataggioRepository;
    private final SorgenteStatoPartita sorgenteDefault;
    private final EventoRepository eventoRepository;
    private final EsecutoreSalvataggioAsincrono esecutoreSalvataggio;

    public GestorePersistenzaPartita(
            StatoPartitaRepository salvataggioRepository,
            SorgenteStatoPartita sorgenteDefault,
            EventoRepository eventoRepository,
            EsecutoreSalvataggioAsincrono esecutoreSalvataggio) {
        this.salvataggioRepository = Objects.requireNonNull(salvataggioRepository);
        this.sorgenteDefault = Objects.requireNonNull(sorgenteDefault);
        this.eventoRepository = Objects.requireNonNull(eventoRepository);
        this.esecutoreSalvataggio = Objects.requireNonNull(esecutoreSalvataggio);
    }

    public boolean hasSalvataggioRiprendibile() {
        try {
            DatiSalvataggioPartita dati = salvataggioRepository.carica();
            return dati != null && dati.codiceStato() != null && !dati.terminata();
        } catch (Exception e) {
            return false;
        }
    }

    public DatiSalvataggioPartita caricaStatoIniziale() {
        return sorgenteDefault.carica();
    }

    public DatiSalvataggioPartita caricaStatoSalvato() {
        return salvataggioRepository.carica();
    }

    public List<Evento> caricaMazzoEventi() {
        List<Evento> eventi = eventoRepository.carica();
        return eventi != null ? eventi : Collections.emptyList();
    }

    public void salvaInBackground(StatoGiocoDto stato) {
        if (stato != null) {
            DatiSalvataggioPartita snapshot = DatiSalvataggioPartita.daDto(stato);
            esecutoreSalvataggio.accodaSalvataggio(() -> salvataggioRepository.salva(snapshot));
        }
    }

    public void arrestaServizi() {
        esecutoreSalvataggio.arrestaEAttendi();
    }
}
