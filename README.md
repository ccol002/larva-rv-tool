# LARVA

LARVA (Logical Automata for Runtime Verification and Analysis) is a runtime
verification tool for Java. Properties are written in a dedicated scripting
language as Dynamic Automata with Timers and Events (DATEs) — automata whose
states, transitions and clocks encode the behaviour you want to check. The
LARVA compiler translates a `.lrv` script into AspectJ code, which is then
woven into the target Java system to monitor it as it runs, flagging bad
states (property violations) as they occur.

This repository is used for the University of Malta's CPS3233 "Verification
Techniques" course, and for LARVA's continued development.

## Repository layout

- `Larva/` — the compiler source (`src/compiler`) and the runtime code
  templates it weaves into generated monitors (`src/resources`); also an
  Eclipse project and the Maven build (`pom.xml`) described below.
- `Larva manuals/` — the LARVA System Manual and the Converter manual.
- `Java demo systems/` — three canonical demos, each a target Java system
  with `run.sh`/`run.bat` (compile and run unmonitored) and
  `run with monitor code.sh`/`.bat` (compile, weave and run monitored):
  - `bank system/` and `benchmark/` each carry a hand-written `.lrv` script
    (`bank.lrv`, `benchmark.lrv`) alongside the Java system it monitors.
    `benchmark/` also has `benchmark_invariants.lrv`, a variant additionally
    demonstrating LARVA's `INVARIANTS` feature.
  - `minepump/` has no hand-written script at all. It demonstrates the
    *converter* instead: `qddc_minepump.txt` is a QDDC formula, and
    `run with monitor code.sh`/`.bat` first runs it through the converter
    to (re)generate `minepump.lrv`, then compiles, weaves and runs it as
    usual — the QDDC-to-LARVA pipeline is the point of this demo, not an
    implementation detail.
- `Larva converter/` — a compiled tool that converts counterexamples,
  Lustre, implementables and QDDC specifications into LARVA scripts (see
  `minepump/` above for its one worked, end-to-end example).
- `Examples/` — further worked examples: `clocks.lrv` (a standalone
  clocks/channels language showcase with no target system of its own),
  `Badlogin example with dynamic clocks/` (a fully-worked example distinct
  from `Tutorial/BadLogin/`'s bare starting skeleton — see below), and the
  RV Competition 2016 benchmarks.
- `Tutorial/` — a guided introduction to writing LARVA scripts.
  `Tutorial/BadLogin/` is an intentionally bare starting skeleton (just
  `Main.java`) for the tutorial's own exercises, not a worked example.
- `Other projects/` — related research prototypes and experiments, kept for
  reference; not part of the maintained tool.

## Quickstart (JDK 21, AspectJ 1.9.x)

Requires a JDK (17+; validated on JDK 21), [Maven](https://maven.apache.org/),
and [AspectJ](https://www.eclipse.org/aspectj/) 1.9.x with its `ajc` compiler
on your `PATH`. [Graphviz](https://graphviz.org/) is optional — if `dot` is on
your `PATH`, the compiler also emits a `.gif` diagram per automaton.

1. **Build the compiler:**

   ```sh
   cd Larva
   mvn package
   ```

   This produces `Larva/target/larva-compiler-1.0.0.jar`, an executable jar
   with no runtime dependencies of its own.

2. **Compile a `.lrv` script** into the monitor's Java/AspectJ sources:

   ```sh
   java -jar Larva/target/larva-compiler-1.0.0.jar path/to/script.lrv -o path/to/output/dir
   ```

   Useful flags: `-v` for verbose output, `-g <path-to-dot>` to point at a
   Graphviz install that isn't on `PATH`.

3. **Weave the generated monitor** into the target system and **run** it —
   for example, for the `bank system` demo:

   ```sh
   cd "Java demo systems/bank system"
   ajc -1.8 -cp aspectjrt.jar -sourceroots . -d bin
   java -cp bin:aspectjrt.jar nesting.Bank
   ```

   Or simply run the equivalent script already provided next to each demo
   system: `./"run with monitor code.sh"` (Linux/macOS) or
   `"run with monitor code.bat"` (Windows).

See the [LARVA System Manual](Larva%20manuals/Larva%20System%20Manual.pdf)
for the full scripting language reference, and `Larva/README.txt` for
features added since the manual was last updated (the `-v` flag, dynamic
clocks).

## License

MIT — see [LICENSE](LICENSE).
