package compiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** The three canonical example scripts must compile without throwing. */
class ValidScriptsTest extends CompilerTestBase {

	@Test
	void bankLrvCompiles(@TempDir Path outputDir) throws Exception {
		assertDoesNotThrow(() -> compile(fixture("bank.lrv"), outputDir));
		assertTrue(Files.exists(outputDir.resolve("larva").resolve("SC.java")));
	}

	@Test
	void benchmarkLrvCompiles(@TempDir Path outputDir) throws Exception {
		assertDoesNotThrow(() -> compile(fixture("benchmark.lrv"), outputDir));
		assertTrue(Files.exists(outputDir.resolve("larva").resolve("SC.java")));
	}

	@Test
	void clocksLrvCompiles(@TempDir Path outputDir) throws Exception {
		assertDoesNotThrow(() -> compile(fixture("clocks.lrv"), outputDir));
		assertTrue(Files.exists(outputDir.resolve("larva").resolve("SC.java")));
	}
}
