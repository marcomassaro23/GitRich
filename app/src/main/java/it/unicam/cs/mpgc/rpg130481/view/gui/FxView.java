package it.unicam.cs.mpgc.rpg130481.view.gui;

import it.unicam.cs.mpgc.rpg130481.controller.GestoreAzioniPartita;

@FunctionalInterface
public interface FxView {
    void setGestoreAzioni(GestoreAzioniPartita gestoreAzioni);
}
