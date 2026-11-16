package it.unicam.cs.mpgc.rpg130481.persistenza.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.StatoPartitaRepository;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class JsonStatoPartitaRepository implements StatoPartitaRepository {

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
        try (
                InputStream stream = RisolutorePercorsi.apriStreamLettura(percorsoFile);
                Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            DatiSalvataggioPartita dati = gson.fromJson(reader, DatiSalvataggioPartita.class);
            if (dati == null) {
                throw new IllegalArgumentException("File JSON vuoto o non valido.");
            }
            return dati;
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento dello stato da file JSON: " + percorsoFile, e);
        }
    }

    @Override
    public void salva(DatiSalvataggioPartita dati) {
        Objects.requireNonNull(dati, "I dati da salvare non possono essere nulli.");

        Path destinazione = RisolutorePercorsi.risolviPercorsoDestinazione(percorsoFile).toPath();
        Path cartellaPadre = destinazione.getParent();
        Path fileTemporaneo = null;

        try {
            if (cartellaPadre != null) {
                Files.createDirectories(cartellaPadre);
                fileTemporaneo = Files.createTempFile(cartellaPadre, "salvataggio_", ".tmp");
            } else {
                fileTemporaneo = Files.createTempFile("salvataggio_", ".tmp");
            }

            try (Writer writer = Files.newBufferedWriter(fileTemporaneo, StandardCharsets.UTF_8)) {
                gson.toJson(dati, writer);
            }

            try {
                Files.move(
                        fileTemporaneo,
                        destinazione,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(fileTemporaneo, destinazione, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Errore durante il salvataggio dello stato su JSON: " + destinazione,
                    e);
        } finally {
            if (fileTemporaneo != null) {
                try {
                    Files.deleteIfExists(fileTemporaneo);
                } catch (IOException ignored) {
                }
            }
        }
    }
}
