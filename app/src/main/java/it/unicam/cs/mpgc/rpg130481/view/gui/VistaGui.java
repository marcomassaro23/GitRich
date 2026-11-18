package it.unicam.cs.mpgc.rpg130481.view.gui;

import it.unicam.cs.mpgc.rpg130481.controller.GestoreAzioniPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.view.VistaPartita;
import java.util.Locale;
import java.util.Objects;
import javafx.scene.control.Alert;

public class VistaGui implements VistaPartita {

    private final GestoreScene gestoreScene;
    private GestoreAzioniPartita gestoreAzioni;

    public VistaGui(GestoreScene gestoreScene) {
        this.gestoreScene = Objects.requireNonNull(gestoreScene, "Il gestore scene non può essere nullo.");
    }

    public void setGestoreAzioni(GestoreAzioniPartita gestoreAzioni) {
        this.gestoreAzioni = Objects.requireNonNull(gestoreAzioni, "Il gestore azioni non può essere nullo.");
    }

    @Override
    public void mostraMenu(boolean salvataggioDisponibile) {
        MenuView view = (MenuView) caricaVista("menu.fxml");
        view.aggiornaDisponibilitaSalvataggio(salvataggioDisponibile);
    }

    @Override
    public void mostraSchermataGioco(Partita partita) {
        EventoView view = (EventoView) caricaVista("partita.fxml");
        view.mostraStato(partita);
    }

    @Override
    public void mostraFinePartita(Partita partita) {
        Objects.requireNonNull(partita, "La partita non può essere nulla.");
        if (!partita.isTerminata()) {
            throw new IllegalArgumentException("La partita non è ancora terminata.");
        }
        String fxml = partita.getStato().name().toLowerCase(Locale.ROOT) + ".fxml";
        FinePartitaView view = (FinePartitaView) caricaVista(fxml);
        view.mostraEsito(partita);
    }

    @Override
    public void mostraMessaggio(String messaggio) {
        Alert avviso = new Alert(Alert.AlertType.INFORMATION);
        avviso.setTitle("Notifica di Gioco");
        avviso.setHeaderText(null);
        avviso.setContentText(messaggio);
        avviso.showAndWait();
    }

    private FxView caricaVista(String fxml) {
        Objects.requireNonNull(gestoreAzioni, "GestoreAzioniPartita non configurato.");
        FxView view = gestoreScene.mostraScena(fxml);
        view.setGestoreAzioni(gestoreAzioni);
        return view;
    }
}
