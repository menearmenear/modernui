package menear.modernui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Map;
import java.util.WeakHashMap;

/** All the "glass" drawing: transparent veil, frosted panels and logo texture swaps. */
public final class GlassRenderer {
    // Corner shape (rows from the edge -> pixels cut off). Gives a ~3px radius.
    private static final int[] CORNER = {3, 1, 0};

    /** One eased value per widget, reused for text and icon buttons. */
    private static final Map<Object, Float> HOVER = new WeakHashMap<>();

    private GlassRenderer() {}

    // ---------------------------------------------------------------- background

    /**
     * Faint dark veil: alpha 0x0D = ~5% black, so the desktop shows through ~95%.
     * The window itself also has to be transparent (see GlBackendMixin / VulkanBackendMixin).
     */
    private static final int VEIL = 0x0D000000;

    public static void background(GuiGraphicsExtractor g, int w, int h) {
        g.fill(0, 0, w, h, VEIL);
    }

    // --------------------------------------------------------------------- logo

    /**
     * Drop-in glassy logo textures. If you generate the PNGs and put them at these
     * paths they are used automatically; otherwise vanilla is kept as the fallback.
     */
    private static final Identifier LOGO_GLASS =
            Identifier.parse("modernui:textures/gui/title/minecraft_glass.png");
    private static final Identifier EDITION_GLASS =
            Identifier.parse("modernui:textures/gui/title/edition_glass.png");

    public static Identifier logo(Identifier vanilla) {
        return exists(LOGO_GLASS) ? LOGO_GLASS : vanilla;
    }

    public static Identifier edition(Identifier vanilla) {
        return exists(EDITION_GLASS) ? EDITION_GLASS : vanilla;
    }

    private static boolean exists(Identifier id) {
        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        return rm != null && rm.getResource(id).isPresent();
    }

    // ------------------------------------------------------------------- buttons

    /**
     * Wide text button. {@code hot} is passed in from the mixin because we cancel
     * before vanilla gets a chance to set its own hover flag.
     */
    public static void button(GuiGraphicsExtractor g, Button b, boolean hot) {
        int x = b.getX(), y = b.getY(), w = b.getWidth(), h = b.getHeight();
        float cur = hover(b, hot && b.active);

        glassPanel(g, x, y, w, h, cur);

        Minecraft mc = Minecraft.getInstance();
        String label = b.getMessage().getString();
        int tw = mc.font.width(label);
        int tx = x + (w - tw) / 2;
        int ty = y + (h - mc.font.lineHeight) / 2 + 1;
        int color = !b.active ? 0x80AAAAAA : lerpColor(0xFFE6E9F2, 0xFFFFFFFF, cur);
        g.text(mc.font, label, tx, ty, color, true);
    }

    /**
     * Frosted backing for the small sprite buttons (friends / language /
     * accessibility). The vanilla icon is still drawn on top by the game.
     */
    public static void iconBacking(GuiGraphicsExtractor g, Button b) {
        int x = b.getX(), y = b.getY(), w = b.getWidth(), h = b.getHeight();
        float cur = hover(b, b.isActive() && b.isHoveredOrFocused());
        glassPanel(g, x, y, w, h, cur);
    }

    // ------------------------------------------------------------------- helpers

    private static float hover(Object key, boolean hot) {
        float target = hot ? 1f : 0f;
        float cur = HOVER.getOrDefault(key, 0f);
        cur += (target - cur) * 0.22f;
        if (Math.abs(target - cur) < 0.01f) cur = target;
        HOVER.put(key, cur);
        return cur;
    }

    /** Shared frosted panel: brighter + glowing border as hover rises, no top line. */
    private static void glassPanel(GuiGraphicsExtractor g, int x, int y, int w, int h, float cur) {
        if (cur > 0.02f) {
            int glowA = (int) (cur * 0x38);
            roundOutline(g, x - 1, y - 1, w + 2, h + 2, (glowA << 24) | 0xFFFFFF);
        }

        int topA = lerp(0x16, 0x5C, cur);
        int botA = lerp(0x08, 0x2E, cur);
        roundFill(g, x, y, w, h, (topA << 24) | 0xFFFFFF, (botA << 24) | 0xFFFFFF);

        int borderA = lerp(0x38, 0xFF, cur);
        roundOutline(g, x, y, w, h, (borderA << 24) | 0xFFFFFF);
    }

    private static int inset(int row, int h) {
        int d = Math.min(row, h - 1 - row);
        return d < CORNER.length ? CORNER[d] : 0;
    }

    private static void roundFill(GuiGraphicsExtractor g, int x, int y, int w, int h, int top, int bottom) {
        for (int i = 0; i < h; i++) {
            int in = inset(i, h);
            float f = h > 1 ? i / (float) (h - 1) : 0f;
            g.fill(x + in, y + i, x + w - in, y + i + 1, lerpColor(top, bottom, f));
        }
    }

    private static void roundOutline(GuiGraphicsExtractor g, int x, int y, int w, int h, int color) {
        for (int i = 0; i < h; i++) {
            int d = Math.min(i, h - 1 - i);
            int in = inset(i, h);
            if (d == 0) {
                g.fill(x + in, y + i, x + w - in, y + i + 1, color);
            } else {
                int prev = inset(i < h / 2 ? i - 1 : i + 1, h);
                int seg = Math.max(1, prev - in);
                g.fill(x + in, y + i, x + in + seg, y + i + 1, color);
                g.fill(x + w - in - seg, y + i, x + w - in, y + i + 1, color);
            }
        }
    }

    private static int lerp(int a, int b, float t) {
        return (int) (a + (b - a) * t);
    }

    private static int lerpColor(int c1, int c2, float t) {
        int a = lerp(c1 >>> 24, c2 >>> 24, t);
        int r = lerp((c1 >> 16) & 0xFF, (c2 >> 16) & 0xFF, t);
        int gr = lerp((c1 >> 8) & 0xFF, (c2 >> 8) & 0xFF, t);
        int b = lerp(c1 & 0xFF, c2 & 0xFF, t);
        return (a << 24) | (r << 16) | (gr << 8) | b;
    }
}
