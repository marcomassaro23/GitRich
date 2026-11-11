package it.unicam.cs.mpgc.rpg130481.repository.json;

import it.unicam.cs.mpgc.rpg130481.model.core.metriche.MetricaGenerica;
import it.unicam.cs.mpgc.rpg130481.model.core.metriche.StatisticheAzienda;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.Metrica;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Deserializza un oggetto JSON in un'istanza di StatisticheAziendaModificabili.
 * Estrae le metriche dal JSON e le converte in oggetti Metrica concreti.
 */
public class StatisticheAziendaDeserializer implements JsonDeserializer<StatisticheAziendaModificabili> {

    @Override
    public StatisticheAziendaModificabili deserialize(JsonElement jsonElemento, Type tipoDestinazione, JsonDeserializationContext contesto) throws JsonParseException {
        JsonObject oggettoJson = jsonElemento.getAsJsonObject();
        JsonArray arrayMetricheJson = oggettoJson.getAsJsonArray("metriche");

        List<Metrica> listaMetriche = new ArrayList<>();

        for (JsonElement elementoMetrica : arrayMetricheJson) {
            listaMetriche.add(estraiMetricaDaJson(elementoMetrica.getAsJsonObject()));
        }

        return new StatisticheAzienda(listaMetriche);
    }

    /**
     * Estrae una metrica da un oggetto JSON e la converte in un'istanza di Metrica.
     *
     * @param metricaJsonObj L'oggetto JSON rappresentante la metrica.
     * @return Un'istanza di Metrica corrispondente ai dati del JSON.
     */
    private Metrica estraiMetricaDaJson(JsonObject metricaJsonObj) {
        String nomeMetrica = metricaJsonObj.get("nome").getAsString();
        int valoreMetrica = metricaJsonObj.get("valore").getAsInt();

        if (metricaJsonObj.has("valoreMinimo") && metricaJsonObj.has("valoreMassimo")) {
            int min = metricaJsonObj.get("valoreMinimo").getAsInt();
            int max = metricaJsonObj.get("valoreMassimo").getAsInt();
            return new MetricaGenerica(nomeMetrica, valoreMetrica, min, max);
        }

        return new MetricaGenerica(nomeMetrica, valoreMetrica);
    }
}
