package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

import java.lang.reflect.Type;

/**
 * Serializza un'istanza di StatoPartita in un oggetto JSON.
 * Aggiunge il campo "tipoStato" al JSON con il nome dello stato della partita.
 */
public class StatoPartitaSerializer implements JsonSerializer<StatoPartita> {

    @Override
    public JsonElement serialize(StatoPartita src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tipoStato", src.getNomeStato());
        return jsonObject;
    }
}
