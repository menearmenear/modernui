package menear.modernui.mixin;

import com.mojang.renderpearl.backend.vulkan.VulkanBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/** Same SDL window flag as the OpenGL path, for the Vulkan backend. */
@Mixin(VulkanBackend.class)
public abstract class VulkanBackendMixin {

    private static final long SDL_WINDOW_TRANSPARENT = 0x0000000040000000L;

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
