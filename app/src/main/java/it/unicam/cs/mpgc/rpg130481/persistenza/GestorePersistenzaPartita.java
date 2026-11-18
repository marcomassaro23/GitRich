package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import java.util.List;
import java.util.Objects;

public class GestorePersistenzaPartita implements ArchivioPartita {

    private final ArchivioDati<ContenitoreDati> salvataggioRepository;
    private final SorgenteDati<ContenitoreDati> sorgenteDefault;
    private final SerializzatorePartita serializzatore;
    private final List<Evento> mazzoEventi;

    public GestorePersistenzaPartita() {
        this(
                new JsonStatoPartitaRepository("saves/salvataggio_partita.json"),
                new JsonStatoPartitaRepository("data/stato_partita_default.json"),
                new XmlEventoRepository("data/eventi.xml"),
                new SerializzatorePartita());
    }

    public GestorePersistenzaPartita(
            ArchivioDati<ContenitoreDati> salvataggioRepository,
            SorgenteDati<ContenitoreDati> sorgenteDefault,
            SorgenteDati<List<Evento>> eventoRepository) {
        this(salvataggioRepository, sorgenteDefault, eventoRepository, new SerializzatorePartita());
    }

    public GestorePersistenzaPartita(
            ArchivioDati<ContenitoreDati> salvataggioRepository,
            SorgenteDati<ContenitoreDati> sorgenteDefault,
            SorgenteDati<List<Evento>> eventoRepository,
            SerializzatorePartita serializzatore) {
        this.salvataggioRepository = Objects.requireNonNull(salvataggioRepository, "Repository salvataggio nullo.");
        this.sorgenteDefault = Objects.requireNonNull(sorgenteDefault, "Sorgente default nulla.");
        this.serializzatore = Objects.requireNonNull(serializzatore, "Serializzatore partita nullo.");
        this.mazzoEventi = List.copyOf(Objects.requireNonNull(eventoRepository, "Repository eventi nullo.").carica());
    }

    @Override
    public boolean esistePartitaRiprendibile() {
        try {
            return !riprendiPartitaSalvata().isTerminata();
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public Partita creaNuovaPartita() {
        return serializzatore.deserializza(sorgenteDefault.carica(), mazzoEventi);
    }

    @Override
    public Partita riprendiPartitaSalvata() {
        return serializzatore.deserializza(salvataggioRepository.carica(), mazzoEventi);
    }

    @Override
    public void salvaPartita(Partita partita) {
        salvataggioRepository.salva(serializzatore.serializza(partita));
    }
}
