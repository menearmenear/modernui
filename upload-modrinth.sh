#!/usr/bin/env bash
# Upload the built jar to an existing Modrinth project.
# Usage: MODRINTH_TOKEN=... ./upload-modrinth.sh <project-slug-or-id>
set -euo pipefail

SLUG="${1:?usage: MODRINTH_TOKEN=... ./upload-modrinth.sh <project-slug>}"
: "${MODRINTH_TOKEN:?set MODRINTH_TOKEN to a valid Modrinth personal access token}"

JAR="build/libs/modernui-1.0.0.jar"
[ -f "$JAR" ] || { echo "missing $JAR - run ./gradlew build first" >&2; exit 1; }

DATA=$(cat <<EOF
{
  "project_id": "$SLUG",
  "version_number": "1.0.0",
  "name": "Modern UI 1.0.0",
  "changelog": "Glassy ~95% transparent title screen, frosted buttons with hover glow, glassy logo textures, no splash text.",
  "dependencies": [],
  "game_versions": ["26.3"],
  "version_types": ["release"],
  "loaders": ["fabric"],
  "featured": false,
  "primary_file": "modernui-1.0.0.jar"
}
EOF
)

curl -fS -X POST 'https://api.modrinth.com/v2/version' \
  -H "Authorization: $MODRINTH_TOKEN" \
  -H 'User-Agent: modernui-upload/1.0' \
  -F "data=$DATA" \
  -F "file=@$JAR;filename=modernui-1.0.0.jar" \
  | jq '{id,project_id,version_number,game_versions,loaders}'
