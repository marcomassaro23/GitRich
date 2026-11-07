package it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi;

/**
 * Interfaccia che rappresenta una modifica a una metrica.
 * Contiene il nome della metrica e la variazione da applicare.
 */
public interface ModificaMetrica {
    /**
     * Restituisce il nome della metrica da modificare.
     *
     * @return il nome della metrica
     */
    String nomeMetrica();

    /**
     * Restituisce la variazione da applicare alla metrica.
     *
     * @return la variazione da applicare
     */
    int variazione();
}
