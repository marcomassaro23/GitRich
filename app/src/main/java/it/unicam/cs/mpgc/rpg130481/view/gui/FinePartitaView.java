package it.unicam.cs.mpgc.rpg130481.view.gui;

import it.unicam.cs.mpgc.rpg130481.controller.GestoreAzioniPartita;
import it.unicam.cs.mpgc.rpg130481.model.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FinePartitaView implements FxView {

    @FXML
    private FlowPane boxMetriche;

    private GestoreAzioniPartita gestoreAzioni;

    @Override
    public void setGestoreAzioni(GestoreAzioniPartita gestoreAzioni) {
        this.gestoreAzioni = Objects.requireNonNull(gestoreAzioni, "Il gestore azioni non può essere nullo.");
    }

    public void mostraEsito(StatoGiocoDto statoFinale) {
        Objects.requireNonNull(statoFinale, "Lo stato finale non può essere nullo.");
        boxMetriche.getChildren().clear();
        statoFinale.statistiche().getMetriche()
                .forEach(metrica -> boxMetriche.getChildren().add(creaEtichettaMetrica(metrica)));
    }

    @FXML
    public void handleRiavviaPartita() {
        gestoreAzioni.avviaNuovaPartita();
    }

    @FXML
    public void handleMenuPrincipale() {
        gestoreAzioni.apriMenuPrincipale();
    }

    private Label creaEtichettaMetrica(Metrica metrica) {
        Label etichetta = new Label(metrica.getNome() + ": " + metrica.getValore());
        etichetta.setFont(Font.font("System", FontWeight.BOLD, 15));
        etichetta.setTextFill(Color.MIDNIGHTBLUE);
        return etichetta;
    }
}
