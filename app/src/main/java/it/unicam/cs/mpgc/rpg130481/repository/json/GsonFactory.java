package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;
import java.util.List;

public class GsonFactory {

    public static Gson creaGsonConfigurato(StatoPartita statoDefault, List<RegolaStato> regole) {
        return new GsonBuilder()
            .registerTypeAdapter(StatoPartita.class, new StatoPartitaSerializer())
            .registerTypeAdapter(StatoPartita.class, new StatoPartitaDeserializer(statoDefault, regole))
            .registerTypeAdapter(StatisticheAziendaModificabili.class, new StatisticheAziendaDeserializer())
            .registerTypeAdapter(Evento.class, new EventoDeserializer())
            .setPrettyPrinting()
            .create();
    }
}
