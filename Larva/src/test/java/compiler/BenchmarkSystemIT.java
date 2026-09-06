package compiler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end: compile benchmark.lrv, weave it into the "benchmark" demo
 * Java classes with the real ajc, run the woven classes, and check the
 * exception-handling property fires - mirroring Tester.java's own
 * "transactionWithExceptionRetried" scenario.
 *
 * As in BankSystemIT: the driving code (Driver.java) is written into the
 * same source root that gets woven, and only its main() is invoked from
 * the test. AspectJ's call() pointcuts only match static call sites
 * inside the woven compilation, so driving the target purely by reflection
 * from this test class (which ajc never sees) risks the advice silently
 * never firing - the test would pass or fail for the wrong reason.
 */
class BenchmarkSystemIT extends CompilerTestBase {

	@BeforeEach
	void checkAjc() {
		AjcSupport.requireAjcOnPath();
	}

	@Test
	void transactionThatThrowsIsNotRetriedAndReachesBadState(@TempDir Path work) throws Exception {
		Path sourceRoot = Files.createDirectory(work.resolve("src"));
		Path classesDir = work.resolve("classes");

		compile(fixture("benchmark.lrv"), sourceRoot);
		AjcSupport.copyDemoSystemSources("benchmark", "benchmark", sourceRoot);
		Files.writeString(sourceRoot.resolve("benchmark").resolve("Driver.java"), DRIVER_SOURCE);

		AjcSupport.weave(sourceRoot, classesDir);

		try (URLClassLoader loader = new URLClassLoader(
				new URL[] { classesDir.toUri().toURL(), AjcSupport.aspectjrtJar().toUri().toURL() },
				ClassLoader.getSystemClassLoader())) {
			Class<?> driver = loader.loadClass("benchmark.Driver");
			driver.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
		}

		String log = Files.readString(sourceRoot.resolve("output_benchmark.txt"));
		assertTrue(log.contains("REACHED BAD STATE") && log.contains("retryTimeoutandExceptionRetry"),
				"Expected \"retryTimeoutandExceptionRetry\" to reach its BAD state after a transaction "
						+ "that throws, but the monitor's log did not show it:\n" + log);
	}

	// Mirrors Tester.java's transactionWithExceptionRetried(): a transaction
	// that throws should not be retried, and reaches the BAD state.
	private static final String DRIVER_SOURCE = """
			package benchmark;

			public class Driver {
				public static void main(String[] args) {
					DummyDatabase.setException(true);
					DummyDatabase.setSucceed(1);
					Bank bank = new Bank();
					Transaction transaction = bank.addUser().addTransaction();
					bank.perform(transaction);
				}
			}
			""";
}
