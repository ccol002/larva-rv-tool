package compiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The generated monitor's name comes from the script's filename, not its path or extension. */
class MonitorNamingTest extends CompilerTestBase {

	@Test
	void monitorNameIsFilenameWithoutPathOrExtension(@TempDir Path tempDir) throws Exception {
		Path nested = Files.createDirectories(tempDir.resolve("deeply").resolve("nested").resolve("dir"));
		Path script = nested.resolve("my-script.lrv");
		Files.copy(fixture("minimal-valid.lrv"), script);

		Path outputDir = Files.createDirectory(tempDir.resolve("out"));
		compile(script, outputDir);

		assertEquals("my-script", Compiler.global.name);
	}

	@Test
	void monitorNameIgnoresUnderscoresAndDigitsVerbatim(@TempDir Path tempDir) throws Exception {
		Path script = tempDir.resolve("bank_v2.lrv");
		Files.copy(fixture("minimal-valid.lrv"), script);

		Path outputDir = Files.createDirectory(tempDir.resolve("out"));
		compile(script, outputDir);

		assertEquals("bank_v2", Compiler.global.name);
	}
}
