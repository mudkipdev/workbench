package dev.mudkip.workbench.api.mappings.remapper;

import java.io.BufferedReader;
import java.nio.file.Path;

public interface RemapperService {
    /**
     * Remaps the input jar file to the target namespace using the provided mappings.
     * This process might be done with different remapping strategies.
     *
     * @see Remapper
     * @param input The input jar file to be remapped
     * @param output The output jar file to be the remapped input content written to
     * @param currentNs The current namespace of the input jar file
     * @param targetNs The target namespace to remap the input jar file to
     * @param mappings Mappings used to remap the input jar file, containing both of the namespaces
     */
    void remap(Path input, Path output, String currentNs, String targetNs, BufferedReader mappings);
}
