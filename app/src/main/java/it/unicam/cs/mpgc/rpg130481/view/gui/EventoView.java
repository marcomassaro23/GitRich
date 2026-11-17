package it.unicam.cs.mpgc.rpg130481.view.gui;

import it.unicam.cs.mpgc.rpg130481.controller.GestoreAzioniPartita;
import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.eventi.OpzioneEvento;
import it.unicam.cs.mpgc.rpg130481.model.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.partita.Turno;
import java.util.List;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class EventoView implements FxView {

    private record CardOpzione(Button pulsante, Label testo, FlowPane variazioni) {}

    @FXML
    private FlowPane boxStatistiche;
    @FXML
    private Label lblTurno;
    @FXML
    private Label lblTitoloEvento;
    @FXML
    private Label lblDescrizioneEvento;

    @FXML
    private Button btnOpzione1, btnOpzione2, btnOpzione3, btnOpzione4;
    @FXML
    private Label lblOpzione1, lblOpzione2, lblOpzione3, lblOpzione4;
    @FXML
    private FlowPane boxVariazioni1, boxVariazioni2, boxVariazioni3, boxVariazioni4;

    private final CreatoreBadgeMetrica creatoreBadge;
    private GestoreAzioniPartita gestoreAzioni;
    private List<CardOpzione> cards;

    public EventoView() {
        this(new CreatoreBadgeMetrica());
    }

    public EventoView(CreatoreBadgeMetrica creatoreBadge) {
        this.creatoreBadge = Objects.requireNonNull(creatoreBadge, "Il creatore badge non può essere nullo.");
    }

    @FXML
    public void initialize() {
        this.cards = List.of(
                new CardOpzione(btnOpzione1, lblOpzione1, boxVariazioni1),
                new CardOpzione(btnOpzione2, lblOpzione2, boxVariazioni2),
                new CardOpzione(btnOpzione3, lblOpzione3, boxVariazioni3),
                new CardOpzione(btnOpzione4, lblOpzione4, boxVariazioni4));
        for (int i = 0; i < cards.size(); i++) {
            final int indice = i;
            cards.get(i).pulsante().setOnAction(e -> gestoreAzioni.elaboraSceltaGiocatore(indice));
        }
    }

    @Override
    public void setGestoreAzioni(GestoreAzioniPartita gestoreAzioni) {
        this.gestoreAzioni = Objects.requireNonNull(gestoreAzioni, "Il gestore azioni non può essere nullo.");
    }

    public void mostraStato(StatoGiocoDto stato) {
        Objects.requireNonNull(stato, "Lo stato di gioco non può essere nullo.");
        List<Metrica> metriche = stato.statistiche().getMetriche();
        mostraMetriche(metriche);
        mostraTurno(stato.turno());
        mostraEvento(stato.eventoCorrente(), metriche);
    }

    @FXML
    public void handleTornaMenu() {
        gestoreAzioni.apriMenuPrincipale();
    }

    private void mostraMetriche(List<Metrica> metriche) {
        boxStatistiche.getChildren().setAll(creatoreBadge.perMetriche(metriche));
    }

    private void mostraTurno(Turno turno) {
        lblTurno.setText("Turno " + turno.corrente() + "/" + turno.massimo());
    }

    private void mostraEvento(Evento evento, List<Metrica> metriche) {
        lblTitoloEvento.setText(evento.getTitolo());
        lblDescrizioneEvento.setText(evento.getDescrizione());
        mostraOpzioni(evento.getOpzioni(), metriche);
    }

    private void mostraOpzioni(List<OpzioneEvento> opzioni, List<Metrica> metriche) {
        for (int i = 0; i < cards.size(); i++) {
            if (i < opzioni.size()) {
                popolaCard(cards.get(i), opzioni.get(i), i + 1, metriche);
            } else {
                nascondiCard(cards.get(i));
            }
        }
    }

    private void popolaCard(CardOpzione card, OpzioneEvento opzione, int numero, List<Metrica> metriche) {
        card.pulsante().setVisible(true);
        card.pulsante().setDisable(false);
        card.testo().setText(numero + ". " + opzione.testo());
        card.variazioni().getChildren().setAll(creatoreBadge.perVariazioni(opzione.variazioni(), metriche));
    }

    private void nascondiCard(CardOpzione card) {
        card.pulsante().setVisible(false);
        card.pulsante().setDisable(true);
    }
}
