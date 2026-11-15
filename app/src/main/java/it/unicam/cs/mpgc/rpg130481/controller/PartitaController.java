package it.unicam.cs.mpgc.rpg130481.controller;

import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.persistenza.GestorePersistenzaPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.view.VistaPartita;

import java.util.List;
import java.util.Objects;

public class PartitaController implements GestoreAzioniPartita {

    private GestoreStatoPartita gestore;
    private final VistaPartita vista;
    private final InizializzatorePartita inizializzatore;
    private final GestorePersistenzaPartita persistenza;

    public PartitaController(
        VistaPartita vista,
        InizializzatorePartita inizializzatore,
        GestorePersistenzaPartita persistenza
    ) {
        this.vista = Objects.requireNonNull(vista, "La vista della partita non può essere nulla.");
        this.inizializzatore = Objects.requireNonNull(inizializzatore, "L'inizializzatore non può essere nullo.");
        this.persistenza = Objects.requireNonNull(persistenza, "Il gestore persistenza non può essere nullo.");
    }

    @Override
    public void apriMenuPrincipale() {
        vista.mostraMenu(persistenza.hasSalvataggioRiprendibile());
    }

    @Override
    public void avviaNuovaPartita() {
        inizializzaPartita(persistenza.caricaStatoIniziale());
    }

    @Override
    public void caricaPartita() {
        if (!persistenza.hasSalvataggioRiprendibile()) {
            avviaNuovaPartita();
            return;
        }
        try {
            inizializzaPartita(persistenza.caricaStatoSalvato());
        } catch (Exception e) {
            vista.mostraMessaggio("Errore durante il ripristino della partita.");
        }
    }

    @Override
    public void elaboraSceltaGiocatore(int indiceOpzione) {
        gestore.giocaTurnoConScelta(indiceOpzione);
        sincronizzaStato();
    }

    private void inizializzaPartita(DatiSalvataggioPartita dati) {
        List<EventoEseguibile> mazzoEventi = persistenza.caricaMazzoEventi();
        this.gestore = inizializzatore.creaPartita(dati, mazzoEventi);
        sincronizzaStato();
    }

    private void sincronizzaStato() {
        StatoGiocoDto statoAttuale = gestore.getStatoAttuale();
        persistenza.salvaInBackground(statoAttuale);
        aggiornaVista(statoAttuale);
    }

    private void aggiornaVista(StatoGiocoDto statoAttuale) {
        if (statoAttuale == null) {
            return;
        }
        if (statoAttuale.isTerminata()) {
            vista.mostraFinePartita(statoAttuale);
        } else {
            vista.mostraSchermataGioco(statoAttuale);
        }
    }
}
