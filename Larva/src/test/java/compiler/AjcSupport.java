package compiler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Shared support for Tier 3 end-to-end tests: shells out to the real "ajc"
 * command (must be on PATH - these tests don't bundle a compiler), rather
 * than driving org.aspectj.tools.ajc.Main in-process, so they exercise the
 * same tool a student would actually have installed.
 */
final class AjcSupport {

	private AjcSupport() {}

	/**
	 * Fails the test with a clear, actionable message if ajc can't be
	 * launched - instead of letting an IOException surface as a raw stack
	 * trace partway through a test, or silently skipping.
	 */
	static void requireAjcOnPath() {
		try {
			Process p = new ProcessBuilder("ajc", "-version").redirectErrorStream(true).start();
			p.getInputStream().readAllBytes();
			p.waitFor();
		} catch (IOException e) {
			fail("This test requires AspectJ's \"ajc\" compiler on PATH, but it could not be launched "
					+ "(" + e.getMessage() + "). Install AspectJ 1.9.x and ensure ajc is on PATH before "
					+ "running \"mvn verify\" - see the root README's quickstart.");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			fail("Interrupted while checking for ajc on PATH.");
		}
	}

	/** Resolves the actual aspectjrt-*.jar file backing the test-scope Maven dependency. */
	static Path aspectjrtJar() {
		try {
			return Path.of(org.aspectj.lang.JoinPoint.class.getProtectionDomain()
					.getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			throw new IllegalStateException("Could not locate aspectjrt.jar on the test classpath", e);
		}
	}

	/** Runs "ajc -1.8 -cp aspectjrt.jar -sourceroots sourceRoot -d outputDir", failing clearly on error. */
	static void weave(Path sourceRoot, Path outputDir) throws IOException, InterruptedException {
		Files.createDirectories(outputDir);
		ProcessBuilder pb = new ProcessBuilder(
				"ajc", "-1.8",
				"-cp", aspectjrtJar().toString(),
				"-sourceroots", sourceRoot.toString(),
				"-d", outputDir.toString());
		pb.redirectErrorStream(true);
		Process process = pb.start();
		String output = new String(process.getInputStream().readAllBytes());
		int exitCode = process.waitFor();
		if (exitCode != 0) {
			fail("ajc failed to weave " + sourceRoot + " (exit " + exitCode + "):\n" + output);
		}
	}

	/**
	 * Copies an "examples/&lt;demoSystemDir&gt;/&lt;packageDir&gt;" Java
	 * package directory (sources only, not .class/.jar/generated output) into
	 * destRoot/packageDir, so it can be compiled together with a compiled
	 * .lrv script's own output via a single ajc -sourceroots.
	 */
	static void copyDemoSystemSources(String demoSystemDir, String packageDir, Path destRoot) throws IOException {
		Path source = Paths.get(System.getProperty("basedir"), "..", "examples", demoSystemDir, packageDir);
		Path destination = destRoot.resolve(packageDir);
		Files.createDirectories(destination);
		try (Stream<Path> files = Files.list(source).filter(p -> p.toString().endsWith(".java"))) {
			for (Path file : (Iterable<Path>) files::iterator) {
				Files.copy(file, destination.resolve(file.getFileName()));
			}
		}
	}
}
