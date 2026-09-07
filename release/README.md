# LARVA

LARVA (Logical Automata for Runtime Verification and Analysis) is a runtime
verification tool for Java. Properties are written in a dedicated scripting
language as Dynamic Automata with Timers and Events (DATEs) — automata whose
states, transitions and clocks encode the behaviour you want to check. The
LARVA compiler translates a `.lrv` script into AspectJ code, which is then
woven into the target Java system to monitor it as it runs, flagging bad
states (property violations) as they occur.

This is a standalone release: everything here is self-contained, with no
need for the rest of the [source repository](https://github.com/ccol002/larva-rv-tool).

## What's in this release

- `larva-compiler-*.jar` — the LARVA compiler, an executable jar with no
  runtime dependencies of its own.
- `Larva manuals/Larva System Manual.pdf` — the full scripting language
  reference.
- `Tutorial/` — a guided, step-by-step introduction to writing LARVA
  scripts (`Before starting the tutorial.docx`, then the two tutorial
  parts), plus a one-page syntax quick reference.
- `Larva examples/` — four small worked demos, each with its own
  `README.md`, `run.sh`/`run.bat` (compile and run unmonitored) and
  `run with monitor code.sh`/`.bat` (compile, weave and run monitored —
  monitor output prints straight to the console):
  - `bank system/` and `benchmark/` each carry a hand-written `.lrv`
    script alongside the Java system it monitors. `benchmark/` also has
    `benchmark_invariants.lrv`, demonstrating LARVA's `INVARIANTS`
    feature.
  - `clocks/` is a standalone showcase of LARVA's clock and channel
    constructs.
  - `badlogin/` demonstrates *dynamic clocks* — a clock re-registered
    with a moving deadline on every event.

This release covers the compiler and its demos only — it doesn't include
the LARVA converter (which turns counterexamples, Lustre, implementables
and QDDC specifications into `.lrv` scripts) or the full course material;
see the source repository for those.

## Quickstart (JDK 17+, AspectJ 1.9.x)

Requires a JDK (17+), and [AspectJ](https://www.eclipse.org/aspectj/) 1.9.x
with its `ajc` compiler on your `PATH`. [Graphviz](https://graphviz.org/) is
optional — if `dot` is on your `PATH`, the compiler also emits a `.gif`
diagram per automaton.

1. **Compile a `.lrv` script** into the monitor's Java/AspectJ sources:

   ```sh
   java -jar larva-compiler-*.jar path/to/script.lrv -o path/to/output/dir -c
   ```

   `-c` sends the monitor's own output straight to the console instead of a
   log file — the demos below are already compiled this way. Other useful
   flags: `-v` for verbose output, `-g <path-to-dot>` to point at a
   Graphviz install that isn't on `PATH`.

2. **Weave the generated monitor** into the target system and **run** it —
   for example, for the `bank system` demo:

   ```sh
   cd "Larva examples/bank system"
   ajc -1.8 -cp aspectjrt.jar -sourceroots . -d bin
   java -cp bin:aspectjrt.jar nesting.Bank
   ```

   Or simply run the equivalent script already provided next to each demo:
   `./"run with monitor code.sh"` (Linux/macOS) or
   `"run with monitor code.bat"` (Windows).

## License

MIT — see [LICENSE](LICENSE).
