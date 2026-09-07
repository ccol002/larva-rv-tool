#!/bin/sh
# minepump has no hand-written .lrv script of its own - it demonstrates the
# QDDC-to-LARVA converter pipeline instead. Unlike the other demo systems'
# "run with monitor code" scripts (which just weave pre-generated larva/
# aspects output), this one regenerates minepump.lrv from qddc_minepump.txt
# and recompiles it every run, so the pipeline is never a mystery step.
#
# -v (verbose) is used because this property never actually reaches a BAD
# state during this short demo run, so without it there would be no
# output at all; verbose output shows the automaton evaluating the
# QDDC-derived formula on every event. -c (console) sends that output to
# stdout directly instead of a log file that's only flushed on a BAD-state
# transition (which, again, this demo never reaches).
cd "$(dirname "$0")" || exit 1

java -cp "../../Larva converter" main.Main -QDDC qddc_minepump.txt -o minepump.lrv

java -jar "../../Larva/target/larva-compiler-1.0.0.jar" minepump.lrv -o . -v -c

ajc -1.8 -cp aspectjrt.jar -sourceroots . -d bin

java -cp bin:aspectjrt.jar main.Main
