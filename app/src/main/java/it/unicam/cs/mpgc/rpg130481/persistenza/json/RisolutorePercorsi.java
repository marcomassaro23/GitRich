package it.unicam.cs.mpgc.rpg130481.persistenza.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

final class RisolutorePercorsi {

    private static final String PREFISSO_MODULO = "app/";

    private RisolutorePercorsi() {
    }

    static InputStream apriStreamLettura(String percorsoRelativo) throws IOException {
        File fileLocale = Stream.of(percorsoRelativo, calcolaPercorsoAlternativo(percorsoRelativo))
                .map(File::new)
                .filter(File::exists)
                .findFirst()
                .orElse(null);

        if (fileLocale != null) {
            return new FileInputStream(fileLocale);
        }

        InputStream risorsaClasspath = RisolutorePercorsi.class
                .getClassLoader()
                .getResourceAsStream(percorsoRelativo);
        if (risorsaClasspath != null) {
            return risorsaClasspath;
        }

        throw new IllegalStateException("Impossibile trovare il file di stato: " + percorsoRelativo);
    }

    static File risolviPercorsoDestinazione(String percorsoRelativo) {
        boolean esecuzioneInternaAlModulo = percorsoRelativo.startsWith(PREFISSO_MODULO)
                && !new File("app").isDirectory();
        String percorsoRisolto = esecuzioneInternaAlModulo
                ? percorsoRelativo.substring(PREFISSO_MODULO.length())
                : percorsoRelativo;
        return new File(percorsoRisolto);
    }

    private static String calcolaPercorsoAlternativo(String percorso) {
        return percorso.startsWith(PREFISSO_MODULO)
                ? percorso.substring(PREFISSO_MODULO.length())
                : PREFISSO_MODULO + percorso;
    }
}
