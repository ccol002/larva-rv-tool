package compiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Malformed scripts must fail with a ParseException carrying a useful message. */
class ParseErrorsTest extends CompilerTestBase {

	@Test
	void undefinedTransitionTargetStateIsRejected(@TempDir Path outputDir) throws Exception {
		ParseException ex = assertThrows(ParseException.class,
				() -> compile(fixture("unknown-state.lrv"), outputDir));
		assertTrue(ex.getMessage().contains("Unknown State"), "message was: " + ex.getMessage());
		assertTrue(ex.getMessage().contains("ghost"), "message was: " + ex.getMessage());
	}

	@Test
	void undefinedEventIsRejected(@TempDir Path outputDir) throws Exception {
		ParseException ex = assertThrows(ParseException.class,
				() -> compile(fixture("unknown-event.lrv"), outputDir));
		assertTrue(ex.getMessage().contains("Unknown Event"), "message was: " + ex.getMessage());
		assertTrue(ex.getMessage().contains("ghostEvent"), "message was: " + ex.getMessage());
	}

	@Test
	void structurallyMalformedTransitionsBlockIsRejected(@TempDir Path outputDir) throws Exception {
		// Fixture omits the "[" delimiter before the event in a transition arrow.
		assertThrows(ParseException.class,
				() -> compile(fixture("malformed-transitions.lrv"), outputDir));
	}
}
