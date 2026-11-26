package menear.modernui;

import net.fabricmc.api.ClientModInitializer;

public class ModernUiClient implements ClientModInitializer {
    public static final String MOD_ID = "modernui";

    @Override
    public void onInitializeClient() {
        // All drawing / state changes are done by the mixins (TitleScreenMixin,
        // AbstractWidgetMixin, GlBackendMixin) so the flag is set early enough
        // to cancel the panorama before it is extracted.
    }
}
