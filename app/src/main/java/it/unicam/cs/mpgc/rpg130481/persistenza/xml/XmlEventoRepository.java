package it.unicam.cs.mpgc.rpg130481.persistenza.xml;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoBase;
import it.unicam.cs.mpgc.rpg130481.model.eventi.OpzioneEvento;
import it.unicam.cs.mpgc.rpg130481.model.eventi.VariazioneMetrica;
import it.unicam.cs.mpgc.rpg130481.persistenza.EventoRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlEventoRepository implements EventoRepository {

    private final String percorsoFileXml;

    public XmlEventoRepository(String percorsoFileXml) {
        if (percorsoFileXml == null || percorsoFileXml.isBlank()) {
            throw new IllegalArgumentException("Il percorso XML non può essere nullo o vuoto.");
        }
        this.percorsoFileXml = percorsoFileXml;
    }

    @Override
    public List<Evento> carica() {
        try (InputStream stream = apriStreamSorgente()) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documento = builder.parse(stream);
            documento.getDocumentElement().normalize();

            return estraiElementi(documento.getElementsByTagName("evento"))
                    .map(this::mappaEvento)
                    .toList();
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

    private Evento mappaEvento(Element elementoEvento) {
        String titolo = leggiContenutoTag(elementoEvento, "titolo");
        String descrizione = leggiContenutoTag(elementoEvento, "descrizione");
        List<OpzioneEvento> opzioni = estraiElementi(elementoEvento.getElementsByTagName("opzione"))
                .map(this::mappaOpzione)
                .toList();
        return new EventoBase(titolo, descrizione, opzioni);
    }

    private OpzioneEvento mappaOpzione(Element elementoOpzione) {
        String testo = leggiContenutoTag(elementoOpzione, "testo");
        List<VariazioneMetrica> variazioni = estraiElementi(elementoOpzione.getElementsByTagName("modificaMetrica"))
                .map(this::mappaVariazione)
                .toList();
        return new OpzioneEvento(testo, variazioni);
    }

    private VariazioneMetrica mappaVariazione(Element elementoModifica) {
        String nomeMetrica = elementoModifica.getAttribute("statistica");
        int delta = Integer.parseInt(elementoModifica.getAttribute("valore"));
        return new VariazioneMetrica(nomeMetrica, delta);
    }

    private Stream<Element> estraiElementi(NodeList nodi) {
        return IntStream.range(0, nodi.getLength())
                .mapToObj(nodi::item)
                .filter(Element.class::isInstance)
                .map(Element.class::cast);
    }

    private String leggiContenutoTag(Element genitore, String nomeTag) {
        NodeList nodi = genitore.getElementsByTagName(nomeTag);
        return nodi.getLength() > 0 ? nodi.item(0).getTextContent() : "";
    }
}
