package dev.mudkip.workbench.api.mappings.remapper;

import net.fabricmc.tinyremapper.NonClassCopyMode;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.fabricmc.tinyremapper.TinyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

public enum Remapper implements RemapperService {
    TINY_REMAPPER {
        @Override
        public void remap(Path input, Path output, String currentNs, String targetNs, BufferedReader mappings) {
            TinyRemapper remapper = TinyRemapper.newRemapper()
                    .renameInvalidLocals(true)
                    .invalidLvNamePattern(Pattern.compile("\\$\\$\\d+"))
                    .inferNameFromSameLvIndex(true)
                    .withMappings(TinyUtils.createTinyMappingProvider(mappings, currentNs, targetNs))
                    .build();
            try (OutputConsumerPath outputConsumer = new OutputConsumerPath.Builder(output).build()) {
                outputConsumer.addNonClassFiles(input, NonClassCopyMode.FIX_META_INF, remapper);
                remapper.readInputs(input);
                remapper.apply(outputConsumer);
            } catch (IOException e) {
                throw new RuntimeException("Failed to remap " + input + " to " + output, e);
            } finally {
                remapper.finish();
            }
        }
    };
}
