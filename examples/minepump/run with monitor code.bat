rem minepump has no hand-written .lrv script of its own - it demonstrates the
rem QDDC-to-LARVA converter pipeline instead. Unlike the other demo systems'
rem "run with monitor code" scripts (which just weave pre-generated larva/
rem aspects output), this one regenerates minepump.lrv from qddc_minepump.txt
rem and recompiles it every run, so the pipeline is never a mystery step.
rem
rem -v (verbose) is used because this property never actually reaches a BAD
rem state during this short demo run, so without it there would be no
rem output at all; verbose output shows the automaton evaluating the
rem QDDC-derived formula on every event. -c (console) sends that output to
rem stdout directly instead of a log file that's only flushed on a BAD-state
rem transition (which, again, this demo never reaches).

call java -cp "..\..\Larva converter" main.Main -QDDC qddc_minepump.txt -o minepump.lrv

call java -jar "..\..\Larva\target\larva-compiler-1.0.0.jar" minepump.lrv -o . -v -c

call ajc -1.8 -cp aspectjrt.jar -sourceroots . -d bin

call java -cp "bin;aspectjrt.jar" main.Main

pause
