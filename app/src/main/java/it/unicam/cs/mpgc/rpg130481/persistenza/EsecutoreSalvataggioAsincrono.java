package it.unicam.cs.mpgc.rpg130481.persistenza;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EsecutoreSalvataggioAsincrono implements EsecutoreSalvataggio {

    private static final Logger LOGGER = Logger.getLogger(EsecutoreSalvataggioAsincrono.class.getName());
    private static final int TIMEOUT_ARRESTO_SECONDI = 2;

    private final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "PartitaController-IOThread");
        thread.setDaemon(true);
        return thread;
    });

    @Override
    public void accodaSalvataggio(Runnable operazioneSalvataggio) {
        if (executor.isShutdown()) {
            return;
        }

        executor.submit(() -> {
            try {
                operazioneSalvataggio.run();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Errore nel salvataggio asincrono della partita", e);
            }
        });
    }

    @Override
    public void arrestaEAttendi() {
        if (executor.isShutdown()) {
            return;
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(TIMEOUT_ARRESTO_SECONDI, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
