package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.eventi.EventoBase;
import it.unicam.cs.mpgc.rpg130481.model.eventi.OpzioneEvento;
import it.unicam.cs.mpgc.rpg130481.model.eventi.VariazioneMetrica;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlEventoRepository implements SorgenteDati<List<Evento>> {

    private final String percorsoFileXml;

    public XmlEventoRepository(String percorsoFileXml) {
        if (percorsoFileXml == null || percorsoFileXml.isBlank()) {
            throw new IllegalArgumentException("Il percorso XML non può essere nullo o vuoto.");
        }
        this.percorsoFileXml = percorsoFileXml;
    }

    @Override
    public List<Evento> carica() {
        Document documento = caricaDocumento();
        return elementiConTag(documento.getDocumentElement(), "evento").stream()
                .map(this::creaEvento)
                .toList();
    }

    private Document caricaDocumento() {
        try (InputStream stream = RisolutoreRisorse.apriStreamLettura(percorsoFileXml)) {
            Document documento = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            documento.getDocumentElement().normalize();
            return documento;
        } catch (Exception e) {
            throw new IllegalStateException("Errore durante il caricamento XML: " + percorsoFileXml, e);
        }
    }

    private Evento creaEvento(Element nodoEvento) {
        String titolo = testoDelTag(nodoEvento, "titolo");
        String descrizione = testoDelTag(nodoEvento, "descrizione");
        List<OpzioneEvento> opzioni = elementiConTag(nodoEvento, "opzione").stream()
                .map(this::creaOpzione)
                .toList();
        return new EventoBase(titolo, descrizione, opzioni);
    }

    private OpzioneEvento creaOpzione(Element nodoOpzione) {
        String testo = testoDelTag(nodoOpzione, "testo");
        List<VariazioneMetrica> variazioni = elementiConTag(nodoOpzione, "modificaMetrica").stream()
                .map(this::creaVariazione)
                .toList();
        return new OpzioneEvento(testo, variazioni);
    }

    private VariazioneMetrica creaVariazione(Element nodoModifica) {
        String statistica = nodoModifica.getAttribute("statistica");
        int delta = Integer.parseInt(nodoModifica.getAttribute("valore"));
        return new VariazioneMetrica(statistica, delta);
    }

    private List<Element> elementiConTag(Element genitore, String tag) {
        NodeList nodi = genitore.getElementsByTagName(tag);
        List<Element> risultati = new ArrayList<>(nodi.getLength());
        for (int i = 0; i < nodi.getLength(); i++) {
            if (nodi.item(i) instanceof Element elemento) {
                risultati.add(elemento);
            }
        }
        return risultati;
    }

    private String testoDelTag(Element genitore, String tag) {
        List<Element> elementi = elementiConTag(genitore, tag);
        return elementi.isEmpty() ? "" : elementi.getFirst().getTextContent().trim();
    }
}
