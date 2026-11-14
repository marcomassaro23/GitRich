package it.unicam.cs.mpgc.rpg130481.persistenza.json;

import java.io.File;
import java.util.stream.Stream;

final class RisolutorePercorsi {

    private static final String PREFISSO_MODULO = "app/";

    private RisolutorePercorsi() {}

    static File cercaFileEsistente(String percorsoRelativo) {
        String percorsoAlternativo = calcolaPercorsoAlternativo(percorsoRelativo);

        return Stream.of(
            percorsoRelativo,
            percorsoAlternativo,
            "app/src/main/resources/" + percorsoRelativo,
            "src/main/resources/" + percorsoRelativo
        )
            .map(File::new)
            .filter(File::exists)
            .findFirst()
            .orElse(null);
    }

    static File risolviPercorsoDestinazione(String percorsoRelativo) {
        boolean esecuzioneInternaAlModulo =
            percorsoRelativo.startsWith(PREFISSO_MODULO) && !new File("app").isDirectory();
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
