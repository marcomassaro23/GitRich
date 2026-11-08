package it.unicam.cs.mpgc.rpg130481.repository.xml;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.repository.interfaces.EventoRepository;

import java.io.File;
import java.io.FileInputStream;
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
        if (mapper == null) {
            new XmlEventoRepository(percorsoFileXml);
        }
        this.mapper = mapper;
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
    public List<Evento> carica() {
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream(percorsoFileXml);
            if (is == null) {
                File file = new File(percorsoFileXml);
                if (file.exists()) {
                    is = new FileInputStream(file);
                }
            }

            if (is == null) {
                throw new IllegalStateException("File XML non trovato: " + percorsoFileXml);
            }

            return mapper.parse(is);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento degli eventi XML: " + percorsoFileXml, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ignored) {}
            }
        }
    }
}
