package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import java.lang.reflect.Type;

public class StatoPartitaSerializer implements JsonSerializer<StatoPartita> {

    @Override
    public JsonElement serialize(StatoPartita stato, Type tipo, JsonSerializationContext contesto) {
        if (stato == null) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonStato = new JsonObject();
        jsonStato.addProperty("tipoStato", stato.getCodice());
        return jsonStato;
    }
}
