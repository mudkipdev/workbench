package dev.mudkip.workbench.api.decompiler;

import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.decompiler.DirectoryResultSaver;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class Vineflower implements Decompiler {
	private static final Map<String, Object> DEFAULT_OPTIONS = new HashMap<>();

	Vineflower() {

	}

	@Override
	public void decompile(Path input, Path output, Map<String, Object> overrideOptions, Path... libraries) {
		Map<String, Object> options = new HashMap<>(DEFAULT_OPTIONS);
		options.putAll(overrideOptions);

		Fernflower fernflower = new Fernflower(
				new DirectoryResultSaver(output.toFile()),
				options,
				new FernflowerLogger());

		fernflower.addSource(input.toFile());
		fernflower.decompileContext();
	}

	private static class FernflowerLogger extends IFernflowerLogger {
		private final Logger logger = LoggerFactory.getLogger("fernflower");

		@Override
		public void writeMessage(String s, Severity severity) {
			this.writeMessage(s, severity, null);
		}

		@Override
		public void writeMessage(String s, Severity severity, Throwable throwable) {
			if (severity.ordinal() > Severity.INFO.ordinal()) {
				if (throwable != null) {
					this.logger.error(s, throwable);
				} else {
					this.logger.error(s);
				}
			}
		}
	}
}
