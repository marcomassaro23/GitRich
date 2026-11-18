package it.unicam.cs.mpgc.rpg130481.view.gui;

import it.unicam.cs.mpgc.rpg130481.controller.GestoreAzioniPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class FinePartitaView implements FxView {

    @FXML
    private FlowPane boxMetriche;

    private final CreatoreBadgeMetrica creatoreBadge;
    private GestoreAzioniPartita gestoreAzioni;

    public FinePartitaView() {
        this(new CreatoreBadgeMetrica());
    }

    public FinePartitaView(CreatoreBadgeMetrica creatoreBadge) {
        this.creatoreBadge = Objects.requireNonNull(creatoreBadge, "Il creatore badge non può essere nullo.");
    }

    @Override
    public void setGestoreAzioni(GestoreAzioniPartita gestoreAzioni) {
        this.gestoreAzioni = Objects.requireNonNull(gestoreAzioni, "Il gestore azioni non può essere nullo.");
    }

    public void mostraEsito(Partita partita) {
        Objects.requireNonNull(partita, "La partita non può essere nulla.");
        boxMetriche.getChildren().setAll(creatoreBadge.perMetriche(partita.getStatistiche()));
    }

    @FXML
    public void handleRiavviaPartita() {
        gestoreAzioni.avviaNuovaPartita();
    }

    @FXML
    public void handleMenuPrincipale() {
        gestoreAzioni.apriMenuPrincipale();
    }
}
