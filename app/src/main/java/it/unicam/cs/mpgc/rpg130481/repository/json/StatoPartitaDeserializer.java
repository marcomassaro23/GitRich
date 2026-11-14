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

    private final List<StatoPartita> statiRegistrati;

    public StatoPartitaDeserializer(StatoPartita statoDefault, List<RegolaStato> regole) {
        if (statoDefault == null) {
            throw new IllegalArgumentException("Lo stato di default non può essere nullo.");
        }

        List<StatoPartita> stati = new ArrayList<>();
        stati.add(statoDefault);

        if (regole != null) {
            for (RegolaStato regola : regole) {
                if (regola != null && regola.getStatoRisultante() != null) {
                    stati.add(regola.getStatoRisultante());
                }
            }
        }

        this.statiRegistrati = List.copyOf(stati);
    }

    @Override
    public StatoPartita deserialize(JsonElement json, Type tipo, JsonDeserializationContext contesto)
        throws JsonParseException {
        JsonObject jsonStato = json.getAsJsonObject();

        if (!jsonStato.has("tipoStato")) {
            throw new JsonParseException("Campo 'tipoStato' mancante nel JSON.");
        }

        String codiceStato = jsonStato.get("tipoStato").getAsString();

        return statiRegistrati
            .stream()
            .filter(stato -> stato.getCodice().equalsIgnoreCase(codiceStato))
            .findFirst()
            .orElseThrow(() -> new JsonParseException("Stato partita non riconosciuto: " + codiceStato));
    }
}
