package menear.modernui.mixin;

import menear.modernui.GlassRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;

/**
 * Uses your generated glass textures for the Minecraft wordmark and the
 * "JAVA EDITION" line when they exist, otherwise leaves vanilla untouched.
 */
@Mixin(LogoRenderer.class)
public abstract class LogoRendererMixin {

    @ModifyArgs(
            method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IFI)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit("
                            + "Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;"
                            + "Lnet/minecraft/resources/Identifier;IIFFIIIII)V")
    )
    private void modernui$glassLogo(Args args) {
        Identifier id = args.get(1);
        if (id.equals(LogoRenderer.MINECRAFT_LOGO)) {
            args.set(1, GlassRenderer.logo(id));
        } else if (id.equals(LogoRenderer.MINECRAFT_EDITION)) {
            args.set(1, GlassRenderer.edition(id));
        }
    }
}
