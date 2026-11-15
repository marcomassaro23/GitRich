package it.unicam.cs.mpgc.rpg130481.persistenza.salvataggio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EsecutoreSalvataggioAsincrono {

    private static final int TIMEOUT_ARRESTO_SECONDI = 2;

    private final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "SalvataggioPartita-IOThread");
        thread.setDaemon(true);
        return thread;
    });

    public void accodaSalvataggio(Runnable operazioneSalvataggio) {
        if (!executor.isShutdown() && operazioneSalvataggio != null) {
            executor.submit(operazioneSalvataggio);
        }
    }

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
