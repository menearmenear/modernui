package menear.modernui.mixin;

import menear.modernui.ModernUiState;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** No panorama on the title screen. */
@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(method = "extractPanorama", at = @At("HEAD"), cancellable = true)
    private void modernui$noPanorama(GuiGraphicsExtractor graphics, float partialTick, CallbackInfo ci) {
        if (ModernUiState.titleScreenActive) ci.cancel();
    }
}
