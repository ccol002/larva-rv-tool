# Benchmark demo

A synthetic banking-style system (`benchmark.Bank`/`benchmark.Tester`)
used to exercise several independent monitor properties at once, driven
by a small set of hand-picked test scenarios in `Tester`.

Two scripts monitor the same target system:

- `benchmark.lrv` checks that no user racks up more than 5 transactions,
  and that a failed transaction is retried the correct number of times
  within the expected time frame.
- `benchmark_invariants.lrv` is a variant of the same script that
  additionally demonstrates LARVA's `INVARIANTS` feature: once a
  transaction reaches a certain point in its automaton, its amount is
  asserted not to change again.

Both scripts compile into the same `larva/`/`aspects/` output (their
generated class names don't collide).

## Running it

- `run.sh` / `run.bat` — compile and run `benchmark.Tester` unmonitored.
  It prompts for a test number (0-4), each exercising a different
  scenario (all transactions succeeding, a transaction failing and
  retrying, etc.) — or pass the number as an argument to skip the
  prompt.
- `run with monitor code.sh` / `.bat` — weave both scripts' pre-generated
  monitor code in with `ajc` and run `Tester` monitored the same way.

Monitor output — including invariant-violation traces — prints
straight to the console, not to a log file.
