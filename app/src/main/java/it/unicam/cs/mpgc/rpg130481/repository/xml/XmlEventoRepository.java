package it.unicam.cs.mpgc.rpg130481.repository.xml;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.repository.interfaces.EventoRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Implementazione di un repository per la gestione degli eventi basata su file XML.
 * Utilizza un mapper per convertire i dati XML in oggetti Evento.
 */
public class XmlEventoRepository implements EventoRepository {
    private final String percorsoFileXml;
    private final XmlEventoMapper mapper;

    /**
     * Costruttore per XmlEventoRepository.
     *
     * @param percorsoFileXml Il percorso del file XML contenente gli eventi.
     * @param mapper          Il mapper utilizzato per convertire i dati XML in oggetti Evento.
     * @throws IllegalArgumentException se il percorso del file XML è nullo o vuoto.
     */
    public XmlEventoRepository(String percorsoFileXml, XmlEventoMapper mapper) {
        if (percorsoFileXml == null || percorsoFileXml.isBlank()) {
            throw new IllegalArgumentException("Il percorso XML non può essere nullo o vuoto.");
        }
        this.percorsoFileXml = percorsoFileXml;
        this.mapper = (mapper != null) ? mapper : new XmlEventoMapper();
    }

    /**
     * Costruttore per XmlEventoRepository che utilizza un mapper predefinito.
     *
     * @param percorsoFileXml Il percorso del file XML contenente gli eventi.
     */
    public XmlEventoRepository(String percorsoFileXml) {
        this(percorsoFileXml, new XmlEventoMapper());
    }

    @Override
    public List<EventoEseguibile> carica() {
        try (InputStream is = apriFlussoInput()) {
            return mapper.parse(is);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento degli eventi XML: " + percorsoFileXml, e);
        }
    }

    /**
     * Apre un flusso di input per leggere il file XML.
     * Cerca prima nel classpath e poi nel file system.
     *
     * @return un InputStream per leggere il file XML
     * @throws IOException se si verifica un errore durante l'apertura del flusso
     */
    private InputStream apriFlussoInput() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(percorsoFileXml);
        if (is != null) {
            return is;
        }

        File file = new File(percorsoFileXml);
        if (file.exists()) {
            return new FileInputStream(file);
        }

        throw new IllegalStateException("File XML non trovato né nel classpath né nel file system: " + percorsoFileXml);
    }
}
