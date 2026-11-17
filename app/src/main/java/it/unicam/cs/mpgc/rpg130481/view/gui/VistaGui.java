package it.unicam.cs.mpgc.rpg130481.view.gui;

import it.unicam.cs.mpgc.rpg130481.controller.GestoreAzioniPartita;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.view.VistaPartita;
import java.util.Objects;
import java.util.function.Consumer;
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
        apriVista("menu.fxml", MenuView.class, v -> v.aggiornaDisponibilitaSalvataggio(salvataggioDisponibile));
    }

    @Override
    public void mostraSchermataGioco(StatoGiocoDto stato) {
        apriVista("partita.fxml", EventoView.class, v -> v.mostraStato(stato));
    }

    @Override
    public void mostraFinePartita(StatoGiocoDto statoFinale) {
        String fxml = statoFinale.statoPartita() == StatoPartita.VITTORIA ? "vittoria.fxml" : "bancarotta.fxml";
        apriVista(fxml, FinePartitaView.class, v -> v.mostraEsito(statoFinale));
    }

    @Override
    public void mostraMessaggio(String messaggio) {
        Alert avviso = new Alert(Alert.AlertType.INFORMATION);
        avviso.setTitle("Notifica di Gioco");
        avviso.setHeaderText(null);
        avviso.setContentText(messaggio);
        avviso.showAndWait();
    }

    private <T extends FxView> void apriVista(String fxml, Class<T> tipo, Consumer<T> azione) {
        Objects.requireNonNull(gestoreAzioni, "GestoreAzioniPartita non configurato.");
        T view = gestoreScene.mostraScena(fxml, tipo);
        view.setGestoreAzioni(gestoreAzioni);
        azione.accept(view);
    }
}
