#!/bin/sh
cd "$(dirname "$0")" || exit 1

javac -sourcepath . benchmark/*.java

java -cp . benchmark.Tester
