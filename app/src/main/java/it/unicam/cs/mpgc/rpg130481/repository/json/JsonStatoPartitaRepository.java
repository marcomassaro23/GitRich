package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.repository.interfaces.StatoPartitaRepository;
import it.unicam.cs.mpgc.rpg130481.utility.PathResolver;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonStatoPartitaRepository implements StatoPartitaRepository {

    private final String percorsoFileJson;
    private final Gson gson;

    public JsonStatoPartitaRepository(String percorsoFileJson, StatoPartita statoDefault, List<RegolaStato> regole) {
        if (percorsoFileJson == null || percorsoFileJson.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        this.percorsoFileJson = percorsoFileJson;
        this.gson = GsonFactory.creaGsonConfigurato(statoDefault, regole);
    }

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
        File file = PathResolver.trovaFileLettura(percorsoFileJson);
        if (file == null || !file.exists()) {
            throw new RuntimeException("Impossibile trovare il file di stato: " + percorsoFileJson);
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, StatoGiocoDto.class);
        } catch (Exception e) {
            throw new RuntimeException(
                "Errore durante il caricamento dello stato da file JSON: " + percorsoFileJson,
                e
            );
        }
    }

    @Override
    public void salva(StatoGiocoDto stato) {
        if (stato == null) {
            throw new IllegalArgumentException("Lo stato da salvare non può essere nullo.");
        }

        File file = PathResolver.risolviFileScrittura(percorsoFileJson);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            gson.toJson(stato, writer);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il salvataggio dello stato su JSON: " + file.getPath(), e);
        }
    }
}
