package it.unicam.cs.mpgc.rpg130481.utility;

import java.io.File;
import java.util.stream.Stream;

/**
 * La classe PathResolver fornisce metodi statici per risolvere percorsi di file in modo flessibile.
 * Permette di trovare file da leggere e risolvere percorsi per la scrittura, considerando sottocartelle e percorsi alternativi.
 */
public class PathResolver {

    /**
     * Trova il file da leggere in base al percorso fornito. Se il percorso inizia con "app/", cerca anche nella sottocartella "app".
     * Se il file non viene trovato, restituisce null.
     *
     * @param path Il percorso del file da leggere.
     * @return Il file trovato o null se non esiste.
     */
    public static File trovaFileLettura(String path) {
        String percorsoAlternativo = path.startsWith("app/") ? path.substring(4) : "app/" + path;

        return Stream.of(
                path,
                percorsoAlternativo,
                "app/src/main/resources/" + path,
                "src/main/resources/" + path
        )
        .map(File::new)
        .filter(File::exists)
        .findFirst()
        .orElse(null);
    }

    /**
     * Risolve il percorso del file per la scrittura, considerando se il percorso inizia con "app/".
     * Se il percorso inizia con "app/" e la cartella "app" non esiste, rimuove "app/" dal percorso.
     * Altrimenti, restituisce il percorso così com'è.
     *
     * @param path Il percorso del file da risolvere.
     * @return Il file risolto per la scrittura.
     */
    public static File risolviFileScrittura(String path) {
        boolean inSottocartellaApp = path.startsWith("app/") && !new File("app").isDirectory();
        return new File(inSottocartellaApp ? path.substring(4) : path);
    }
}
