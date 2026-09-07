#!/bin/sh
cd "$(dirname "$0")" || exit 1

javac -sourcepath . nesting/*.java

java -cp . nesting.Bank
