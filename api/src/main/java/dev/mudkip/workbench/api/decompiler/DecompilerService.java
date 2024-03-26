package dev.mudkip.workbench.api.decompiler;

import java.nio.file.Path;
import java.util.Map;

public interface DecompilerService {
    /**
     * Decompiles a jar file into the specified output directory.
     * This action can be done by various decompilers, where Vineflower is recommended
     * as it is the most updated and feature-rich decompiler based on Fernflower.
     *
     * @param input           The input jar file
     * @param output          The output directory
     * @param overrideOptions Additional options to be used during decompilation
     * @param libraries       Additional libraries to be used during decompilation for better results
     * @see Decompiler
     */
    void decompile(Path input, Path output, Map<String, Object> overrideOptions, Path... libraries);
}
