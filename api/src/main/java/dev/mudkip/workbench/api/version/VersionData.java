package dev.mudkip.workbench.api.version;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.SystemUtils;

import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface VersionData {
    /**
     * @return JSON representation of the version data
     */
    String getJson();

    /**
     * @return Reference to the main class of the current version
     */
    String getMainClass();

    /**
     * @return The arguments used to launch the game
     */
    ArgumentListing getMinecraftArgs();

    /**
     * @return The recommended arguments to launch JVM with
     */
    ArgumentListing getJvmArgs();

    /**
     * @return The libraries required to run the game
     */
    List<Library> getLibraries();

    /**
     * @return The version ID
     */
    String getId();

    /**
     * @return The release cycle of the version
     */
    Optional<ReleaseCycle> getType();

    /**
     * @return All downloadable sources for the version
     */
    Downloads getDownloads();

    record Library(String name, String url, Type isRuntime) {

        public enum NativeVersion {
            LINUX,
            WINDOWS,
            OSX;

            public static String translate(JsonObject object) {
                if (object.has("natives")) {
                    return object.getAsJsonObject("natives")
                            .entrySet().stream().filter(entry -> {
                                if (entry.getKey().equals("windows") && SystemUtils.IS_OS_WINDOWS)
                                    return true;
                                if (entry.getKey().equals("osx") && SystemUtils.IS_OS_MAC)
                                    return true;
                                return entry.getKey().equals("linux") && SystemUtils.IS_OS_LINUX;
                            }).findFirst()
                            .get().getValue().getAsString();
                }
                return null;
            }
        }

        public enum Type {
            STANDARD,
            NATIVE,
            AGENT;

            public static Type find(JsonObject object) {
                if (object.getAsJsonObject("downloads").has("classifiers")) {
                    if (object.getAsJsonObject("downloads").getAsJsonObject("classifiers").has("agent")) {
                        return AGENT;
                    } else {
                        return NATIVE;
                    }
                } else {
                    return STANDARD;
                }
            }
        }
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
