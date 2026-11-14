package it.unicam.cs.mpgc.rpg130481.repository.xml;

import it.unicam.cs.mpgc.rpg130481.model.core.eventi.EventoGenerico;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.OpzioneEventoBase;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.VariazioneMetrica;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.EventoEseguibile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Mapper per convertire i dati XML in oggetti Evento.
 * Fornisce metodi per eseguire il parsing di un flusso di input XML e creare oggetti Evento corrispondenti.
 */
public class XmlEventoMapper {

    /**
     * Esegue il parsing di un flusso di input XML e restituisce una lista di oggetti Evento.
     *
     * @param inputStream Il flusso di input contenente i dati XML degli eventi.
     * @return Una lista di oggetti Evento creati a partire dai dati XML.
     * @throws RuntimeException se si verifica un errore durante il parsing del flusso XML.
     */
    public List<EventoEseguibile> parse(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList eventiNodi = document.getElementsByTagName("evento");
            return estraiElementi(eventiNodi)
                    .map(this::toEvento)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il parsing del flusso XML degli eventi", e);
        }
    }

    /**
     * Converte un elemento XML in un oggetto Evento.
     *
     * @param elEvento L'elemento XML rappresentante un evento.
     * @return L'oggetto Evento corrispondente all'elemento XML.
     */
    private EventoEseguibile toEvento(Element elEvento) {
        String titolo = getTestoTag(elEvento, "titolo");
        String descrizione = getTestoTag(elEvento, "descrizione");

        List<OpzioneEventoBase> opzioni = estraiElementi(elEvento.getElementsByTagName("opzione"))
                .map(this::toOpzione)
                .toList();

        return new EventoGenerico(titolo, descrizione, opzioni);
    }

    /**
     * Converte un elemento XML in un oggetto OpzioneEvento.
     *
     * @param elOpzione L'elemento XML rappresentante un'opzione di evento.
     * @return L'oggetto OpzioneEvento corrispondente all'elemento XML.
     */
    private OpzioneEventoBase toOpzione(Element elOpzione) {
        String testo = getTestoTag(elOpzione, "testo");

        List<VariazioneMetrica> modifiche = estraiElementi(elOpzione.getElementsByTagName("modificaMetrica"))
                .map(this::toVariazioneMetrica)
                .toList();

        return new OpzioneEventoBase(testo, modifiche);
    }

    /**
     * Converte un elemento XML in un oggetto VariazioneMetrica.
     *
     * @param elModifica L'elemento XML rappresentante una modifica di metrica.
     * @return L'oggetto VariazioneMetrica corrispondente all'elemento XML.
     */
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

    /**
     * Ottiene il testo contenuto in un tag specifico di un elemento XML.
     *
     * @param elemento Il elemento XML da cui estrarre il testo.
     * @param tagName  Il nome del tag da cui ottenere il testo.
     * @return Il testo contenuto nel tag specificato, o una stringa vuota se il tag non è presente.
     */
    private String getTestoTag(Element elemento, String tagName) {
        NodeList nodi = elemento.getElementsByTagName(tagName);
        if (nodi.getLength() > 0) {
            return nodi.item(0).getTextContent();
        }
        return "";
    }
}
