package id.alphareso.meidofx.base.sidebars;

import java.util.function.Consumer;

import static id.alphareso.meidofx.base.sidebars.SidebarMenuFactory.*;
import static id.alphareso.meidofx.base.sidebars.SidebarNavigation.MenuItemBuilder;

/**
 * Enum-based configuration for sidebar menus.
 * Define ID, title, icon (SVG), and structure here.
 */
public enum SidebarMenuConfig {

    DASHBOARD("dashboard", "Dashboard", "M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"),

    USERS("users", "Users", "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"),

    SETTINGS("settings", "Settings", "M19.14 12.94c.04-.3.06-.61.06-.94 0-.32-.02-.64-.07-.94l2.03-1.58..."),

    HELP("help", "Help & Support", "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 17h-2v-2h2v2zm2.07-7.75...");

    public final String id;
    public final String title;
    public final String svg;

    SidebarMenuConfig(String id, String title, String svg) {
        this.id = id;
        this.title = title;
        this.svg = svg;
    }

    /**
     * Builds the menu item as a standalone (direct action).
     */
    public MenuItemBuilder buildStandalone(Consumer<SidebarNavigation.SidebarMenuItem> action) {
        return standaloneMenu(id, title, svg, action);
    }

    /**
     * Builds the menu item as a root (with possible sub items).
     */
    public MenuItemBuilder buildRoot(Consumer<SidebarNavigation.SidebarMenuItem> action) {
        return rootMenu(id, title, svg, action);
    }

    public MenuItemBuilder buildRoot() {
        return rootMenu(id, title, svg);
    }
}
