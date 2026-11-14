package it.unicam.cs.mpgc.rpg130481.model.core.metriche;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.MetricaModificabile;

public class MetricaBase implements MetricaModificabile {

    private final String nome;
    private final int valoreMinimo;
    private final int valoreMassimo;
    private int valore;

    public MetricaBase(String nome, int valoreIniziale, int valoreMinimo, int valoreMassimo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
        if (valoreMinimo >= valoreMassimo) {
            throw new IllegalArgumentException("Il valore minimo deve essere inferiore al valore massimo.");
        }
        this.nome = nome;
        this.valoreMinimo = valoreMinimo;
        this.valoreMassimo = valoreMassimo;
        this.valore = Math.clamp(valoreIniziale, valoreMinimo, valoreMassimo);
    }

    public MetricaBase(String nome, int valoreIniziale) {
        this(nome, valoreIniziale, 0, 100);
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getValore() {
        return valore;
    }

    @Override
    public int getValoreMinimo() {
        return valoreMinimo;
    }

    @Override
    public int getValoreMassimo() {
        return valoreMassimo;
    }

    @Override
    public boolean isCritica() {
        return isMin();
    }

    @Override
    public int applicaVariazione(int delta) {
        this.valore = Math.clamp(this.valore + delta, valoreMinimo, valoreMassimo);
        return this.valore;
    }

    @Override
    public void setValoreAssoluto(int nuovoValore) {
        this.valore = Math.clamp(nuovoValore, valoreMinimo, valoreMassimo);
    }
}
