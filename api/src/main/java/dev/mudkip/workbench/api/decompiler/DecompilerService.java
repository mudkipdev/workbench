package dev.mudkip.workbench.api.decompiler;

import java.nio.file.Path;

public interface DecompilerService {
    Path decompile(Path input);
}
