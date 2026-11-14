package it.unicam.cs.mpgc.rpg130481.model.core.metriche;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.MetricaModificabile;

public class MetricaBase implements MetricaModificabile {

    private final String nome;
    private final int valoreMinimo;
    private final int valoreMassimo;
    private final boolean criticaAlMassimo;
    private int valore;

    public MetricaBase(
        String nome,
        int valoreIniziale,
        int valoreMinimo,
        int valoreMassimo,
        boolean criticaAlMassimo
    ) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
        if (valoreMinimo >= valoreMassimo) {
            throw new IllegalArgumentException("Il valore minimo deve essere inferiore al valore massimo.");
        }
        this.nome = nome;
        this.valoreMinimo = valoreMinimo;
        this.valoreMassimo = valoreMassimo;
        this.criticaAlMassimo = criticaAlMassimo;
        this.valore = Math.clamp(valoreIniziale, valoreMinimo, valoreMassimo);
    }

    public MetricaBase(String nome, int valoreIniziale, int valoreMinimo, int valoreMassimo) {
        this(nome, valoreIniziale, valoreMinimo, valoreMassimo, false);
    }

    public MetricaBase(String nome, int valoreIniziale) {
        this(nome, valoreIniziale, 0, 100, false);
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
    public boolean isInStatoCritico() {
        return criticaAlMassimo ? isMax() : isMin();
    }

    @Override
    public int applicaVariazione(int variazione) {
        this.valore = Math.clamp(this.valore + variazione, valoreMinimo, valoreMassimo);
        return this.valore;
    }

    @Override
    public void setValore(int nuovoValore) {
        this.valore = Math.clamp(nuovoValore, valoreMinimo, valoreMassimo);
    }
}
