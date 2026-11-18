package it.unicam.cs.mpgc.rpg130481;

import it.unicam.cs.mpgc.rpg130481.controller.PartitaController;
import it.unicam.cs.mpgc.rpg130481.persistenza.ArchivioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.GestorePersistenzaPartita;
import it.unicam.cs.mpgc.rpg130481.view.gui.GestoreScene;
import it.unicam.cs.mpgc.rpg130481.view.gui.VistaGui;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private PartitaController controller;

    @Override
    public void start(Stage stage) {
        ArchivioPartita persistenza = new GestorePersistenzaPartita();
        VistaGui vista = new VistaGui(new GestoreScene(stage));
        this.controller = new PartitaController(vista, persistenza);
        vista.setGestoreAzioni(controller);

        stage.setTitle("GitRich");
        stage.setResizable(false);
        controller.apriMenuPrincipale();
    }

    @Override
    public void stop() {
        if (controller != null) {
            controller.salvaPartita();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
