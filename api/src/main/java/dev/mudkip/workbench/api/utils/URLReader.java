package dev.mudkip.workbench.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

public class URLReader {
    private final String url;

    public URLReader(String url) {
        this.url = url;
    }

    public void use(Consumer<BufferedReader> readerConsumer) {
        try {
            URL link = new URL(url);
            try (var stream = link.openStream()) {
                readerConsumer.accept(new BufferedReader(new InputStreamReader(stream)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
