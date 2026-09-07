rem minepump has no hand-written .lrv script of its own - it demonstrates the
rem QDDC-to-LARVA converter pipeline instead. Unlike the other demo systems'
rem "run with monitor code" scripts (which just weave pre-generated larva/
rem aspects output), this one regenerates minepump.lrv from qddc_minepump.txt
rem and recompiles it every run, so the pipeline is never a mystery step.
rem
rem -v (verbose) is used because this property never actually reaches a BAD
rem state during this short demo run, so without it the log would show
rem nothing at all; verbose output shows the automaton evaluating the
rem QDDC-derived formula on every event.

call java -cp "..\..\Larva converter" main.Main -QDDC qddc_minepump.txt -o minepump.lrv

call java -jar "..\..\Larva\target\larva-compiler-1.0.0.jar" minepump.lrv -o . -v

call ajc -1.8 -cp aspectjrt.jar -sourceroots . -d bin

call java -cp "bin;aspectjrt.jar" main.Main

pause
