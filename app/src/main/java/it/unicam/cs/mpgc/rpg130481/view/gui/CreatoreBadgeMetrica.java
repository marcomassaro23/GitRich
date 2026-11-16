package it.unicam.cs.mpgc.rpg130481.view.gui;

import it.unicam.cs.mpgc.rpg130481.model.eventi.VariazioneMetrica;
import it.unicam.cs.mpgc.rpg130481.model.metriche.Metrica;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CreatoreBadgeMetrica {

    private final List<Color> colori;

    public CreatoreBadgeMetrica() {
        this(List.of(
                Color.MEDIUMSEAGREEN,
                Color.DODGERBLUE,
                Color.MEDIUMORCHID,
                Color.TOMATO,
                Color.DARKORANGE,
                Color.LIGHTSEAGREEN));
    }

    public CreatoreBadgeMetrica(List<Color> colori) {
        if (colori == null || colori.isEmpty()) {
            throw new IllegalArgumentException("La lista dei colori non può essere vuota.");
        }
        this.colori = List.copyOf(colori);
    }

    public Label perMetrica(Metrica metrica, List<Metrica> metriche) {
        String testo = metrica.getNome() + ": " + metrica.getValore();
        Color colore = coloreDi(metrica.getNome(), metriche);
        return creaBadge(testo, colore, 14, new Insets(8, 16, 8, 16), 8, 2);
    }

    public Label perVariazione(VariazioneMetrica variazione, List<Metrica> metriche) {
        String testo = String.format("%s: %+d", variazione.nomeMetrica(), variazione.variazione());
        Color colore = coloreDi(variazione.nomeMetrica(), metriche);
        return creaBadge(testo, colore, 11, new Insets(3, 8, 3, 8), 6, 1.5);
    }

    private Label creaBadge(
            String testo,
            Color sfondo,
            int dimensioneFont,
            Insets padding,
            double raggio,
            double spessoreBordo) {
        CornerRadii angoli = new CornerRadii(raggio);

        Label badge = new Label(testo);
        badge.setTextFill(Color.WHITE);
        badge.setFont(Font.font("System", FontWeight.BOLD, dimensioneFont));
        badge.setPadding(padding);
        badge.setBackground(new Background(new BackgroundFill(sfondo, angoli, new Insets(1))));
        badge.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, angoli, new BorderWidths(spessoreBordo))));
        return badge;
    }

    private Color coloreDi(String nomeMetrica, List<Metrica> metriche) {
        for (int i = 0; i < metriche.size(); i++) {
            if (metriche.get(i).getNome().equalsIgnoreCase(nomeMetrica)) {
                return colori.get(i % colori.size());
            }
        }
        return Color.SLATEGRAY;
    }
}
