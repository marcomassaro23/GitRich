package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg130481.model.core.partita.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import it.unicam.cs.mpgc.rpg130481.repository.interfaces.StatoPartitaRepository;
import it.unicam.cs.mpgc.rpg130481.utility.RisolutorePercorsi;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonStatoPartitaRepository implements StatoPartitaRepository {

    private final String percorsoFile;
    private final Gson gson;

    public JsonStatoPartitaRepository(String percorsoFile, StatoPartita statoDefault, List<RegolaStato> regole) {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        this.percorsoFile = percorsoFile;
        this.gson = GsonFactory.costruisciSerializerPartita(statoDefault, regole);
    }

    public JsonStatoPartitaRepository(String percorsoFile, Gson gson) {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        if (gson == null) {
            throw new IllegalArgumentException("L'istanza di Gson non può essere nulla.");
        }
        this.percorsoFile = percorsoFile;
        this.gson = gson;
    }

    @Override
    public StatoGiocoDto carica() {
        File sorgente = RisolutorePercorsi.cercaFileEsistente(percorsoFile);
        if (sorgente == null || !sorgente.exists()) {
            throw new RuntimeException("Impossibile trovare il file di stato: " + percorsoFile);
        }

        try (Reader reader = new FileReader(sorgente, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, StatoGiocoDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento dello stato da file JSON: " + percorsoFile, e);
        }
    }

    @Override
    public void salva(StatoGiocoDto stato) {
        if (stato == null) {
            throw new IllegalArgumentException("Lo stato da salvare non può essere nullo.");
        }

        File destinazione = RisolutorePercorsi.risolviPercorsoDestinazione(percorsoFile);
        assicuraCartellaEsistente(destinazione);

        try (Writer writer = new FileWriter(destinazione, StandardCharsets.UTF_8)) {
            gson.toJson(stato, writer);
        } catch (Exception e) {
            throw new RuntimeException(
                "Errore durante il salvataggio dello stato su JSON: " + destinazione.getPath(),
                e
            );
        }
    }

    private void assicuraCartellaEsistente(File file) {
        File cartellaPadre = file.getParentFile();
        if (cartellaPadre != null && !cartellaPadre.exists()) {
            cartellaPadre.mkdirs();
        }
    }
}
