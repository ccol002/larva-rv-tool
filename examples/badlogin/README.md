# Badlogin demo (dynamic clocks)

A minimal target system (`main.Main`/`main.User`) that repeatedly calls
`User.badlogin()` on a timer, standing in for an authentication system
under a brute-force-style login attack.

`badloginsDynamicClocks.lrv` demonstrates LARVA's *dynamic clocks*: a
`DynamicClock` is (re)registered on every bad login with a moving
deadline, rather than a clock declared once with a fixed timeout. It's
a simpler, illustrative property (the comment in the script itself
notes it isn't a fully correct bad-login-rate check) meant to show the
dynamic-clock mechanism working, not to be a realistic security policy.

## Running it

- `run.sh` / `run.bat` — compile and run the target system unmonitored;
  it loops forever printing "Bad Login at: ..." (stop it with Ctrl+C).
- `run with monitor code.sh` / `.bat` — weave the pre-generated monitor
  code in with `ajc` and run monitored the same way.

With the monitor woven in, its own trace ("Seems to work :)", printed
whenever a clock resets cleanly) prints directly to the console
alongside the target system's own output.
