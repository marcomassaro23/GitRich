package it.unicam.cs.mpgc.rpg130481.persistenza.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.unicam.cs.mpgc.rpg130481.controller.StatoGiocoDto;
import it.unicam.cs.mpgc.rpg130481.persistenza.DatiSalvataggioPartita;
import it.unicam.cs.mpgc.rpg130481.persistenza.StatoPartitaRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonStatoPartitaRepository implements StatoPartitaRepository {

    private final String percorsoFile;
    private final Gson gson;
    private final SalvataggioPartitaMapper mapper;

    public JsonStatoPartitaRepository(String percorsoFile) {
        this(percorsoFile, new GsonBuilder().setPrettyPrinting().create(), new SalvataggioPartitaMapper());
    }

    public JsonStatoPartitaRepository(String percorsoFile, Gson gson, SalvataggioPartitaMapper mapper) {
        if (percorsoFile == null || percorsoFile.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file JSON non può essere nullo o vuoto.");
        }
        this.percorsoFile = percorsoFile;
        this.gson = Objects.requireNonNull(gson, "L'istanza di Gson non può essere nulla.");
        this.mapper = Objects.requireNonNull(mapper, "Il mapper non può essere nullo.");
    }

    @Override
    public DatiSalvataggioPartita carica() {
        File sorgente = RisolutorePercorsi.cercaFileEsistente(percorsoFile);
        if (sorgente == null || !sorgente.exists()) {
            throw new RuntimeException("Impossibile trovare il file di stato: " + percorsoFile);
        }

        try (Reader reader = new FileReader(sorgente, StandardCharsets.UTF_8)) {
            DatiSalvataggioPartita dati = gson.fromJson(reader, DatiSalvataggioPartita.class);
            return mapper.valida(dati);
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
            DatiSalvataggioPartita dati = mapper.daDto(stato);
            gson.toJson(dati, writer);
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
