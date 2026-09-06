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
 * End-to-end: compile bank.lrv, weave it into the "bank system" demo Java
 * classes with the real ajc, run the woven classes, and check the
 * property actually fires - by calling the target's methods directly
 * (not by simulating console menu input, which was a fragile ad hoc
 * approach used during manual investigation) and reading the monitor's
 * log.
 *
 * The driving code (Driver.java below) is written into the same source
 * root that gets woven, and only its main() is invoked from the test (via
 * reflection, which is fine here). This matters: AspectJ's call()
 * pointcuts - what LARVA generates for user-defined events - only match
 * static call sites inside the woven compilation. A call made via
 * reflection from code that ajc never saw (i.e. this test class itself)
 * would never trigger the advice at all, silently making the test
 * meaningless rather than failing loudly.
 */
class BankSystemIT extends CompilerTestBase {

	@BeforeEach
	void checkAjc() {
		AjcSupport.requireAjcOnPath();
	}

	@Test
	void moreThanFiveUsersReachesBadState(@TempDir Path work) throws Exception {
		Path sourceRoot = Files.createDirectory(work.resolve("src"));
		Path classesDir = work.resolve("classes");

		compile(fixture("bank.lrv"), sourceRoot);
		AjcSupport.copyDemoSystemSources("bank system", "nesting", sourceRoot);
		Files.writeString(sourceRoot.resolve("nesting").resolve("Driver.java"), DRIVER_SOURCE);

		AjcSupport.weave(sourceRoot, classesDir);

		try (URLClassLoader loader = new URLClassLoader(
				new URL[] { classesDir.toUri().toURL(), AjcSupport.aspectjrtJar().toUri().toURL() },
				ClassLoader.getSystemClassLoader())) {
			Class<?> driver = loader.loadClass("nesting.Driver");
			driver.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
		}

		String log = Files.readString(sourceRoot.resolve("output_bank.txt"));
		assertTrue(log.contains("REACHED BAD STATE") && log.contains("toomany"),
				"Expected the \"users\" property to reach its BAD (toomany) state after 7 addUser() calls, "
						+ "but the monitor's log did not show it:\n" + log);
	}

	// The guard "userCnt > 5" is checked *before* that call's increment, so the
	// 6th call only brings userCnt to 6 - it takes a 7th call to actually see
	// userCnt > 5 and transition to the "toomany" BAD state.
	private static final String DRIVER_SOURCE = """
			package nesting;

			public class Driver {
				public static void main(String[] args) {
					Bank bank = new Bank();
					for (int i = 0; i < 7; i++) {
						bank.addUser();
					}
				}
			}
			""";
}
