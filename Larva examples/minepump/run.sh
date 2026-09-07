#!/bin/sh
cd "$(dirname "$0")" || exit 1

javac -sourcepath . main/*.java

java -cp . main.Main
