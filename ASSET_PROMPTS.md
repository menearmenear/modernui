# Glassy asset prompts

The mod draws all button panels in code, so **no images are needed for buttons**.
The logo and the "JAVA EDITION" line are textures — drop the two PNGs below into
the paths shown and the mod picks them up automatically (vanilla is the fallback).

```
src/main/resources/assets/modernui/textures/gui/title/
├── minecraft_glass.png   <- prompt 1
└── edition_glass.png     <- prompt 2
```

Rebuild the jar (`./gradlew build`) after adding them.

---

## Prompt 1 — `minecraft_glass.png` (256 x 64 px)

> A transparent PNG game UI asset, exactly 256 by 64 pixels, containing ONLY a
> glassmorphism version of the "Minecraft" wordmark. The wordmark is centered
> horizontally and sits in the TOP 44 pixels of the canvas (the bottom 20 pixels
> must stay fully transparent padding — the game crops the image to 256x44).
> The letters are the familiar chunky, slightly uneven Minecraft-style block
> lettering, but rebuilt as polished frosted glass: semi-transparent icy white
> and pale cyan-blue glass with a subtle gradient from cooler at the top to
> warmer white at the bottom. Every letter has a crisp bright specular rim on
> its upper-left edge, a soft inner frosted blur, faint chromatic fringing
> (cyan on one edge, warm white on the other), and small glint highlights like
> light catching beveled crystal. Behind the letters there is a barely visible
> soft glow halo, no solid box and no background plate. The entire canvas outside
> the letter shapes and halo is 100% transparent alpha (no checkerboard, no
> black, no white background). Clean, premium, modern glass UI aesthetic, crisp
> pixel-perfect edges suitable for a Minecraft GUI texture, straight-on flat
> 2D view, no perspective, no mockup, no drop shadow on the canvas, no extra
> text, no watermark.

## Prompt 2 — `edition_glass.png` (128 x 16 px)

> A transparent PNG game UI asset, exactly 128 by 16 pixels, containing ONLY the
> words "JAVA EDITION" in a glassmorphism style. Use small, clean, slightly
> blocky uppercase lettering, centered, occupying the TOP 14 pixels of the canvas
> (the last 2 rows fully transparent). The text is frosted translucent glass:
> pale icy white with a faint blue-cyan tint, a bright thin specular highlight
> along the top edge of each letter, a soft inner glow, and a very subtle
> chromatic fringe. No background rectangle, no border, no backdrop, no shadow
> on the canvas. Everything outside the letters is 100% transparent alpha.
> Matches the frosted glass wordmark style, crisp pixel-art-friendly edges,
> flat straight-on 2D game UI texture, no perspective, no extra text, no
> watermark.

---

## Optional — fully custom glass icons (not wired in by default)

The friends, language and accessibility buttons currently use the vanilla icons
drawn on top of a frosted-glass panel, so they already look glassy without any
assets. If you also want **replacement glass icons**, generate a single sprite
sheet of three 15x15 transparent icons spaced evenly in a 45x15 PNG:

> Three small 15x15 pixel transparent icons in a horizontal 45x15 sprite sheet,
> evenly spaced with no padding between cells: (1) two simplified friendly
> person silhouettes, (2) a globe with latitude/longitude lines, (3) a
> accessibility person-in-circle symbol. All three rendered as frosted glass —
> translucent icy white with pale cyan tint, bright thin rim highlight on the
> top-left, soft inner glow, subtle chromatic fringe. Pure transparent alpha
> background, no box behind each icon, flat straight-on game UI sprites,
> pixel-art-friendly crisp edges, no text, no watermark.

Tell me if you generate this sheet and I'll wire it into the icon buttons.
