package it.unicam.cs.mpgc.rpg130481.view.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestoreScene {

    private final Stage stage;
    private String fxmlAttivo;
    private FxView controllerAttivo;

    public GestoreScene(Stage stage) {
        this.stage = Objects.requireNonNull(stage, "Lo stage non può essere nullo.");
    }

    public FxView mostraScena(String nomeFileFxml) {
        if (nomeFileFxml.equals(fxmlAttivo) && controllerAttivo != null) {
            return controllerAttivo;
        }
        try {
            URL risorsaFxml = Objects.requireNonNull(
                    getClass().getResource("/views/" + nomeFileFxml),
                    "File FXML non trovato: " + nomeFileFxml);
            FXMLLoader loader = new FXMLLoader(risorsaFxml);
            Parent radice = loader.load();
            FxView controller = loader.getController();

            this.fxmlAttivo = nomeFileFxml;
            this.controllerAttivo = controller;

            if (stage.getScene() == null) {
                stage.setScene(new Scene(radice));
            } else {
                stage.getScene().setRoot(radice);
            }
            if (!stage.isShowing()) {
                stage.show();
            }
            return controller;
        } catch (IOException e) {
            throw new IllegalStateException("Errore durante il caricamento FXML: " + nomeFileFxml, e);
        }
    }
}
