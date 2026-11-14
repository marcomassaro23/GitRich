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
    public EventoBase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        if (json == null || !json.isJsonObject()) {
            return null;
        }

        JsonObject oggettoJson = json.getAsJsonObject();

        String titolo = "Evento";
        if (oggettoJson.has("titolo")) {
            titolo = oggettoJson.get("titolo").getAsString();
        }

        String descrizione = "";
        if (oggettoJson.has("descrizione")) {
            descrizione = oggettoJson.get("descrizione").getAsString();
        }

        JsonArray arrayOpzioniJson = null;
        if (oggettoJson.has("opzioni")) {
            arrayOpzioniJson = oggettoJson.getAsJsonArray("opzioni");
        } else if (oggettoJson.has("opzioniLeggibili")) {
            arrayOpzioniJson = oggettoJson.getAsJsonArray("opzioniLeggibili");
        }

        if (arrayOpzioniJson == null || arrayOpzioniJson.isEmpty()) {
            return null;
        }

        List<OpzioneEventoBase> listaOpzioni = new ArrayList<>();
        for (JsonElement elementoOpzione : arrayOpzioniJson) {
            if (elementoOpzione.isJsonObject()) {
                listaOpzioni.add(estraiOpzioneDaJson(elementoOpzione.getAsJsonObject()));
            }
        }

        return new EventoBase(titolo, descrizione, listaOpzioni);
    }

    private OpzioneEventoBase estraiOpzioneDaJson(JsonObject opzioneJsonObj) {
        String testo = "";
        if (opzioneJsonObj.has("testo")) {
            testo = opzioneJsonObj.get("testo").getAsString();
        }

        List<VariazioneMetrica> listaModifiche = new ArrayList<>();
        if (opzioneJsonObj.has("modifiche")) {
            JsonArray arrayModificheJson = opzioneJsonObj.getAsJsonArray("modifiche");
            for (JsonElement elementoModifica : arrayModificheJson) {
                if (elementoModifica.isJsonObject()) {
                    listaModifiche.add(estraiVariazioneMetricaDaJson(elementoModifica.getAsJsonObject()));
                }
            }
        }

        return new OpzioneEventoBase(testo, listaModifiche);
    }

    private VariazioneMetrica estraiVariazioneMetricaDaJson(JsonObject modificaJsonObj) {
        String nomeMetrica = "";
        if (modificaJsonObj.has("nomeMetrica")) {
            nomeMetrica = modificaJsonObj.get("nomeMetrica").getAsString();
        }

        int variazione = 0;
        if (modificaJsonObj.has("variazione")) {
            variazione = modificaJsonObj.get("variazione").getAsInt();
        }

        return new VariazioneMetrica(nomeMetrica, variazione);
    }
}
