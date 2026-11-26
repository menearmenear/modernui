package menear.modernui.mixin;

import menear.modernui.ModernUiState;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Removes the yellow rotating splash text from the glassy title screen. */
@Mixin(SplashRenderer.class)
public abstract class SplashRendererMixin {

    @Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
    private void modernui$noSplash(GuiGraphicsExtractor g, int i, Font font,
                                   float f, CallbackInfo ci) {
        if (ModernUiState.titleScreenActive) ci.cancel();
    }
}
