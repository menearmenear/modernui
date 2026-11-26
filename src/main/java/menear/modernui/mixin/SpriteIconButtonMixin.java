package menear.modernui.mixin;

import menear.modernui.GlassRenderer;
import menear.modernui.ModernUiState;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.SpriteIconButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Frosted backing for the friends / language / accessibility icons. */
@Mixin(SpriteIconButton.class)
public abstract class SpriteIconButtonMixin {

    @Inject(method = "extractSprite", at = @At("HEAD"), require = 0)
    private void modernui$glassBacking(GuiGraphicsExtractor g, int x, int y, CallbackInfo ci) {
        if (!ModernUiState.titleScreenActive) return;
        GlassRenderer.iconBacking(g, (SpriteIconButton) (Object) this);
    }
}
