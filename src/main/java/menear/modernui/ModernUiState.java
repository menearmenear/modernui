package menear.modernui;

/** True only while the vanilla TitleScreen is being extracted this frame. */
public final class ModernUiState {
    public static volatile boolean titleScreenActive = false;

    private ModernUiState() {}
}
