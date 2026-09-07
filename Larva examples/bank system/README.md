# Bank system demo

An interactive console banking system (`nesting.Bank`): users can be
added, deleted, and processed, each user holding accounts, each account
holding transactions. `run.sh`/`run.bat` drives it via a text menu read
from stdin.

`bank.lrv` checks a three-level nesting property: no more than 5 users
in the bank, no more than 5 accounts per user, and no more than 5
transactions per account. Each level is a separate monitor instance,
created and torn down as users/accounts/transactions come and go.

## Running it

- `run.sh` / `run.bat` — compile and run unmonitored.
- `run with monitor code.sh` / `.bat` — weave `bank.lrv`'s pre-generated
  monitor code in with `ajc` and run it monitored.

The monitor's own output (state transitions, bad-state traces) prints
straight to the console alongside the bank system's own menu output —
no separate log file to go looking in.
