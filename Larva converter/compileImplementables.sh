#!/bin/sh
cd "$(dirname "$0")" || exit 1

read -p "Enter script file: " F
read -p "Enter destination file: " O

java -cp . main.Main -IMPL "$F" -o "$O"
