package compiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Direct regression guard for the fix merged to src/resources/Clock.txt:
 * Clock.reset() must restore "thison", otherwise a clock switched off with
 * off() stays off across a reset(). This checks the embedded template
 * directly (via a real compile, so it's exercised the same way a real
 * script would trigger it), not just the source file on disk.
 */
class ClockResetRegressionTest extends CompilerTestBase {

	@Test
	void generatedClockResetRestoresThison(@TempDir Path outputDir) throws Exception {
		compile(fixture("clocks.lrv"), outputDir);

		Path clockJava = outputDir.resolve("larva").resolve("Clock.java");
		assertTrue(Files.exists(clockJava), "Clock.java was not generated");

		String source = Files.readString(clockJava);
		int resetStart = source.indexOf("public void reset()");
		assertTrue(resetStart >= 0, "reset() method not found in generated Clock.java");

		int nextMethodStart = source.indexOf("public boolean verified", resetStart);
		assertTrue(nextMethodStart > resetStart, "could not bound reset()'s method body");

		String resetBody = source.substring(resetStart, nextMethodStart);
		assertTrue(resetBody.contains("thison = true;"),
				"reset() does not restore thison - regression of the Clock.txt fix:\n" + resetBody);
	}
}
