# Clocks demo

A showcase for LARVA's own clock and channel constructs rather than a
real target system: `Main` does nothing but print a message, sleep for
about 9 seconds, and print another message on exit — just enough time
for `clocks.lrv`'s automaton to run its course.

`clocks.lrv` defines two properties (`test1`, `test2`) that each fire a
clock five times at one-second intervals, then send a message over a
channel to trigger the same behaviour in the other property. There's no
real "requirement" being checked here — it's a self-contained
demonstration of clocks (`Clock`, timed transitions) and channels
(`Channel.send`/`receive`) working together, not a normal target-system
property.

## Running it

- `run.sh` / `run.bat` — compile and run `Main` unmonitored (it just
  prints its start/end messages, since there's no monitor woven in).
- `run with monitor code.sh` / `.bat` — weave the pre-generated monitor
  code in with `ajc` and run monitored.

With the monitor woven in, "Reached Normal State!! 1" and
"Reached Normal State!! 2" print directly to the console once each
property's automaton completes its cycle.
