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

public class JsonStatoPartitaRepository implements ArchivioDati<DatiSalvataggioPartita> {

    private final String percorsoFile;
    private final Gson gson;

    public JsonStatoPartitaRepository(String percorsoFile) {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        this.percorsoFile = percorsoFile;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public DatiSalvataggioPartita carica() {
        try (InputStream stream = RisolutoreRisorse.apriStreamLettura(percorsoFile);
                Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            DatiSalvataggioPartita dati = gson.fromJson(reader, DatiSalvataggioPartita.class);
            if (dati == null || !dati.isValido()) {
                throw new IllegalArgumentException("Dati di salvataggio incompleti o corrotti.");
            }
            return dati;
        } catch (IOException | RuntimeException e) {
            throw new IllegalStateException("Errore durante il caricamento JSON: " + percorsoFile, e);
        }
    }

    @Override
    public void salva(DatiSalvataggioPartita dati) {
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
