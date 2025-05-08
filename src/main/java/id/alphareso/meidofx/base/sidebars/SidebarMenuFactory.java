package id.alphareso.meidofx.base.sidebars;

import java.util.function.Consumer;

import static id.alphareso.meidofx.base.sidebars.SidebarNavigation.MenuItemBuilder;

/**
 * Factory class for building SidebarNavigation menu items in a DRY and robust way.
 */
public class SidebarMenuFactory {

    /**
     * Creates a root menu item with a title and SVG icon (without action).
     *
     * @param id      the unique identifier
     * @param title   the display title
     * @param svgPath the SVG path
     * @return the configured MenuItemBuilder
     */
    public static MenuItemBuilder rootMenu(String id, String title, String svgPath) {
        return MenuItemBuilder.create(id)
                .withTitle(title)
                .withSvgIcon(svgPath);
    }

    /**
     * Creates a root menu item with a title, SVG icon, and action.
     *
     * @param id      the unique identifier
     * @param title   the display title
     * @param svgPath the SVG path
     * @param action  the click action
     * @return the configured MenuItemBuilder
     */
    public static MenuItemBuilder rootMenu(String id, String title, String svgPath, Consumer<SidebarNavigation.SidebarMenuItem> action) {
        return rootMenu(id, title, svgPath).withAction(action);
    }

    /**
     * Creates a sub menu item under a root item.
     *
     * @param id     the unique identifier
     * @param title  the display title
     * @param action the click action
     * @return the configured MenuItemBuilder
     */
    public static MenuItemBuilder subMenu(String id, String title, Consumer<SidebarNavigation.SidebarMenuItem> action) {
        return MenuItemBuilder.create(id)
                .withTitle(title)
                .withAction(action);
    }

    /**
     * Creates a standalone menu item (no children, direct navigation).
     *
     * @param id      the unique identifier
     * @param title   the display title
     * @param svgPath the SVG path
     * @param action  the click action
     * @return the configured MenuItemBuilder
     */
    public static MenuItemBuilder standaloneMenu(String id, String title, String svgPath, Consumer<SidebarNavigation.SidebarMenuItem> action) {
        return MenuItemBuilder.create(id)
                .withTitle(title)
                .withSvgIcon(svgPath)
                .withAction(action);
    }
}
