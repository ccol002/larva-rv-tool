#!/bin/sh
# Builds a self-contained "download and experiment" zip of LARVA: the
# compiler jar, its manual, the tutorial, and the demos that are pure
# LARVA (no converter involved). See release/README.md for what ships.
#
# Usage: ./package-release.sh [version]   (default version: 1.0.0)
set -e
cd "$(dirname "$0")"

VERSION="${1:-1.0.0}"
NAME="larva-$VERSION"
STAGE="$(mktemp -d)"
ROOT="$STAGE/$NAME"
mkdir -p "$ROOT"

echo "Building compiler jar..."
(cd Larva && mvn -q -DskipTests package)
JAR="$(ls Larva/target/larva-compiler-*.jar)"
cp "$JAR" "$ROOT/"

cp LICENSE "$ROOT/"
cp release/README.md "$ROOT/README.md"

mkdir -p "$ROOT/Larva manuals"
cp "Larva manuals/Larva System Manual.pdf" "$ROOT/Larva manuals/"

cp -r Tutorial "$ROOT/Tutorial"

mkdir -p "$ROOT/Larva examples"
for demo in "bank system" benchmark clocks badlogin; do
	cp -r "Larva examples/$demo" "$ROOT/Larva examples/$demo"
done

# Drop stray build artifacts that don't belong in a source-only release.
find "$ROOT" -name "*.class" -delete
find "$ROOT" -depth -name bin -type d -exec rm -rf {} +

OUT="$NAME.zip"
rm -f "$OUT"
(cd "$STAGE" && zip -qr "$OLDPWD/$OUT" "$NAME")
rm -rf "$STAGE"

echo "Built $OUT"
