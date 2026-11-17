package it.unicam.cs.mpgc.rpg130481.persistenza;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class RisolutoreRisorse {

    private RisolutoreRisorse() {}

    public static InputStream apriStreamLettura(String percorso) throws IOException {
        Path fileSuDisco = Path.of(percorso);
        if (Files.exists(fileSuDisco)) {
            return Files.newInputStream(fileSuDisco);
        }

        InputStream risorsaClasspath = RisolutoreRisorse.class
                .getClassLoader()
                .getResourceAsStream(percorso);
        if (risorsaClasspath != null) {
            return risorsaClasspath;
        }

        throw new FileNotFoundException("File o risorsa non trovata: " + percorso);
    }
}
