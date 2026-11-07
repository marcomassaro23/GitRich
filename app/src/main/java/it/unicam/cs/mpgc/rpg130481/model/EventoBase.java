package it.unicam.cs.mpgc.rpg130481.model;

import java.util.List;

public abstract class EventoBase implements Evento {
    private final String titolo;
    private final String descrizione;
    private final List<OpzioneEvento> opzioni;

    public EventoBase(String titolo, String descrizione, List<OpzioneEvento> opzioni) {
        if (titolo == null || titolo.isBlank() || descrizione == null || descrizione.isBlank()) {
            throw new IllegalArgumentException("Titolo e descrizione non possono essere vuoti.");
        }
        if (opzioni == null || opzioni.isEmpty()) {
            throw new IllegalArgumentException("L'evento deve contenere almeno un'opzione.");
        }
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.opzioni = List.copyOf(opzioni);
    }

    @Override public String getTitolo() { return titolo; }
    @Override public String getDescrizione() { return descrizione; }

    @Override
    public List<OpzioneEventoLeggibile> getOpzioniLeggibili() {
        return List.copyOf(this.opzioni);
    }

    @Override
    public boolean isAttivabile(StatisticheAziendaLeggibili statistiche) {
        if (statistiche == null) {
            throw new IllegalArgumentException("Le statistiche aziendali non possono essere nulle.");
        }
        return true;
    }

    @Override
    public void eseguiOpzione(int indiceOpzione, StatisticheAzienda statistiche) {
        if (statistiche == null) {
            throw new IllegalArgumentException("Le statistiche aziendali non possono essere nulle.");
        }
        if (indiceOpzione < 0 || indiceOpzione >= opzioni.size()) {
            throw new IllegalArgumentException("Indice opzione non valido: " + indiceOpzione);
        }

        OpzioneEvento opzione = opzioni.get(indiceOpzione);
        opzione.getModifiche().forEach(modifica ->
                statistiche.applicaVariazione(modifica.nomeMetrica(), modifica.variazione())
        );
    }
}
