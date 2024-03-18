package dev.mudkip.workbench.api.decompiler;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.decompiler.DirectoryResultSaver;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public enum Decompiler implements DecompilerService {
    VINEFLOWER {
        @Override
        public void decompile(Path input, Path output, Path... libraries) {
            Map<String, Object> props = new HashMap<>();
            Fernflower fernflower = new Fernflower(
                    new DirectoryResultSaver(output.toFile()),
                    props,
                    new FernFlowerLogger()
            );
            fernflower.addSource(input.toFile());
            fernflower.decompileContext();
        }
    },
    CFR {
        @Override
        public void decompile(Path input, Path output, Path... libraries) {
            throw new NotImplementedException();
        }
    },
    PROCYON {
        @Override
        public void decompile(Path input, Path output, Path... libraries) {
            throw new NotImplementedException();
        }
    };

    static class FernFlowerLogger extends IFernflowerLogger {

        private Logger logger = LoggerFactory.getLogger("Fernflower");

        @Override
        public void writeMessage(String s, Severity severity) {
            writeMessage(s, severity, null);
        }

        @Override
        public void writeMessage(String s, Severity severity, Throwable throwable) {
            if (severity.ordinal() > Severity.INFO.ordinal()) {
                if (throwable != null) {
                    logger.error(s, throwable);
                } else {
                    logger.error(s);
                }
            }
        }
    }
}
