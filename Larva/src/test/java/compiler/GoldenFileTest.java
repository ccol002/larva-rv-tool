package compiler;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Golden-file tests: compile a fixture, and diff its generated .java/.aj
 * output against a committed "known good" copy under
 * src/test/resources/golden/&lt;fixture&gt;/. Generated output has been
 * confirmed deterministic for a given compiler version, so any diff here
 * means codegen actually changed - for one of these five fixtures, that's
 * either a real regression or a deliberate change that needs review.
 *
 * Fixtures prioritise the constructs that were never end-to-end tested
 * during the source-tree consolidation: dynamic clocks, channels, nested
 * FOREACH (bank), and non-trivial guards/actions generally (benchmark,
 * clocks).
 *
 * To deliberately refresh golden files after a legitimate codegen change,
 * run:
 *
 *   mvn test -Dtest=GoldenFileTest -Dlarva.golden.regenerate=true
 *
 * This overwrites the committed golden/ files with freshly generated
 * output instead of comparing against them. Review the resulting git diff
 * before committing - it is not a substitute for actually checking the
 * change is correct.
 */
class GoldenFileTest extends CompilerTestBase {

	private static final boolean REGENERATE = Boolean.getBoolean("larva.golden.regenerate");

	@ParameterizedTest
	@ValueSource(strings = { "bank", "benchmark", "clocks", "dynamicclocks", "channels" })
	void generatedCodeMatchesGolden(String fixtureName, @TempDir Path outputDir) throws Exception {
		compile(fixture(fixtureName + ".lrv"), outputDir);

		Set<Path> generatedRelative = relativize(outputDir, collectGeneratedFiles(outputDir));
		Path goldenDir = goldenDir(fixtureName);

		if (REGENERATE) {
			regenerate(goldenDir, outputDir, generatedRelative);
			return;
		}

		assertTrue(Files.isDirectory(goldenDir),
				"No golden directory for '" + fixtureName + "' at " + goldenDir
						+ " - run with -Dlarva.golden.regenerate=true to create it.");

		Set<Path> golden = collectFiles(goldenDir);

		assertEquals(golden, generatedRelative,
				"Set of generated .java/.aj files differs from golden for '" + fixtureName + "'");

		for (Path relative : golden) {
			String expected = Files.readString(goldenDir.resolve(relative));
			String actual = normalize(Files.readString(outputDir.resolve(relative)), outputDir);
			assertEquals(expected, actual,
					"Generated content for '" + relative + "' (fixture '" + fixtureName + "') no longer "
							+ "matches the golden copy. If this is an intentional codegen change, re-run with "
							+ "-Dlarva.golden.regenerate=true and review the diff before committing.");
		}
	}

	/**
	 * The root class's PrintWriter is constructed with the compile's output
	 * directory baked in as a literal (it needs to know where to write the
	 * monitor's log at runtime), so every compile embeds a different, machine-
	 * and run-specific absolute path. Mask it out so golden files stay
	 * meaningful and portable across machines/directories/runs.
	 */
	private static String normalize(String content, Path outputDir) {
		return content.replace(outputDir.toString(), "{{OUTPUT_DIR}}");
	}

	/** Only .java/.aj are "generated code" for golden purposes - diagrams (.gif/.txt) are excluded. */
	private static Set<Path> collectGeneratedFiles(Path outputDir) throws IOException {
		try (Stream<Path> walk = Files.walk(outputDir)) {
			return walk.filter(Files::isRegularFile)
					.filter(p -> p.toString().endsWith(".java") || p.toString().endsWith(".aj"))
					.collect(Collectors.toCollection(TreeSet::new));
		}
	}

	private static Set<Path> collectFiles(Path dir) throws IOException {
		try (Stream<Path> walk = Files.walk(dir)) {
			return walk.filter(Files::isRegularFile)
					.map(p -> dir.relativize(p))
					.collect(Collectors.toCollection(TreeSet::new));
		}
	}

	private static Set<Path> relativize(Path base, Set<Path> absolutePaths) {
		return absolutePaths.stream()
				.map(base::relativize)
				.collect(Collectors.toCollection(TreeSet::new));
	}

	/** Resolves src/test/resources/golden/&lt;fixture&gt; in the module's *source* tree (not target/). */
	private static Path goldenDir(String fixtureName) {
		String basedir = System.getProperty("basedir", ".");
		return Paths.get(basedir, "src", "test", "resources", "golden", fixtureName);
	}

	private static void regenerate(Path goldenDir, Path outputDir, Set<Path> relativeFiles) {
		try {
			if (Files.isDirectory(goldenDir)) {
				try (Stream<Path> walk = Files.walk(goldenDir)) {
					// delete deepest-first so directories are empty when their turn comes
					for (Path p : walk.sorted(java.util.Comparator.reverseOrder()).collect(Collectors.toList())) {
						Files.delete(p);
					}
				}
			}
			for (Path relative : relativeFiles) {
				Path destination = goldenDir.resolve(relative);
				Files.createDirectories(destination.getParent());
				String normalized = normalize(Files.readString(outputDir.resolve(relative)), outputDir);
				Files.writeString(destination, normalized);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Failed to regenerate golden files at " + goldenDir, e);
		}
	}
}
