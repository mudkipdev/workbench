package dev.mudkip.workbench.api.version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface VersionData {
    String getJson();

    String getMainClass();

    ArgumentListing getMinecraftArgs();

    ArgumentListing getJvmArgs();

    List<Library> getLibraries();

    String getId();

    Optional<ReleaseCycle> getType();

    Downloads getDownloads();

    record Library(String name, String url, boolean isRuntime) {
    }

    class ArgumentListing extends HashMap<String, String> {
        public String joined(Map<String, String> values) {
            return this.entrySet().stream().map(entry -> {
                StringBuilder builder = new StringBuilder(entry.getKey());
                if (entry.getValue() != null) {
                    String valueName = entry.getValue().replaceAll("[{}]", "");
                    if (values.containsKey(valueName)) {
                        builder.append(" ")
                                .append(values.get(valueName));
                    } else {
                        throw new IllegalStateException("Missing value for key-value " + valueName);
                    }
                }
                return builder.toString();
            }).collect(Collectors.joining(" "));
        }
    }

    record Downloads(String clientUrl, String serverUrl) {
        public boolean hasServer() {
            return serverUrl != null;
        }
    }
}
