package dev.mudkip.workbench.api.mappings.remapper;

import java.io.BufferedReader;
import java.nio.file.Path;

public interface RemapperService {
    void remap(Path input, Path output, String currentNs, String targetNs, BufferedReader mappings);
}
