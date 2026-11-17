package it.unicam.cs.mpgc.rpg130481.view.gui;

import java.util.Objects;
import it.unicam.cs.mpgc.rpg130481.controller.GestoreAzioniPartita;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuView implements FxView {

    @FXML
    private Button btnRiprendiPartita;

    private GestoreAzioniPartita gestoreAzioni;

    @Override
    public void setGestoreAzioni(GestoreAzioniPartita gestoreAzioni) {
        this.gestoreAzioni = Objects.requireNonNull(gestoreAzioni, "Il gestore azioni non può essere nullo.");
    }

    public void aggiornaDisponibilitaSalvataggio(boolean riprendibile) {
        btnRiprendiPartita.setDisable(!riprendibile);
    }

    @FXML
    public void handleNuovaPartita() {
        gestoreAzioni.avviaNuovaPartita();
    }

    @FXML
    public void handleRiprendiPartita() {
        gestoreAzioni.caricaPartita();
    }

    @FXML
    public void handleEsci() {
        Platform.exit();
    }
}
