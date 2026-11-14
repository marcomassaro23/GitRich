package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StatoPartitaDeserializer implements JsonDeserializer<StatoPartita> {

    private final List<StatoPartita> statiDisponibili;

    public StatoPartitaDeserializer(StatoPartita statoDefault, List<RegolaStato> regole) {
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
    public StatoPartita deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        if (!jsonObject.has("tipoStato")) {
            throw new JsonParseException("Campo 'tipoStato' mancante nel JSON.");
        }

        String tipoStato = jsonObject.get("tipoStato").getAsString();

        for (StatoPartita stato : statiDisponibili) {
            if (stato.getNomeStato().equalsIgnoreCase(tipoStato)) {
                return stato;
            }
        }

        throw new JsonParseException("Stato partita non riconosciuto: " + tipoStato);
    }
}
