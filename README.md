# Modern UI (menear.modernui)

Glassy, ~95% transparent title screen for Fabric, Minecraft 26.3.

- Needs JDK 25.
- Gradle wrapper isn't included: copy `gradlew`, `gradlew.bat` and `gradle/` from the
  Fabric example mod, or run `gradle wrapper --gradle-version 9.7.1`.
- `./gradlew runClient` to test, `./gradlew build` for the jar in `build/libs`.

## What it does

- Transparent window: `SDL_WINDOW_TRANSPARENT` + 8-bit alpha are injected into
  RenderPearl's `GlBackend` / `VulkanBackend` window creation.
- ~5% black veil (`0x0D000000` in `GlassRenderer`) so ~95% of the desktop shows.
- Frosted, rounded text buttons with a smooth, clearly visible hover glow. The
  old white line on top of each button is gone.
- Frosted backing for the friends / language / accessibility icon buttons.
- Optional glassy logo textures (see below).

Some SDL video drivers/compositors (notably some Wayland setups) may still ignore
transparent windows — try `SDL_VIDEODRIVER=x11 ./gradlew runClient` if it looks opaque.

## Glassy logo

Generate the two transparent PNGs described in `ASSET_PROMPTS.md` and place them at:

```
src/main/resources/assets/modernui/textures/gui/title/minecraft_glass.png
src/main/resources/assets/modernui/textures/gui/title/edition_glass.png
```

They are used automatically when present; vanilla is the fallback otherwise.
Rebuild after adding them.
