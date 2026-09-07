# LARVA converter

A standalone tool that converts specifications written in other formalisms
into LARVA `.lrv` scripts, so you don't have to hand-write a DATE automaton
from scratch when your property is already expressed one of these ways:

- **Counterexamples** (`-CE`) — a trace showing a property violation.
- **Implementables** (`-IMPL`) — implementable specifications.
- **Lustre** (`-LUSTRE`) — Lustre synchronous dataflow specifications.
- **QDDC** (`-QDDC`) — Quantified Discrete Duration Calculus formulas.

This is a standalone release: everything here is self-contained, with no
need for the rest of the [source repository](https://github.com/ccol002/larva-rv-tool).
It does **not** include the LARVA compiler itself — the `.lrv` script this
tool produces still needs to be compiled and woven with LARVA to actually
monitor a running system; grab a separate LARVA release for that.

## What's in this release

- `main/`, `CE/`, `CEform/`, `newCEform/`, `Lustre/`, `PEA/`, `newPEA/`,
  `QDDC/`, `parsing/` — the converter itself, as compiled classes (no
  source; this predates the rest of the tool's move to Maven).
- `compileCounterexamples.sh`/`.bat`, `compileImplementables.sh`/`.bat`,
  `compileLustre.sh`/`.bat`, `compileQDDC.sh`/`.bat` — one convenience
  script per input format; each just prompts for an input and output file
  and runs the converter.
- `Larva manuals/Converter manual.pdf` — the full reference for all four
  input formats.
- `example scripts/` — one or more sample inputs per format, to try the
  converter on before writing your own.

## Running it

Pick the script matching your input format, e.g.:

```sh
./compileQDDC.sh
Enter script file: example scripts/qddc/qddc_bounded.txt
Enter destination file: bounded.lrv
```

Or invoke the converter directly:

```sh
java -cp . main.Main -QDDC path/to/input.txt -o path/to/output.lrv
```

(swap `-QDDC` for `-CE`, `-IMPL`, or `-LUSTRE` as needed). Requires a JDK
(17+) — no other runtime dependencies.

## License

MIT — see [LICENSE](LICENSE).
