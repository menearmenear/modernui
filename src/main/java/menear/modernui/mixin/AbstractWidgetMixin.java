package menear.modernui.mixin;

import menear.modernui.GlassRenderer;
import menear.modernui.ModernUiState;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Swaps the vanilla look of the title screen's text buttons for glass. */
@Mixin(AbstractWidget.class)
public abstract class AbstractWidgetMixin {

    @Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
    private void modernui$glass(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
                                float delta, CallbackInfo ci) {
        if (!ModernUiState.titleScreenActive) return;
        if (!((Object) this instanceof Button button)) return;

        // Small sprite buttons (friends / language / accessibility) are handled by
        // SpriteIconButtonMixin so their icons stay visible.
        if (!button.visible || button.getWidth() < 60) return;

        // We cancel before vanilla can set its own isHovered flag, so work it out here.
        boolean hovered = button.active
                && mouseX >= button.getX() && mouseX < button.getX() + button.getWidth()
                && mouseY >= button.getY() && mouseY < button.getY() + button.getHeight();

        GlassRenderer.button(graphics, button, hovered);
        ci.cancel();
    }
}
