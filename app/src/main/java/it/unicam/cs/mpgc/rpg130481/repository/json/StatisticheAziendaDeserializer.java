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
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAzienda;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StatisticheAziendaDeserializer implements JsonDeserializer<StatisticheAzienda> {

    private static final int MINIMO_DEFAULT = 0;
    private static final int MASSIMO_DEFAULT = 100;
    @Override
    public StatisticheAzienda deserialize(JsonElement json, Type tipo, JsonDeserializationContext contesto)
        throws JsonParseException {
        JsonObject jsonStatistiche = json.getAsJsonObject();
        JsonArray metricheJson = jsonStatistiche.getAsJsonArray("metriche");

        List<MetricaModificabile> metriche = new ArrayList<>();
        for (JsonElement elemento : metricheJson) {
            metriche.add(leggiMetrica(elemento.getAsJsonObject()));
        }

        return new StatisticheAziendaBase(metriche);
    }

    private MetricaModificabile leggiMetrica(JsonObject jsonMetrica) {
        String nome = jsonMetrica.get("nome").getAsString();
        int valore = jsonMetrica.get("valore").getAsInt();
        int minimo = jsonMetrica.has("valoreMinimo")
            ? jsonMetrica.get("valoreMinimo").getAsInt()
            : MINIMO_DEFAULT;
        int massimo = jsonMetrica.has("valoreMassimo")
            ? jsonMetrica.get("valoreMassimo").getAsInt()
            : MASSIMO_DEFAULT;
        boolean criticaAlMassimo = jsonMetrica.has("criticaAlMassimo")
            && jsonMetrica.get("criticaAlMassimo").getAsBoolean();

        return new MetricaBase(nome, valore, minimo, massimo, criticaAlMassimo);
    }
}
