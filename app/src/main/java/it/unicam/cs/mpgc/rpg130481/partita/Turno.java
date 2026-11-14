package it.unicam.cs.mpgc.rpg130481.partita;

public record Turno(int corrente, int massimo) {
    public Turno {
        if (corrente <= 0) {
            throw new IllegalArgumentException("Il numero del turno corrente deve essere positivo.");
        }
        if (massimo <= 0 || corrente > massimo) {
            throw new IllegalArgumentException("Il turno massimo non può essere minore del turno corrente.");
        }
    }

    public Turno successivo() {
        return new Turno(corrente + 1, massimo);
    }

    public boolean isUltimo() {
        return corrente == massimo;
    }
}
