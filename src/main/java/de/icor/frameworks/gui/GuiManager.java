package de.icor.frameworks.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.UUID;

public class GuiManager implements Listener {
    public static final HashMap<UUID, GuiBuilder> openGuis = new HashMap<>();

    public void registerGui(UUID player, GuiBuilder gui) {
        openGuis.put(player, gui);
    }

    public GuiBuilder getGui(UUID player) {
        return openGuis.get(player);
    }
}