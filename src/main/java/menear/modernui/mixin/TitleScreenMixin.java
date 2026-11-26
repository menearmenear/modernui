package menear.modernui.mixin;

import menear.modernui.GlassRenderer;
import menear.modernui.ModernUiState;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * TitleScreen extracts the panorama *before* calling super.extractRenderState,
 * so the flag has to be set here, not in ScreenEvents.beforeExtract.
 */
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    @Inject(method = "extractRenderState", at = @At("HEAD"))
    private void modernui$begin(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
                                float delta, CallbackInfo ci) {
        ModernUiState.titleScreenActive = true;
        TitleScreen screen = (TitleScreen) (Object) this;
        GlassRenderer.background(graphics, screen.width, screen.height);
    }

    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void modernui$end(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
                              float delta, CallbackInfo ci) {
        ModernUiState.titleScreenActive = false;
    }

    @Inject(method = "extractPanorama", at = @At("HEAD"), cancellable = true, require = 0)
    private void modernui$noPanorama(GuiGraphicsExtractor graphics, float partialTick, CallbackInfo ci) {
        if (ModernUiState.titleScreenActive) ci.cancel();
    }
}
