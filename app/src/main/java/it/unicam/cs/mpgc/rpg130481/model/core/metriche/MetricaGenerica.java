package it.unicam.cs.mpgc.rpg130481.model.core.metriche;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.Metrica;

/**
 * Implementazione generica dell'interfaccia {@link Metrica}.
 */
public class MetricaGenerica implements Metrica {
    private final String nome;
    private final int valoreMinimo;
    private final int valoreMassimo;
    private int valore;

    /**
     * Costruisce una nuova metrica generica con il nome specificato, il valore iniziale, il valore minimo e il valore massimo.
     * @param nome          il nome della metrica
     * @param valoreIniziale il valore iniziale della metrica
     * @param valoreMinimo   il valore minimo consentito per la metrica
     * @param valoreMassimo  il valore massimo consentito per la metrica
     * @throws IllegalArgumentException se il nome è nullo o vuoto, o se il valore minimo è maggiore o uguale al valore massimo
     */
    public MetricaGenerica(String nome, int valoreIniziale, int valoreMinimo, int valoreMassimo) {
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

    /**
     * Costruisce una nuova metrica generica con il nome specificato e il valore iniziale.
     * Il valore minimo è impostato a 0 e il valore massimo a 100.
     * @param nome          il nome della metrica
     * @param valoreIniziale il valore iniziale della metrica
     */
    public MetricaGenerica(String nome, int valoreIniziale) {
        this(nome, valoreIniziale, 0, 100);
    }

    @Override public String getNome() { return nome; }
    @Override public int getValore() { return valore; }
    @Override public int getValoreMinimo() { return valoreMinimo; }
    @Override public int getValoreMassimo() { return valoreMassimo; }

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
