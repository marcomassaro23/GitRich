package it.unicam.cs.mpgc.rpg130481.persistenza.xml;

import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoBase;
import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoEseguibile;
import it.unicam.cs.mpgc.rpg130481.model.eventi.OpzioneEvento;
import it.unicam.cs.mpgc.rpg130481.model.eventi.VariazioneMetrica;

public class XmlEventoMapper {

    public List<EventoEseguibile> leggiEventi(InputStream sorgente) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(sorgente);
            documento.getDocumentElement().normalize();

            NodeList nodiEvento = documento.getElementsByTagName("evento");
            return estraiElementi(nodiEvento).map(this::mappaEvento).toList();
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il parsing del flusso XML degli eventi", e);
        }
    }

    private EventoEseguibile mappaEvento(Element elementoEvento) {
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
