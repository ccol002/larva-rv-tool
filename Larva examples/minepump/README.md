# Minepump demo

A simplified mine pump controller (`main.Main`, with a water-level
sensor `DH2O` and an `ALARM`), used to demonstrate LARVA's *converter*
rather than a hand-written script: `qddc_minepump.txt` is a QDDC
formula (a bound on how quickly the alarm must react once the water
level rises), and `run with monitor code.sh`/`.bat` first runs it
through the converter to (re)generate `minepump.lrv`, then compiles,
weaves and runs it — the conversion step is the point of this demo,
not an implementation detail to skip past.

## Running it

- `run.sh` / `run.bat` — compile and run the pump controller
  unmonitored.
- `run with monitor code.sh` / `.bat` — regenerate `minepump.lrv` from
  `qddc_minepump.txt`, compile it with LARVA, weave with `ajc`, and run
  monitored. This demo's property never actually reaches a bad state in
  the short run the script drives, so it's compiled with `-v` as well
  as `-c`: without `-v` there would be no monitor output at all to show
  on the console.

With both flags, the automaton's evaluation of the QDDC-derived formula
prints directly to the console on every event.
