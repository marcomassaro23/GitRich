package it.unicam.cs.mpgc.rpg130481.persistenza;

import it.unicam.cs.mpgc.rpg130481.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130481.model.partita.Partita;
import it.unicam.cs.mpgc.rpg130481.model.partita.StatoGiocoDto;
import java.util.List;
import java.util.Objects;

public class GestorePersistenzaPartita implements ArchivioPartita, AutoCloseable {

    private final ArchivioDati<DatiSalvataggioPartita> salvataggioRepository;
    private final SorgenteDati<DatiSalvataggioPartita> sorgenteDefault;
    private final List<Evento> mazzoEventi;
    private final MapperSalvataggioPartita mapper;

    public GestorePersistenzaPartita() {
        this(
                new ArchivioDatiAsincrono<>(new JsonStatoPartitaRepository("saves/salvataggio_partita.json")),
                new JsonStatoPartitaRepository("data/stato_partita_default.json"),
                new XmlEventoRepository("data/eventi.xml"),
                new MapperSalvataggioPartita());
    }

    public GestorePersistenzaPartita(
            ArchivioDati<DatiSalvataggioPartita> salvataggioRepository,
            SorgenteDati<DatiSalvataggioPartita> sorgenteDefault,
            SorgenteDati<List<Evento>> eventoRepository,
            MapperSalvataggioPartita mapper) {
        this.salvataggioRepository = Objects.requireNonNull(salvataggioRepository);
        this.sorgenteDefault = Objects.requireNonNull(sorgenteDefault);
        this.mazzoEventi = List.copyOf(Objects.requireNonNull(eventoRepository).carica());
        this.mapper = Objects.requireNonNull(mapper);
    }

    @Override
    public boolean esistePartitaRiprendibile() {
        try {
            return !salvataggioRepository.carica().isTerminata();
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public Partita creaNuovaPartita() {
        return mapper.versoPartita(sorgenteDefault.carica(), mazzoEventi);
    }

    @Override
    public Partita riprendiPartitaSalvata() {
        return mapper.versoPartita(salvataggioRepository.carica(), mazzoEventi);
    }

    @Override
    public void salvaStato(StatoGiocoDto stato) {
        salvataggioRepository.salva(mapper.versoDatiSalvataggio(stato));
    }

    @Override
    public void close() throws Exception {
        if (salvataggioRepository instanceof AutoCloseable closeable) {
            closeable.close();
        }
    }
}
