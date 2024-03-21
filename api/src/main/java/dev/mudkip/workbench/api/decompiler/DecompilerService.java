package dev.mudkip.workbench.api.decompiler;

import java.nio.file.Path;

public interface DecompilerService {
    /**
     * Decompiles a jar file into the specified output directory.
     * This action can be done by various decompilers, where Vineflower is recommended
     * as it is the most updated and feature-rich decompiler based on Fernflower.
     *
     * @see Decompiler
     * @param input The input jar file
     * @param output The output directory
     * @param libraries Additional libraries to be used during decompilation for better results
     */
    void decompile(Path input, Path output, Path... libraries);
}
