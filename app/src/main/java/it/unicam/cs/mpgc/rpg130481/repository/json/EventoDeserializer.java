package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.EventoBase;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.OpzioneEventoBase;
import it.unicam.cs.mpgc.rpg130481.model.core.eventi.VariazioneMetrica;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventoDeserializer implements JsonDeserializer<EventoBase> {

    @Override
    public EventoBase deserialize(JsonElement json, Type tipo, JsonDeserializationContext contesto)
        throws JsonParseException {
        if (json == null || !json.isJsonObject()) {
            return null;
        }

        JsonObject jsonEvento = json.getAsJsonObject();
        String titolo = leggiTesto(jsonEvento, "titolo", "Evento");
        String descrizione = leggiTesto(jsonEvento, "descrizione", "");

        JsonArray opzioniJson = estraiArrayOpzioni(jsonEvento);
        if (opzioniJson == null || opzioniJson.isEmpty()) {
            return null;
        }

        List<OpzioneEventoBase> opzioni = new ArrayList<>();
        for (JsonElement elemento : opzioniJson) {
            if (elemento.isJsonObject()) {
                opzioni.add(leggiOpzione(elemento.getAsJsonObject()));
            }
        }

        return new EventoBase(titolo, descrizione, opzioni);
    }

    private JsonArray estraiArrayOpzioni(JsonObject jsonEvento) {
        if (jsonEvento.has("opzioni")) {
            return jsonEvento.getAsJsonArray("opzioni");
        }
        if (jsonEvento.has("opzioniLeggibili")) {
            return jsonEvento.getAsJsonArray("opzioniLeggibili");
        }
        return null;
    }

    private OpzioneEventoBase leggiOpzione(JsonObject jsonOpzione) {
        String testo = leggiTesto(jsonOpzione, "testo", "");
        List<VariazioneMetrica> variazioni = new ArrayList<>();

        if (jsonOpzione.has("modifiche")) {
            for (JsonElement elemento : jsonOpzione.getAsJsonArray("modifiche")) {
                if (elemento.isJsonObject()) {
                    variazioni.add(leggiVariazioneMetrica(elemento.getAsJsonObject()));
                }
            }
        }

        return new OpzioneEventoBase(testo, variazioni);
    }

    private VariazioneMetrica leggiVariazioneMetrica(JsonObject jsonVariazione) {
        String nomeMetrica = leggiTesto(jsonVariazione, "nomeMetrica", "");
        int delta = jsonVariazione.has("variazione") ? jsonVariazione.get("variazione").getAsInt() : 0;
        return new VariazioneMetrica(nomeMetrica, delta);
    }

    private String leggiTesto(JsonObject sorgente, String chiave, String valorePredefinito) {
        return sorgente.has(chiave) ? sorgente.get(chiave).getAsString() : valorePredefinito;
    }
}
