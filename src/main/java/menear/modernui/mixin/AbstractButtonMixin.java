package menear.modernui.mixin;

import menear.modernui.ModernUiState;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.SpriteIconButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Hide the vanilla grey box behind the title screen's icon buttons. */
@Mixin(AbstractButton.class)
public abstract class AbstractButtonMixin {

    @Inject(method = "extractDefaultSprite", at = @At("HEAD"), cancellable = true, require = 0)
    private void modernui$noVanillaBox(GuiGraphicsExtractor g, CallbackInfo ci) {
        if (ModernUiState.titleScreenActive && (Object) this instanceof SpriteIconButton) {
            ci.cancel();
        }
    }
}
