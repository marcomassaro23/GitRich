package it.unicam.cs.mpgc.rpg130481.repository.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

/**
 * Adapter personalizzato per la serializzazione e deserializzazione di oggetti StatoPartita.
 * Questo adapter gestisce la conversione tra l'oggetto StatoPartita e il suo rappresentazione JSON.
 */
public class StatoPartitaAdapter extends TypeAdapter<StatoPartita> {
    private final List<StatoPartita> statiDisponibili;

    /**
     * Costruttore dell'adapter che accetta uno stato di default e una lista di regole.
     * Gli stati disponibili saranno derivati dallo stato di default e dagli stati associati alle regole.
     *
     * @param statoDefault Lo stato di default da utilizzare.
     * @param regole       La lista di regole da cui derivare ulteriori stati disponibili.
     */
    public StatoPartitaAdapter(StatoPartita statoDefault, List<RegolaStato> regole) {
        if (statoDefault == null) {
            throw new IllegalArgumentException("Lo stato di default non può essere nullo.");
        }

        List<StatoPartita> lista = new ArrayList<>();
        lista.add(statoDefault);

        if (regole != null) {
            for (RegolaStato regola : regole) {
                if (regola != null && regola.getStatoAssociato() != null) {
                    lista.add(regola.getStatoAssociato());
                }
            }
        }

        this.statiDisponibili = List.copyOf(lista);
    }

    @Override
    public void write(JsonWriter out, StatoPartita value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("tipoStato").value(value.getNomeStato());
        out.endObject();
    }

    @Override
    public StatoPartita read(JsonReader in) throws IOException {
        in.beginObject();
        String tipoStato = null;

        while (in.hasNext()) {
            if ("tipoStato".equals(in.nextName())) {
                tipoStato = in.nextString();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        if (tipoStato == null) {
            throw new JsonParseException("Campo 'tipoStato' mancante nel JSON.");
        }

        for (StatoPartita stato : statiDisponibili) {
            if (stato.getNomeStato().equalsIgnoreCase(tipoStato)) {
                return stato;
            }
        }

        throw new JsonParseException("Stato partita non riconosciuto: " + tipoStato);
    }
}
