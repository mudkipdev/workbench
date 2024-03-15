package dev.mudkip.workbench.api.mappings;

import dev.mudkip.workbench.api.utility.FileUtility;
import dev.mudkip.workbench.api.utility.UrlUtility;
import dev.mudkip.workbench.api.utility.Urls;
import dev.mudkip.workbench.api.utility.MavenDependency;
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

public enum Mappings implements MappingProvider {
    // OFFICIAL(false, MappingFormat.PROGUARD_FILE),
    // YARN(true, MappingFormat.TINY_FILE, MappingFormat.TINY_2_FILE),
    // MCP(true, MappingFormat.SRG_FILE),
    FEATHER(true, MappingFormat.TINY_2_FILE) {
        @Override
        public MemoryMappingTree getMappings(String version, Path file) {
            UrlUtility.download(
                    Urls.FEATHER_URL + "/" + version + "//feather-" + version + "-mergedv2.jar",
                    file);

            return FileUtility.readStandardTinyV2Jar(file.toFile());
        }

        @Override
        public String[] getVersions() {
            try {
                return Mappings.readMetadata(new URL(Urls.FEATHER_URL + "//maven-metadata.xml"))
                        .versioning().releases();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    },
    BABRIC_INTERMEDIARY(true, MappingFormat.TINY_2_FILE) {
        @Override
        public MemoryMappingTree getMappings(String version, Path file) {
            UrlUtility.download(
                    Urls.BABRIC_URL + "/intermediary/" + version + "/intermediary-" + version + "-v2.jar",
                    file);
            return FileUtility.readStandardTinyV2Jar(file.toFile());
        }

        @Override
        public String[] getVersions() {
            try {
                return Mappings.readMetadata(new URL(Urls.BABRIC_URL + "/intermediary/maven-metadata.xml"))
                        .versioning().releases();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private final boolean intermediary;
    private final MappingFormat[] formats;

    Mappings(boolean intermediary, MappingFormat... formats) {
        this.intermediary = intermediary;
        this.formats = formats;
    }

    @Override
    public MappingFormat[] getSupportedFormats() {
        return this.formats;
    }

    @Override
    public boolean hasIntermediary() {
        return this.intermediary;
    }

    private static MavenDependency readMetadata(URL url) {
        try (var stream = url.openStream()) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(stream);

            String group = document.getElementsByTagName("groupId").item(0).getTextContent();;
            String artifact = document.getElementsByTagName("artifactId").item(0).getTextContent();
            MavenDependency.Versioning versioning;

            NodeList nodeList = document.getElementsByTagName("versions").item(0).getChildNodes();
            String[] versions = new String[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {
                versions[i] = nodeList.item(i).getTextContent();
            }

            versioning = new MavenDependency.Versioning(
                    document.getElementsByTagName("latest").item(0).getTextContent(),
                    document.getElementsByTagName("release").item(0).getTextContent(),
                    versions
            );

            return new MavenDependency(group, artifact, versioning);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
