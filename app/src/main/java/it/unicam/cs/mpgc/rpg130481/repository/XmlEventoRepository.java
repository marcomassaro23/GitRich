package it.unicam.cs.mpgc.rpg130481.repository;

import it.unicam.cs.mpgc.rpg130481.model.core.eventi.EventoGenerico;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.OpzioneEvento;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.VariazioneMetrica;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class XmlEventoRepository implements EventoRepository {
    private final String percorsoFileXml;

    public XmlEventoRepository(String percorsoFileXml) {
        if (percorsoFileXml == null || percorsoFileXml.isBlank()) {
            throw new IllegalArgumentException("Il percorso del file XML non può essere nullo o vuoto.");
        }
        this.percorsoFileXml = percorsoFileXml;
    }

    @Override
    public List<Evento> caricaTuttiGliEventi() {
        Document document = caricaDocumentoXml();
        return estraiElementi(document.getElementsByTagName("evento"))
                .map(this::creaEvento)
                .toList();
    }

    private Document caricaDocumentoXml() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(percorsoFileXml)) {
            if (inputStream == null) {
                throw new IllegalStateException("Impossibile trovare il file XML nel classpath: " + percorsoFileXml);
            }
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();
            return document;
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento/parsing del file XML: " + percorsoFileXml, e);
        }
    }

    private Evento creaEvento(Element elEvento) {
        String titolo = getTestoTag(elEvento, "titolo");
        String descrizione = getTestoTag(elEvento, "descrizione");

        List<OpzioneEvento> opzioni = estraiElementi(elEvento.getElementsByTagName("opzione"))
                .map(this::creaOpzione)
                .toList();

        return new EventoGenerico(titolo, descrizione, opzioni);
    }

    private OpzioneEvento creaOpzione(Element elOpzione) {
        String testo = getTestoTag(elOpzione, "testo");

        List<VariazioneMetrica> modifiche = estraiElementi(elOpzione.getElementsByTagName("modificaMetrica"))
                .map(this::creaVariazioneMetrica)
                .toList();

        return new OpzioneEvento(testo, modifiche);
    }

    private VariazioneMetrica creaVariazioneMetrica(Element elModifica) {
        String stat = elModifica.getAttribute("statistica");
        int val = Integer.parseInt(elModifica.getAttribute("valore"));
        return new VariazioneMetrica(stat, val);
    }

    private Stream<Element> estraiElementi(NodeList nodi) {
        return IntStream.range(0, nodi.getLength())
                .mapToObj(nodi::item)
                .filter(Element.class::isInstance)
                .map(Element.class::cast);
    }

    private String getTestoTag(Element elemento, String tagName) {
        NodeList nodi = elemento.getElementsByTagName(tagName);
        return nodi.getLength() > 0 ? nodi.item(0).getTextContent() : "";
    }
}
