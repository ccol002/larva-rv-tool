package compiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The -v/-c/-l/-s CLI flags toggle their corresponding static fields. Driven
 * through main() (not compile()) since flag parsing lives there; main()
 * swallows exceptions internally but sets the flags before ever attempting
 * to compile, so this is still a valid, direct way to exercise it.
 */
class CompilerFlagsTest extends CompilerTestBase {

	@Test
	void noFlagsLeavesDefaultsUnset(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString() });
		assertFalse(Compiler.verbose);
		assertFalse(Compiler.console);
		assertFalse(Compiler.light);
		assertFalse(Compiler.synchronous);
	}

	@Test
	void verboseFlagSetsVerbose(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-v" });
		assertTrue(Compiler.verbose);
		assertFalse(Compiler.console);
		assertFalse(Compiler.light);
		assertFalse(Compiler.synchronous);
	}

	@Test
	void consoleFlagSetsConsole(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-c" });
		assertTrue(Compiler.console);
		assertFalse(Compiler.verbose);
		assertFalse(Compiler.light);
		assertFalse(Compiler.synchronous);
	}

	@Test
	void lightFlagSetsLight(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-l" });
		assertTrue(Compiler.light);
		assertFalse(Compiler.verbose);
		assertFalse(Compiler.console);
		assertFalse(Compiler.synchronous);
	}

	@Test
	void synchronousShortFlagSetsSynchronous(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-s" });
		assertTrue(Compiler.synchronous);
		assertFalse(Compiler.verbose);
		assertFalse(Compiler.console);
		assertFalse(Compiler.light);
	}

	@Test
	void synchronousLongFlagSetsSynchronous(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "--synchronous" });
		assertTrue(Compiler.synchronous);
	}

	@Test
	void allFourFlagsCanBeSetTogether(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-v", "-c", "-l", "-s" });
		assertTrue(Compiler.verbose);
		assertTrue(Compiler.console);
		assertTrue(Compiler.light);
		assertTrue(Compiler.synchronous);
	}

	@Test
	void synchronousFlagGeneratesAutoFlushingPrintWriter(@TempDir Path outputDir) throws Exception {
		Compiler.synchronous = true;
		compile(fixture("minimalvalid.lrv"), outputDir);

		String generated = Files.readString(outputDir.resolve("larva/_cls_minimalvalid0.java"));
		assertTrue(generated.contains("pw = new PrintWriter(new FileOutputStream("),
				"Expected an auto-flushing PrintWriter when -s is set, got:\n" + generated);
		assertTrue(generated.contains("), true);"), "Expected the PrintWriter's autoFlush constructor arg to be true");
	}

	@Test
	void withoutSynchronousFlagGeneratesPlainPrintWriter(@TempDir Path outputDir) throws Exception {
		compile(fixture("minimalvalid.lrv"), outputDir);

		String generated = Files.readString(outputDir.resolve("larva/_cls_minimalvalid0.java"));
		assertFalse(generated.contains("FileOutputStream"), "Did not expect FileOutputStream without -s");
	}
}
