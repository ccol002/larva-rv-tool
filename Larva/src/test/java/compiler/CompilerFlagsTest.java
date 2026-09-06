package compiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The -v/-c/-l CLI flags toggle their corresponding static fields. Driven
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
	}

	@Test
	void verboseFlagSetsVerbose(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-v" });
		assertTrue(Compiler.verbose);
		assertFalse(Compiler.console);
		assertFalse(Compiler.light);
	}

	@Test
	void consoleFlagSetsConsole(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-c" });
		assertTrue(Compiler.console);
		assertFalse(Compiler.verbose);
		assertFalse(Compiler.light);
	}

	@Test
	void lightFlagSetsLight(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-l" });
		assertTrue(Compiler.light);
		assertFalse(Compiler.verbose);
		assertFalse(Compiler.console);
	}

	@Test
	void allThreeFlagsCanBeSetTogether(@TempDir Path outputDir) throws Exception {
		Compiler.main(new String[] { fixture("minimalvalid.lrv").toString(), "-o", outputDir.toString(), "-v", "-c", "-l" });
		assertTrue(Compiler.verbose);
		assertTrue(Compiler.console);
		assertTrue(Compiler.light);
	}
}
