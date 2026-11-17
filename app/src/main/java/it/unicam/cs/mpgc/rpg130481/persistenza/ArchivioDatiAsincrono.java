package it.unicam.cs.mpgc.rpg130481.persistenza;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArchivioDatiAsincrono<T> implements ArchivioDati<T>, AutoCloseable {

    private final ArchivioDati<T> archivio;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile T ultimoDato;

    public ArchivioDatiAsincrono(ArchivioDati<T> archivio) {
        this.archivio = Objects.requireNonNull(archivio, "L'archivio non può essere nullo.");
    }

    @Override
    public T carica() {
        return ultimoDato != null ? ultimoDato : archivio.carica();
    }

    @Override
    public void salva(T dati) {
        Objects.requireNonNull(dati, "I dati da salvare non possono essere nulli.");
        this.ultimoDato = dati;
        if (!executor.isShutdown()) {
            executor.execute(() -> archivio.salva(dati));
        }
    }

    @Override
    public void close() {
        executor.close();
    }
}
