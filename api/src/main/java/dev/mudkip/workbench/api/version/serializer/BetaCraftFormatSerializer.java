package dev.mudkip.workbench.api.version.serializer;

import com.google.gson.*;
import dev.mudkip.workbench.api.version.ReleaseCycle;
import dev.mudkip.workbench.api.version.VersionData;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.arch.Processor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BetaCraftFormatSerializer implements JsonSerializer<VersionData>, JsonDeserializer<VersionData> {
    @Override
    public JsonObject serialize(VersionData src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }

    @Override
    public VersionData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        return new VersionData() {
            @Override
            public String getJson() {
                return object.toString();
            }

            @Override
            public String getMainClass() {
                return object.getAsJsonPrimitive("mainClass").getAsString();
            }

            @Override
            public ArgumentListing getMinecraftArgs() {
                ArgumentListing args = new ArgumentListing();
                for (JsonElement element : object.getAsJsonObject("arguments").getAsJsonArray("game").asList()) {
                    String item = element.getAsString();
                    if (item.contains("=")) {
                        String[] split = item.split("=");
                        args.put(split[0], split[1].replaceAll("[{}$]", ""));
                    } else
                        args.put(item, null);
                }
                return args;
            }

            @Override
            public ArgumentListing getJvmArgs() {
                ArgumentListing args = new ArgumentListing();
                for (JsonElement element : object.getAsJsonObject("arguments").getAsJsonArray("jvm").asList()) {
                    String item = element.getAsString();
                    if (item.contains("=")) {
                        String[] split = item.split("=");
                        args.put(split[0], split[1].replaceAll("[{}$]", ""));
                    } else
                        args.put(item, null);
                }
                return args;
            }

            @Override
            public List<Library> getLibraries() {
                List<Library> libraries = new ArrayList<>();
                for (JsonElement libObj : object.getAsJsonArray("libraries")) {
                    JsonObject lib = libObj.getAsJsonObject();
                    if (lib.getAsJsonObject("downloads")
                            .has("artifact")) {
                        libraries.add(new Library(
                                lib.getAsJsonPrimitive("name").getAsString(),
                                lib.getAsJsonObject("downloads")
                                        .getAsJsonObject("artifact")
                                        .getAsJsonPrimitive("url").getAsString(),
                                Library.Type.STANDARD));
                    } else if (lib.getAsJsonObject("downloads")
                            .has("classifiers")) {
                        if (Library.Type.find(lib) == Library.Type.NATIVE) {
                            String nativeVersion = Library.NativeVersion.translate(lib);
                            if (nativeVersion != null) {
                                libraries.add(new Library(
                                        lib.getAsJsonPrimitive("name").getAsString(),
                                        lib.getAsJsonObject("downloads")
                                                .getAsJsonObject("classifiers")
                                                .getAsJsonObject(nativeVersion)
                                                .getAsJsonPrimitive("url").getAsString(),
                                        Library.Type.NATIVE));
                            }
                        } else if (Library.Type.find(lib) == Library.Type.AGENT) {
                            libraries.add(new Library(
                                    lib.getAsJsonPrimitive("name").getAsString(),
                                    lib.getAsJsonObject("downloads")
                                            .getAsJsonObject("classifiers")
                                            .getAsJsonObject("agent")
                                            .getAsJsonPrimitive("url").getAsString(),
                                    Library.Type.AGENT));
                        }
                    }
                }
                return libraries;
            }

            @Override
            public String getId() {
                return object.getAsJsonPrimitive("id").getAsString();
            }

            @Override
            public Optional<ReleaseCycle> getType() {
                return ReleaseCycle.get(object.getAsJsonPrimitive("type").getAsString());
            }

            @Override
            public Downloads getDownloads() {
                return new Downloads(
                        looping(object, "downloads.client").getAsJsonObject()
                                .getAsJsonPrimitive("url").getAsString(),
                        looping(object, "downloads.server").getAsJsonObject()
                                .getAsJsonPrimitive("url").getAsString()
                );
            }
        };
    }

    private static JsonElement looping(JsonObject obj, String path) {
        for (String s : path.split("\\.")) {
            obj = obj.getAsJsonObject(s);
        }
        return obj;
    }
}
