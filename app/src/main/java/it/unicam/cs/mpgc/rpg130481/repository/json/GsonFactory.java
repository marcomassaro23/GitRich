package it.unicam.cs.mpgc.rpg130481.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.metriche.StatisticheAziendaModificabili;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.RegolaStato;
import it.unicam.cs.mpgc.rpg130481.model.interfaces.partita.stati.StatoPartita;

import java.util.List;

/**
 * Factory per creare istanze di Gson configurate con serializer e deserializer personalizzati.
 * Fornisce metodi per ottenere un'istanza di Gson configurata per serializzare e deserializzare
 * oggetti di tipo StatoPartita, EventoLeggibile e StatisticheAziendaModificabili
 */
public class GsonFactory {
    /**
     * Crea un'istanza di Gson configurata con serializer e deserializer personalizzati.
     *
     * @param statoDefault Lo stato di default da utilizzare per la deserializzazione.
     * @param regole       La lista di regole per la deserializzazione.
     * @return Un'istanza di Gson configurata.
     */
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
