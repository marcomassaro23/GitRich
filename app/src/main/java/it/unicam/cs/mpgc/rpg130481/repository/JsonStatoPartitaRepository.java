package it.unicam.cs.mpgc.rpg130481.repository;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stato.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stato.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.repository.interfaces.StatoPartitaRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Implementazione di StatoPartitaRepository che utilizza un file JSON per la persistenza dello stato della partita.
 * Questa classe gestisce il caricamento e il salvataggio dello stato della partita in formato JSON.
 */
public class JsonStatoPartitaRepository implements StatoPartitaRepository {
    private final String percorsoFileJson;
    private final Gson gson;

    /**
     * Costruttore che accetta il percorso del file JSON, lo stato di default e una lista di regole.
     * Utilizza GsonFactory per creare un'istanza di Gson configurata con l'adapter personalizzato per StatoPartita.
     *
     * @param percorsoFileJson Il percorso del file JSON dove salvare/caricare lo stato della partita.
     * @param statoDefault     Lo stato di default da utilizzare nell'adapter.
     * @param regole           La lista di regole da cui derivare ulteriori stati disponibili.
     */
    public JsonStatoPartitaRepository(String percorsoFileJson, StatoPartita statoDefault, List<RegolaStato> regole) {
        if (percorsoFileJson == null || percorsoFileJson.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        this.percorsoFileJson = percorsoFileJson;
        this.gson = GsonFactory.creaGsonConfigurato(statoDefault, regole);
    }

    /**
     * Costruttore che accetta il percorso del file JSON e un'istanza di Gson.
     * Questo costruttore consente di utilizzare un'istanza di Gson già configurata.
     *
     * @param percorsoFileJson Il percorso del file JSON dove salvare/caricare lo stato della partita.
     * @param gson             L'istanza di Gson da utilizzare per la serializzazione/deserializzazione.
     */
    public JsonStatoPartitaRepository(String percorsoFileJson, Gson gson) {
        if (percorsoFileJson == null || percorsoFileJson.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        if (gson == null) {
            throw new IllegalArgumentException("L'istanza di Gson non può essere nulla.");
        }
        this.percorsoFileJson = percorsoFileJson;
        this.gson = gson;
    }

    @Override
    public StatoGiocoDto carica() {
        try (Reader reader = new FileReader(percorsoFileJson, StandardCharsets.UTF_8)) {
            StatoGiocoDto dto = gson.fromJson(reader, StatoGiocoDto.class);
            if (dto == null) {
                throw new IllegalStateException("File JSON vuoto o non valido.");
            }
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento dello stato da JSON: " + percorsoFileJson, e);
        }
    }

    @Override
    public void salva(StatoGiocoDto stato) {
        if (stato == null) {
            throw new IllegalArgumentException("Lo stato da salvare non può essere nullo.");
        }
        try (Writer writer = new FileWriter(percorsoFileJson, StandardCharsets.UTF_8)) {
            gson.toJson(stato, writer);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il salvataggio dello stato su JSON: " + percorsoFileJson, e);
        }
    }
}
