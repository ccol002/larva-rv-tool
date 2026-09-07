#!/bin/sh
cd "$(dirname "$0")" || exit 1

javac Main.java

java Main
