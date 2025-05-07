package id.alphareso.meidofx.base.sidebars;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A vertical navigation sidebar component that provides a customizable menu structure
 * with icon support and nested menu items.
 */
public class SidebarNavigation extends StackPane {

    private final VBox menuContainer;
    private final List<SidebarMenuItem> menuItems;
    private final List<Consumer<SidebarMenuItem>> selectionListeners;
    private SidebarMenuItem selectedMenuItem;
    private double prefWidth;

    /**
     * Creates a new sidebar navigation panel with default width of 200px.
     */
    public SidebarNavigation() {
        this(200);
    }

    /**
     * Creates a new sidebar navigation panel with specified width.
     *
     * @param width The preferred width of the sidebar
     */
    public SidebarNavigation(double width) {
        this.prefWidth = width;
        this.menuItems = new ArrayList<>();
        this.selectionListeners = new ArrayList<>();

        // Create menu container
        menuContainer = new VBox();
        menuContainer.setSpacing(2);
        menuContainer.setPadding(new Insets(10));
        menuContainer.getStyleClass().add("sidebar-menu-container");

        // Create scroll pane for the menu container
        ScrollPane scrollPane = new ScrollPane(menuContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.getStyleClass().add("sidebar-scroll-pane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Set up styling
        this.setPrefWidth(prefWidth);
        this.getStyleClass().add("sidebar-navigation");

        // Add the scroll pane to the stack pane
        this.getChildren().add(scrollPane);
    }

    /**
     * Sets the background style of the sidebar.
     *
     * @param style CSS style for the background
     * @return This sidebar instance for method chaining
     */
    public SidebarNavigation setBackgroundStyle(String style) {
        this.setStyle(style);
        return this;
    }

    /**
     * Sets the width of the sidebar.
     *
     * @param width The new width value
     * @return This sidebar instance for method chaining
     */
    // setWidth(double width)
    public SidebarNavigation setNewWidth(double width) {
        this.prefWidth = width;
        this.setPrefWidth(width);
        return this;
    }

    /**
     * Adds a menu section header to the sidebar.
     *
     * @param title The section title text
     * @return This sidebar instance for method chaining
     */
    public SidebarNavigation addSection(String title) {
        Label sectionLabel = new Label(title);
        sectionLabel.getStyleClass().add("sidebar-section-label");
        sectionLabel.setPadding(new Insets(10, 5, 5, 5));
        menuContainer.getChildren().add(sectionLabel);
        return this;
    }

    /**
     * Adds a menu item to the sidebar.
     *
     * @param builder The menu item builder
     * @return This sidebar instance for method chaining
     */
    public SidebarNavigation addMenuItem(MenuItemBuilder builder) {
        SidebarMenuItem menuItem = builder.build();
        menuItems.add(menuItem);
        menuContainer.getChildren().add(menuItem);

        // Set up selection handling
        menuItem.setOnMouseClicked(event -> selectMenuItem(menuItem));

        return this;
    }

    /**
     * Adds a selection listener that will be called when a menu item is selected.
     *
     * @param listener The listener to be called with the selected menu item
     * @return This sidebar instance for method chaining
     */
    public SidebarNavigation addSelectionListener(Consumer<SidebarMenuItem> listener) {
        selectionListeners.add(listener);
        return this;
    }

    /**
     * Selects the specified menu item and deselects any previously selected item.
     *
     * @param menuItem The menu item to select
     */
    public void selectMenuItem(SidebarMenuItem menuItem) {
        // Deselect the previously selected item
        if (selectedMenuItem != null) {
            selectedMenuItem.setSelected(false);
        }

        // Select the new item
        selectedMenuItem = menuItem;
        selectedMenuItem.setSelected(true);

        // Notify listeners
        for (Consumer<SidebarMenuItem> listener : selectionListeners) {
            listener.accept(menuItem);
        }
    }

    /**
     * Selects a menu item by its ID.
     *
     * @param id The ID of the menu item to select
     * @return true if the item was found and selected, false otherwise
     */
    public boolean selectMenuItemById(String id) {
        for (SidebarMenuItem item : menuItems) {
            if (id.equals(item.getId())) {
                selectMenuItem(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of all menu items in the sidebar.
     *
     * @return List of sidebar menu items
     */
    public List<SidebarMenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * Gets the currently selected menu item.
     *
     * @return The currently selected menu item or null if none is selected
     */
    public SidebarMenuItem getSelectedMenuItem() {
        return selectedMenuItem;
    }

    /**
     * Represents a menu item in the sidebar navigation.
     */
    public static class SidebarMenuItem extends HBox {
        private final String id;
        private final String title;
        private final Node icon;
        private final List<SidebarMenuItem> subItems;
        private VBox subItemContainer;
        private boolean hasSubItems;
        private boolean expanded;
        private Consumer<SidebarMenuItem> onAction;

        /**
         * Creates a new sidebar menu item.
         *
         * @param id The unique identifier for this menu item
         * @param title The display title for this menu item
         * @param icon The icon node for this menu item
         */
        private SidebarMenuItem(String id, String title, Node icon) {
            this.id = id;
            this.title = title;
            this.icon = icon;
            this.subItems = new ArrayList<>();
            this.hasSubItems = false;
            this.expanded = false;

            // Setup the menu item layout
            this.setAlignment(Pos.CENTER_LEFT);
            this.setPadding(new Insets(8, 5, 8, 5));
            this.setSpacing(10);
            this.getStyleClass().add("sidebar-menu-item");

            // Add icon if provided
            if (icon != null) {
                this.getChildren().add(icon);
            }

            // Add title label
            Label titleLabel = new Label(title);
            titleLabel.getStyleClass().add("sidebar-menu-item-title");
            HBox.setHgrow(titleLabel, Priority.ALWAYS);
            this.getChildren().add(titleLabel);

            // Create container for sub-items
            subItemContainer = new VBox();
            subItemContainer.setSpacing(2);
            subItemContainer.setPadding(new Insets(2, 0, 2, 15));
            subItemContainer.setVisible(false);
            subItemContainer.setManaged(false);

            // Add event handler for selection
            this.setOnMouseClicked(event -> {
                if (hasSubItems) {
                    toggleExpanded();
                    event.consume();
                } else if (onAction != null) {
                    onAction.accept(this);
                }
            });
        }

        /**
         * Sets this menu item as selected or not.
         *
         * @param selected true to select this item, false to deselect
         */
        public void setSelected(boolean selected) {
            if (selected) {
                this.getStyleClass().add("selected");
            } else {
                this.getStyleClass().remove("selected");
            }
        }

        /**
         * Sets the action to be performed when this menu item is clicked.
         *
         * @param onAction The action consumer
         */
        public void setOnAction(Consumer<SidebarMenuItem> onAction) {
            this.onAction = onAction;
        }

        /**
         * Adds a sub-item to this menu item.
         *
         * @param subItem The sub-item to add
         */

        public void addSubItem(SidebarMenuItem subItem) {
            subItems.add(subItem);
            subItemContainer.getChildren().add(subItem);
            hasSubItems = true; // Set hasSubItems to true when adding the first subitem

            if (this.getChildren().size() == 2) { // Ensure arrow is added only once
                // Add expand/collapse indicator
                SVGPath arrow = new SVGPath();
                arrow.setContent("M0,0 L8,8 L0,16 Z");
                arrow.getStyleClass().add("sidebar-menu-arrow");
                this.getChildren().add(arrow);
            }

            // No need to replace the item, just make subItemContainer visible/managed
        }

        private void initSubItemContainer() {
            // Create container for sub-items (only if it doesn't exist)
            if (subItemContainer == null) {
                subItemContainer = new VBox();
                subItemContainer.setSpacing(2);
                subItemContainer.setPadding(new Insets(2, 0, 2, 15));
                subItemContainer.setVisible(false);
                subItemContainer.setManaged(false);

                // Add subItemContainer to the main HBox
                VBox container = new VBox();
                container.getChildren().addAll(this, subItemContainer);
                ((VBox) this.getParent()).getChildren().set(
                        ((VBox) this.getParent()).getChildren().indexOf(this),
                        container
                );
            }
        }

        /**
         * Toggles the expanded state of this menu item.
         */
        public void toggleExpanded() {
            expanded = !expanded;
            subItemContainer.setVisible(expanded);
            subItemContainer.setManaged(expanded); // Manage layout based on visibility

            // Update the arrow direction
            if (hasSubItems && this.getChildren().size() > 2) {
                Node arrow = this.getChildren().get(this.getChildren().size() - 1); // Get the last child (arrow)
                if (arrow instanceof SVGPath) {
                    if (expanded) {
                        ((SVGPath) arrow).setContent("M0,0 L16,0 L8,8 Z");
                    } else {
                        ((SVGPath) arrow).setContent("M0,0 L8,8 L0,16 Z");
                    }
                }
            }
        }

        /**
         * Gets the unique identifier for this menu item.
         *
         * @return The menu item ID
         */
        // getId()
        public String getMenuId() {
            return id;
        }

        /**
         * Gets the title of this menu item.
         *
         * @return The menu item title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Gets the icon of this menu item.
         *
         * @return The menu item icon
         */
        public Node getIcon() {
            return icon;
        }

        /**
         * Gets the list of sub-items for this menu item.
         *
         * @return The list of sub-items
         */
        public List<SidebarMenuItem> getSubItems() {
            return subItems;
        }

        /**
         * Checks if this menu item has sub-items.
         *
         * @return true if this item has sub-items, false otherwise
         */
        public boolean hasSubItems() {
            return hasSubItems;
        }

        /**
         * Checks if this menu item is expanded.
         *
         * @return true if this item is expanded, false otherwise
         */
        public boolean isExpanded() {
            return expanded;
        }
    }

    /**
     * Builder class for creating sidebar menu items.
     */
    public static class MenuItemBuilder {
        private String id;
        private String title;
        private Node icon;
        private String tooltipText;
        private Consumer<SidebarMenuItem> onAction;
        private final List<MenuItemBuilder> subItems = new ArrayList<>();

        /**
         * Creates a new menu item builder with the specified ID.
         *
         * @param id The unique identifier for the menu item
         * @return A new menu item builder
         */
        public static MenuItemBuilder create(String id) {
            return new MenuItemBuilder().withId(id);
        }

        /**
         * Sets the ID for this menu item.
         *
         * @param id The unique identifier
         * @return This builder instance for method chaining
         */
        public MenuItemBuilder withId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the title for this menu item.
         *
         * @param title The display title
         * @return This builder instance for method chaining
         */
        public MenuItemBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets an SVG icon for this menu item using a path string.
         *
         * @param svgPath The SVG path string
         * @return This builder instance for method chaining
         */
        public MenuItemBuilder withSvgIcon(String svgPath) {
            SVGPath path = new SVGPath();
            path.setContent(svgPath);
            path.getStyleClass().add("sidebar-menu-icon");
            this.icon = path;
            return this;
        }

        /**
         * Sets a custom icon node for this menu item.
         *
         * @param icon The icon node
         * @return This builder instance for method chaining
         */
        public MenuItemBuilder withIcon(Node icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Sets a tooltip for this menu item.
         *
         * @param tooltipText The tooltip text
         * @return This builder instance for method chaining
         */
        public MenuItemBuilder withTooltip(String tooltipText) {
            this.tooltipText = tooltipText;
            return this;
        }

        /**
         * Sets the action to be performed when this menu item is clicked.
         *
         * @param onAction The action consumer
         * @return This builder instance for method chaining
         */
        public MenuItemBuilder withAction(Consumer<SidebarMenuItem> onAction) {
            this.onAction = onAction;
            return this;
        }

        /**
         * Adds a sub-item to this menu item.
         *
         * @param subItemBuilder The sub-item builder
         * @return This builder instance for method chaining
         */
        public MenuItemBuilder withSubItem(MenuItemBuilder subItemBuilder) {
            this.subItems.add(subItemBuilder);
            return this;
        }

        /**
         * Builds the menu item with the configured properties.
         *
         * @return The built SidebarMenuItem instance
         */
        public SidebarMenuItem build() {
            if (id == null) {
                throw new IllegalStateException("Menu item ID cannot be null");
            }

            if (title == null) {
                title = id;
            }

            SidebarMenuItem menuItem = new SidebarMenuItem(id, title, icon);

            if (tooltipText != null) {
                Tooltip tooltip = new Tooltip(tooltipText);
                Tooltip.install(menuItem, tooltip);
            }

            if (onAction != null) {
                menuItem.setOnAction(onAction);
            }

            // Build and add sub-items if any
            for (MenuItemBuilder subItemBuilder : subItems) {
                SidebarMenuItem subItem = subItemBuilder.build();
                menuItem.addSubItem(subItem);
            }

            return menuItem;
        }
    }
}