package it.unicam.cs.mpgc.rpg130481.persistenza.xml;

import it.unicam.cs.mpgc.rpg130481.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.persistenza.EventoRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlEventoRepository implements EventoRepository {

    private final String percorsoFileXml;
    private final XmlEventoMapper mapper;

    public XmlEventoRepository(String percorsoFileXml, XmlEventoMapper mapper) {
        if (percorsoFileXml == null || percorsoFileXml.isBlank()) {
            throw new IllegalArgumentException("Il percorso XML non può essere nullo o vuoto.");
        }
        this.percorsoFileXml = percorsoFileXml;
        this.mapper = mapper != null ? mapper : new XmlEventoMapper();
    }

    public XmlEventoRepository(String percorsoFileXml) {
        this(percorsoFileXml, new XmlEventoMapper());
    }

    @Override
    public List<EventoEseguibile> carica() {
        try (InputStream stream = apriStreamSorgente()) {
            return mapper.leggiEventi(stream);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento degli eventi XML: " + percorsoFileXml, e);
        }
    }

    private InputStream apriStreamSorgente() throws IOException {
        InputStream risorsaClasspath = getClass().getClassLoader().getResourceAsStream(percorsoFileXml);
        if (risorsaClasspath != null) {
            return risorsaClasspath;
        }

        File fileLocale = new File(percorsoFileXml);
        if (fileLocale.exists()) {
            return new FileInputStream(fileLocale);
        }

        throw new IllegalStateException("File XML non trovato né nel classpath né nel file system: " + percorsoFileXml);
    }
}
