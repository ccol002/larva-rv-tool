#!/bin/sh
# Builds a self-contained "download and experiment" zip of the LARVA
# converter on its own (no LARVA compiler itself). See
# release-converter/README.md for what ships.
#
# Usage: ./package-converter-release.sh [version]   (default version: 1.0.0)
set -e
cd "$(dirname "$0")"

VERSION="${1:-1.0.0}"
NAME="larva-converter-$VERSION"
STAGE="$(mktemp -d)"
ROOT="$STAGE/$NAME"
mkdir -p "$ROOT"

cp -r "Larva converter"/. "$ROOT/"
cp LICENSE "$ROOT/"
cp release-converter/README.md "$ROOT/README.md"

mkdir -p "$ROOT/Larva manuals"
cp "Larva manuals/Converter manual.pdf" "$ROOT/Larva manuals/"

cp -r "Larva converter example scripts" "$ROOT/example scripts"

OUT="$NAME.zip"
rm -f "$OUT"
(cd "$STAGE" && zip -qr "$OLDPWD/$OUT" "$NAME")
rm -rf "$STAGE"

echo "Built $OUT"
