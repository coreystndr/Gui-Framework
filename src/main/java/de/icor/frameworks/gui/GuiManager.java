package de.icor.frameworks.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.UUID;

public class GuiManager implements Listener {
    private final HashMap<UUID, GuiBuilder> openGuis = new HashMap<>();

    public void registerGui(UUID player, GuiBuilder gui) {
        openGuis.put(player, gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        UUID player = event.getWhoClicked().getUniqueId();
        if (openGuis.containsKey(player)) {
            openGuis.get(player).handleClick(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        openGuis.remove(player);
    }

    public GuiBuilder getGui(UUID player) {
        return openGuis.get(player);
    }
}