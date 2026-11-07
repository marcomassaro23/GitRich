package it.unicam.cs.mpgc.rpg130481.model;

public class MetricaGenerica implements Metrica {
    private final String nome;
    private int valore;

    public MetricaGenerica(String nome, int valoreIniziale) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della metrica non può essere nullo o vuoto.");
        }
        this.nome = nome;
        this.valore = Math.clamp(valoreIniziale, 0, 100);
    }

    @Override public String getNome() { return nome; }
    @Override public int getValore() { return valore; }
    @Override public int getValoreMinimo() { return 0; }
    @Override public int getValoreMassimo() { return 100; }

    @Override
    public boolean isCritica() {
        return isMin(); // Critica se scende a 0
    }

    @Override
    public int applicaVariazione(int delta) {
        this.valore = Math.clamp(this.valore + delta, 0, 100);
        return this.valore;
    }

    @Override
    public void setValoreAssoluto(int nuovoValore) {
        this.valore = Math.clamp(nuovoValore, 0, 100);
    }
}
