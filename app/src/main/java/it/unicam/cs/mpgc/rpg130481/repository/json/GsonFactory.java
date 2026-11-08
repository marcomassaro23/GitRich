package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

import java.util.List;

/**
 * Factory per la creazione di istanze di Gson configurate con adattatori personalizzati.
 * Questa classe fornisce metodi statici per ottenere istanze di Gson con configurazioni specifiche.
 */
public class GsonFactory {

    /**
     * Crea un'istanza di Gson configurata con l'adapter personalizzato per StatoPartita.
     * L'adapter gestisce la serializzazione e deserializzazione degli oggetti StatoPartita.
     *
     * @param statoDefault Lo stato di default da utilizzare nell'adapter.
     * @param regole       La lista di regole da cui derivare ulteriori stati disponibili.
     * @return Un'istanza di Gson configurata con l'adapter personalizzato.
     */
    public static Gson creaGsonConfigurato(StatoPartita statoDefault, List<RegolaStato> regole) {
        return new GsonBuilder()
                .registerTypeAdapter(StatoPartita.class, new StatoPartitaAdapter(statoDefault, regole))
                .setPrettyPrinting()
                .create();
    }
}
