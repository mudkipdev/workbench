package dev.mudkip.workbench.api.utility;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.file.Path;
import java.util.function.Consumer;

public final class UrlUtility {
	private UrlUtility() {

	}

	public static void use(URL url, Consumer<BufferedReader> readerConsumer) {
		try (var stream = url.openStream()) {
			readerConsumer.accept(new BufferedReader(new InputStreamReader(stream)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void use(String url, Consumer<BufferedReader> readerConsumer) {
		try {
			use(new URL(url), readerConsumer);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void download(URL url, Path target) {
		try {
			var byteChannel = Channels.newChannel(url.openStream());
			var outputStream = new FileOutputStream(target.toFile());
			outputStream.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
			outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void download(String url, Path target) {
		try {
			download(new URL(url), target);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
