package it.unicam.cs.mpgc.rpg130481.persistenza;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class JsonStatoPartitaRepository implements ArchivioDati<ContenitoreDati> {

    private final String percorsoFile;
    private final Gson gson;

    public JsonStatoPartitaRepository(String percorsoFile) {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        this.percorsoFile = percorsoFile;
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    }

    @Override
    public ContenitoreDati carica() {
        try (InputStream stream = RisolutoreRisorse.apriStreamLettura(percorsoFile);
                Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            ContenitoreDati dati = gson.fromJson(reader, ContenitoreDati.class);
            if (dati == null || dati.turno() == null || dati.metriche() == null) {
                throw new IllegalArgumentException("Dati di salvataggio incompleti o corrotti.");
            }
            return dati;
        } catch (IOException | RuntimeException e) {
            throw new IllegalStateException("Errore durante il caricamento JSON: " + percorsoFile, e);
        }
    }

    @Override
    public void salva(ContenitoreDati dati) {
        Objects.requireNonNull(dati, "I dati da salvare non possono essere nulli.");
        Path destinazione = Path.of(percorsoFile);
        try {
            if (destinazione.getParent() != null) {
                Files.createDirectories(destinazione.getParent());
            }
            Files.writeString(destinazione, gson.toJson(dati), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Errore durante il salvataggio JSON: " + destinazione, e);
        }
    }
}
