package it.unicam.cs.mpgc.rpg130481.controller;

import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.persistenza.ArchivioPartita;
import it.unicam.cs.mpgc.rpg130481.view.VistaPartita;
import java.util.Objects;

public class PartitaController implements GestoreAzioniPartita {

    private Partita partita;
    private final VistaPartita vista;
    private final ArchivioPartita persistenza;

    public PartitaController(VistaPartita vista, ArchivioPartita persistenza) {
        this.vista = Objects.requireNonNull(vista, "La vista della partita non può essere nulla.");
        this.persistenza = Objects.requireNonNull(persistenza, "L'archivio della partita non può essere nullo.");
    }

    @Override
    public void apriMenuPrincipale() {
        vista.mostraMenu(persistenza.esistePartitaRiprendibile());
    }

   @Override
    public void avviaNuovaPartita() {
        esegui("Impossibile avviare una nuova partita", () -> {
            partita = persistenza.creaNuovaPartita();
            salvaEAggiornaVista();
        });
    }

    @Override
    public void caricaPartita() {
        esegui("Impossibile ripristinare la partita", () -> {
            partita = persistenza.riprendiPartitaSalvata();
            aggiornaVista(StatoGiocoDto.daPartita(partita));
        });
    }

    @Override
    public void elaboraSceltaGiocatore(int indiceOpzione) {
        if (partita == null || partita.isTerminata()) {
            return;
        }
        esegui("Azione non valida", () -> {
            partita.giocaTurnoConScelta(indiceOpzione);
            salvaEAggiornaVista();
        });
    }

    private void esegui(String contestoErrore, Runnable azione) {
        try {
            azione.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            vista.mostraMessaggio(contestoErrore + ": " + e.getMessage());
        }
    }

    private void salvaEAggiornaVista() {
        StatoGiocoDto stato = StatoGiocoDto.daPartita(partita);
        persistenza.salvaStato(stato);
        aggiornaVista(stato);
    }

    private void aggiornaVista(StatoGiocoDto stato) {
        if (stato.isTerminata()) {
            vista.mostraFinePartita(stato);
        } else {
            vista.mostraSchermataGioco(stato);
        }
    }
}
