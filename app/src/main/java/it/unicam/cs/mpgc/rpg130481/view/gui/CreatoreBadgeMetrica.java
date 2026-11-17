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

    private record StileBadge(int fontSize, Insets padding, double radius, double borderWidth) {
    }

    private static final StileBadge STILE_GRANDE = new StileBadge(14, new Insets(8, 16, 8, 16), 8, 2);
    private static final StileBadge STILE_PICCOLO = new StileBadge(11, new Insets(3, 8, 3, 8), 6, 1.5);

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

    public List<Label> perMetriche(List<Metrica> metriche) {
        return metriche.stream()
                .map(m -> creaBadge(
                        m.getNome() + ": " + m.getValore(),
                        coloreDi(m.getNome(), metriche),
                        STILE_GRANDE))
                .toList();
    }

    public List<Label> perVariazioni(List<VariazioneMetrica> variazioni, List<Metrica> metriche) {
        return variazioni.stream()
                .map(variazione -> perVariazione(variazione, metriche))
                .toList();
    }

    private Label perVariazione(VariazioneMetrica variazione, List<Metrica> metriche) {
        String testo = String.format("%s: %+d", variazione.nomeMetrica(), variazione.variazione());
        Color colore = coloreDi(variazione.nomeMetrica(), metriche);
        return creaBadge(testo, colore, STILE_PICCOLO);
    }

    private Label creaBadge(String testo, Color sfondo, StileBadge stile) {
        CornerRadii angoli = new CornerRadii(stile.radius());
        Label badge = new Label(testo);
        badge.setTextFill(Color.WHITE);
        badge.setFont(Font.font("System", FontWeight.BOLD, stile.fontSize()));
        badge.setPadding(stile.padding());
        badge.setBackground(new Background(new BackgroundFill(sfondo, angoli, new Insets(1))));
        badge.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, angoli, new BorderWidths(stile.borderWidth()))));
        return badge;
    }

    private Color coloreDi(String nomeMetrica, List<Metrica> metriche) {
        for (int i = 0; i < metriche.size(); i++) {
            if (metriche.get(i).getNome().equals(nomeMetrica)) {
                return colori.get(i % colori.size());
            }
        }
        return Color.SLATEGRAY;
    }
}
