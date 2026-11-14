package it.unicam.cs.mpgc.rpg130481.repository.xml;

import it.unicam.cs.mpgc.rpg130481.model.core.eventi.EventoBase;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.OpzioneEventoBase;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.VariazioneMetrica;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlEventoMapper {

    public List<EventoEseguibile> parse(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList eventiNodi = document.getElementsByTagName("evento");
            return estraiElementi(eventiNodi).map(this::toEvento).toList();
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il parsing del flusso XML degli eventi", e);
        }
    }

    private EventoEseguibile toEvento(Element elEvento) {
        String titolo = getTestoTag(elEvento, "titolo");
        String descrizione = getTestoTag(elEvento, "descrizione");

        List<OpzioneEventoBase> opzioni = estraiElementi(elEvento.getElementsByTagName("opzione"))
            .map(this::toOpzione)
            .toList();

        return new EventoBase(titolo, descrizione, opzioni);
    }

    private OpzioneEventoBase toOpzione(Element elOpzione) {
        String testo = getTestoTag(elOpzione, "testo");

        List<VariazioneMetrica> modifiche = estraiElementi(elOpzione.getElementsByTagName("modificaMetrica"))
            .map(this::toVariazioneMetrica)
            .toList();

        return new OpzioneEventoBase(testo, modifiche);
    }

    private VariazioneMetrica toVariazioneMetrica(Element elModifica) {
        String metrica = elModifica.getAttribute("statistica");
        int valore = Integer.parseInt(elModifica.getAttribute("valore"));
        return new VariazioneMetrica(metrica, valore);
    }

    /**
     * Estrae gli elementi XML da un NodeList e li restituisce come uno Stream di Element.
     *
     * @param nodi Il NodeList contenente gli elementi XML.
     * @return Uno Stream di Element estratti dal NodeList.
     */
    private Stream<Element> estraiElementi(NodeList nodi) {
        return IntStream.range(0, nodi.getLength())
            .mapToObj(nodi::item)
            .filter(node -> node instanceof Element)
            .map(node -> (Element) node);
    }

    private String getTestoTag(Element elemento, String tagName) {
        NodeList nodi = elemento.getElementsByTagName(tagName);
        if (nodi.getLength() > 0) {
            return nodi.item(0).getTextContent();
        }
        return "";
    }
}
