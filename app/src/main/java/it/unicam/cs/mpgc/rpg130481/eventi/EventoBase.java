package it.unicam.cs.mpgc.rpg130481.eventi;

import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.metriche.StatisticheAziendaModificabili;
import java.util.List;

public class EventoBase implements EventoEseguibile {

    private final String titolo;
    private final String descrizione;
    private final List<OpzioneEvento> opzioni;

    public EventoBase(String titolo, String descrizione, List<OpzioneEvento> opzioni) {
        if (isTestoAssente(titolo) || isTestoAssente(descrizione)) {
            throw new IllegalArgumentException("Titolo e descrizione non possono essere vuoti.");
        }
        if (opzioni == null || opzioni.isEmpty()) {
            throw new IllegalArgumentException("L'evento deve contenere almeno un'opzione.");
        }
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.opzioni = List.copyOf(opzioni);
    }

    @Override
    public String getTitolo() {
        return titolo;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public List<OpzioneEvento> getOpzioni() {
        return opzioni;
    }

    @Override
    public boolean isAttivabileCon(StatisticheAzienda statistiche) {
        if (statistiche == null) {
            throw new IllegalArgumentException("Le statistiche aziendali non possono essere nulle.");
        }
        return true;
    }

    @Override
    public void eseguiOpzione(int indiceOpzione, StatisticheAziendaModificabili statistiche) {
        if (statistiche == null) {
            throw new IllegalArgumentException("Le statistiche aziendali non possono essere nulle.");
        }
        if (isIndiceFuoriIntervallo(indiceOpzione)) {
            throw new IllegalArgumentException("Indice opzione non valido: " + indiceOpzione);
        }

        OpzioneEvento opzioneScelta = opzioni.get(indiceOpzione);
        opzioneScelta
            .variazioni()
            .forEach(variazione -> statistiche.applicaVariazione(variazione.nomeMetrica(), variazione.variazione()));
    }

    private boolean isTestoAssente(String testo) {
        return testo == null || testo.isBlank();
    }

    private boolean isIndiceFuoriIntervallo(int indice) {
        return indice < 0 || indice >= opzioni.size();
    }
}
