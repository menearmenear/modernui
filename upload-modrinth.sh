#!/usr/bin/env bash
# Upload the built jar to the Modern UI Modrinth project.
# Usage: MODRINTH_TOKEN=... ./upload-modrinth.sh [project-slug]
set -euo pipefail

SLUG="${1:-modernui-glass-menu}"
: "${MODRINTH_TOKEN:?set MODRINTH_TOKEN to a valid Modrinth personal access token}"

JAR="build/libs/modernui-1.0.0.jar"
FILE="modernui-1.0.0.jar"
[ -f "$JAR" ] || { echo "missing $JAR - run ./gradlew build first" >&2; exit 1; }

# The version API requires a base62 project id, not a slug.
PROJECT_ID=$(curl -sS -H "Authorization: $MODRINTH_TOKEN" \
  -H 'User-Agent: modernui-upload/1.0' \
  "https://api.modrinth.com/v2/project/$SLUG" | jq -r '.id')

if [ -z "$PROJECT_ID" ] || [ "$PROJECT_ID" = "null" ]; then
  echo "could not resolve project '$SLUG'" >&2
  exit 1
fi

DATA=$(cat <<EOF
{
  "project_id": "$PROJECT_ID",
  "version_number": "1.0.0",
  "name": "Modern UI 1.0.0",
  "changelog": "Glassy ~95% transparent title screen, frosted buttons with hover glow, glassy logo textures, no splash text.",
  "dependencies": [],
  "game_versions": ["26.3"],
  "version_type": "release",
  "loaders": ["fabric"],
  "featured": false,
  "file_parts": ["$FILE"],
  "primary_file": "$FILE"
}
EOF
)

curl -sS -X POST 'https://api.modrinth.com/v2/version' \
  -H "Authorization: $MODRINTH_TOKEN" \
  -H 'User-Agent: modernui-upload/1.0' \
  -F "data=$DATA" \
  -F "$FILE=@$JAR" \
  | jq '{id,project_id,version_number,status,error,description}'
