package menear.modernui.mixin;

import com.mojang.renderpearl.backend.opengl.GlBackend;
import org.lwjgl.sdl.SDLVideo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * SDL3's OpenGL backend creates the window, not {@code Window}. Ask for an alpha
 * channel there and add SDL_WINDOW_TRANSPARENT so the compositor blends the
 * framebuffer (which we clear to transparent) with the desktop.
 */
@Mixin(GlBackend.class)
public abstract class GlBackendMixin {

    private static final long SDL_WINDOW_TRANSPARENT = 0x0000000040000000L;

    @Inject(method = "createWindow", at = @At("HEAD"))
    private void modernui$alphaChannel(CallbackInfoReturnable<Long> cir) {
        SDLVideo.SDL_GL_SetAttribute(SDLVideo.SDL_GL_ALPHA_SIZE, 8);
    }

    @ModifyArg(
            method = "createWindow",
            at = @At(value = "INVOKE",
                    target = "Lorg/lwjgl/sdl/SDLVideo;SDL_CreateWindow(Ljava/lang/CharSequence;IIJ)J"),
            index = 3
    )
    private long modernui$transparentFlag(long flags) {
        return flags | SDL_WINDOW_TRANSPARENT;
    }
}
