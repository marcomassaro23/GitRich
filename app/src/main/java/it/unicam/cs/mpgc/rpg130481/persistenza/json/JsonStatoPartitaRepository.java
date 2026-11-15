package it.unicam.cs.mpgc.rpg130481.persistenza.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio.StatoPartitaRepository;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonStatoPartitaRepository implements StatoPartitaRepository {

    private final String percorsoFile;
    private final Gson gson;

    public JsonStatoPartitaRepository(String percorsoFile) {
        this(percorsoFile, new GsonBuilder().setPrettyPrinting().create());
    }

    public JsonStatoPartitaRepository(String percorsoFile, Gson gson) {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        this.percorsoFile = percorsoFile;
        this.gson = Objects.requireNonNull(gson, "L'istanza di Gson non può essere nulla.");
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
    public void salva(StatoGiocoDto stato) {
        Objects.requireNonNull(stato, "Lo stato da salvare non può essere nullo.");

        File destinazione = RisolutorePercorsi.risolviPercorsoDestinazione(percorsoFile);
        File cartellaPadre = destinazione.getParentFile();
        if (cartellaPadre != null && !cartellaPadre.exists()) {
            cartellaPadre.mkdirs();
        }

        try (Writer writer = new FileWriter(destinazione, StandardCharsets.UTF_8)) {
            gson.toJson(DatiSalvataggioPartita.daDto(stato), writer);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore durante il salvataggio dello stato su JSON: " + destinazione.getPath(),
                    e);
        }
    }
}
