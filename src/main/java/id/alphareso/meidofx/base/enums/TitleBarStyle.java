package id.alphareso.meidofx.base.enums;

/**
 * Enumeration defining the various display configurations for the title bar.
 * These configurations determine which components (icon, title, control buttons)
 * are visible in the title bar.
 */
public enum TitleBarStyle {
    /**
     * Display all components: application icon, title text, and window control buttons.
     */
    ALL(true, true),

    /**
     * Hide the left components (icon and title) and show only the window control buttons.
     */
    NO_LEFT(false, true),

    /**
     * Show only the left components (icon and title) and hide the window control buttons.
     */
    NO_RIGHT(true, false),

    /**
     * Hide all components, resulting in a minimal title bar that can still be used for
     * dragging the window but shows no visual elements.
     */
    NO_ALL(false, false);

    private final boolean showLeftComponents;
    private final boolean showRightComponents;

    /**
     * Constructor for TitleBarStyle enumeration.
     *
     * @param showLeftComponents Whether to show the left components (icon and title)
     * @param showRightComponents Whether to show the right components (window control buttons)
     */
    TitleBarStyle(boolean showLeftComponents, boolean showRightComponents) {
        this.showLeftComponents = showLeftComponents;
        this.showRightComponents = showRightComponents;
    }

    /**
     * Determines if the left components (icon and title) should be shown.
     *
     * @return true if left components should be shown, false otherwise
     */
    public boolean showLeftComponents() {
        return showLeftComponents;
    }

    /**
     * Determines if the right components (window control buttons) should be shown.
     *
     * @return true if right components should be shown, false otherwise
     */
    public boolean showRightComponents() {
        return showRightComponents;
    }
}