#!/bin/sh
# Modern equivalent of the old "aj5" load-time weaving launcher, which is not
# part of current AspectJ distributions: weave and compile in one step with
# ajc, then run the woven classes directly.
cd "$(dirname "$0")" || exit 1

ajc -1.8 -cp aspectjrt.jar -sourceroots . -d bin

java -cp bin:aspectjrt.jar benchmark.Tester
