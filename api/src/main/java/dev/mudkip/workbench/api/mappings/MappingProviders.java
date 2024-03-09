package dev.mudkip.workbench.api.mappings;

import dev.mudkip.workbench.api.utils.MavenMetadata;
import dev.mudkip.workbench.api.utils.Utils;
import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public enum MappingProviders implements IMappingProvider {
    // OFFICIAL(false, MappingFormat.PROGUARD_FILE),
    // YARN(true, MappingFormat.TINY_FILE, MappingFormat.TINY_2_FILE),
    // MCP(true, MappingFormat.SRG_FILE),
    FEATHER(true, MappingFormat.TINY_2_FILE) {
        @Override
        public MemoryMappingTree getMappings(String version, Path file) {
            Utils.download("https://maven.ornithemc.net/releases/net/ornithemc/feather/" + version + "//feather-" + version + "-mergedv2.jar", file);
            try (JarFile jarFile = new JarFile(file.toFile())) {
                JarEntry entry = jarFile.getJarEntry("mappings/mappings.tiny");
                try (InputStream stream = jarFile.getInputStream(entry)) {
                    MemoryMappingTree mem = new MemoryMappingTree();
                    MappingReader.read(new InputStreamReader(stream), mem);
                    return mem;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String[] getVersions() {
            return MappingProviders.readMetadata("https://maven.ornithemc.net/releases/net/ornithemc/feather//maven-metadata.xml")
                    .getVersioning().releases();
        }
    };

    private final boolean hasIntermediary;
    private final MappingFormat[] formats;

    MappingProviders(boolean hasIntermediary, MappingFormat... formats) {
        this.hasIntermediary = hasIntermediary;
        this.formats = formats;
    }

    @Override
    public MappingFormat[] getSupportedFormats() {
        return formats;
    }

    @Override
    public boolean hasIntermediary() {
        return hasIntermediary;
    }

    private static MavenMetadata readMetadata(String url) {
        try {
            URL link = new URL(url);
            try (var stream = link.openStream()) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(stream);
                MavenMetadata mavenMetadata = new MavenMetadata();
                mavenMetadata.setArtifactId(document.getElementsByTagName("artifactId").item(0).getTextContent());
                mavenMetadata.setGroupId(document.getElementsByTagName("groupId").item(0).getTextContent());
                NodeList nodeList = document.getElementsByTagName("versions").item(0).getChildNodes();
                String[] versions = new String[nodeList.getLength()];
                for (int i = 0; i < nodeList.getLength(); i++) {
                    versions[i] = nodeList.item(i).getTextContent();
                }
                mavenMetadata.setVersioning(new MavenMetadata.Versioning(
                        document.getElementsByTagName("latest").item(0).getTextContent(),
                        document.getElementsByTagName("release").item(0).getTextContent(),
                        versions
                ));
                return mavenMetadata;
            } catch (IOException | ParserConfigurationException | SAXException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
