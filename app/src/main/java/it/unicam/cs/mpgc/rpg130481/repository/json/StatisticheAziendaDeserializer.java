package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unicam.cs.mpgc.rpg130481.model.core.metriche.MetricaBase;
import it.unicam.cs.mpgc.rpg130481.model.core.metriche.StatisticheAziendaBase;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.MetricaModificabile;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StatisticheAziendaDeserializer implements JsonDeserializer<StatisticheAziendaModificabili> {

    @Override
    public StatisticheAziendaModificabili deserialize(
        JsonElement jsonElemento,
        Type tipoDestinazione,
        JsonDeserializationContext contesto
    ) throws JsonParseException {
        JsonObject oggettoJson = jsonElemento.getAsJsonObject();
        JsonArray arrayMetricheJson = oggettoJson.getAsJsonArray("metriche");

        List<MetricaModificabile> listaMetriche = new ArrayList<>();

        for (JsonElement elementoMetrica : arrayMetricheJson) {
            listaMetriche.add(estraiMetricaDaJson(elementoMetrica.getAsJsonObject()));
        }

        return new StatisticheAziendaBase(listaMetriche);
    }

    private MetricaModificabile estraiMetricaDaJson(JsonObject metricaJsonObj) {
        String nomeMetrica = metricaJsonObj.get("nome").getAsString();
        int valoreMetrica = metricaJsonObj.get("valore").getAsInt();

        if (metricaJsonObj.has("valoreMinimo") && metricaJsonObj.has("valoreMassimo")) {
            int min = metricaJsonObj.get("valoreMinimo").getAsInt();
            int max = metricaJsonObj.get("valoreMassimo").getAsInt();
            return new MetricaBase(nomeMetrica, valoreMetrica, min, max);
        }

        return new MetricaBase(nomeMetrica, valoreMetrica);
    }
}
