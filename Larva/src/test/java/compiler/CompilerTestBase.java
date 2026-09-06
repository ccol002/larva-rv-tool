package compiler;

import org.junit.jupiter.api.BeforeEach;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Shared base for compiler tests: resets Compiler's cross-compile static
 * state before every test, since Compiler/Global/Event/Events/State/Token
 * all use static fields to track a single compile - without this, tests
 * would pass or fail depending on what ran before them in the same JVM.
 */
public abstract class CompilerTestBase {

	@BeforeEach
	void resetCompilerState() {
		Compiler.resetState();
		// Diagram generation shells out to Graphviz asynchronously (fire-and-forget,
		// no waitFor()). Tests shouldn't depend on whether the host happens to have
		// Graphviz installed, and a real "dot" process can still be writing into a
		// @TempDir after the test method returns, racing its cleanup. Point at a
		// binary that can't exist so ProcessBuilder.start() fails immediately and
		// synchronously instead.
		Compiler.graphvizDir = "larva-test-suite-graphviz-does-not-exist";
	}

	/** Resolves a fixture under src/test/resources/fixtures/ by name. */
	protected static Path fixture(String name) throws Exception {
		URL url = CompilerTestBase.class.getClassLoader().getResource("fixtures/" + name);
		if (url == null) {
			throw new IllegalStateException("Fixture not found on classpath: fixtures/" + name);
		}
		return Paths.get(url.toURI());
	}

	/** Compiles a script into outputDir using the plain (non-CLI) entry point. */
	protected static void compile(Path script, Path outputDir) throws ParseException, java.io.IOException {
		Compiler.inputDir = script.toString();
		Compiler.outputDir = outputDir.toString();
		Compiler.compile();
	}
}
