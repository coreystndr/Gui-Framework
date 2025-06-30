package de.icor.frameworks.gui;

import de.icor.frameworks.Frameworks;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.function.Consumer;

public class GuiBuilder {

    @Getter
    private final Inventory inventory;
    private final Plugin plugin;

    private final Map<Integer, ItemStack> itemBuffer = new HashMap<>();
    private final Map<Integer, Consumer<InventoryClickEvent>> clickHandlers = new HashMap<>();

    private AnimationType animationType = null;
    private int animationDelay = 1;
    private Sound sound = null;
    private float volume = 1f;
    private float pitch = 1f;

    public GuiBuilder(String title, GuiType type, Plugin plugin) {
        int size = 0;
        switch (type) {
            case ONE_ROW -> size = 9;
            case TWO_ROWS -> size = 18;
            case THREE_ROWS -> size = 27;
            case FOUR_ROWS -> size = 36;
            case FIVE_ROWS -> size = 45;
            case SIX_ROWS -> size = 54;
            case SEVEN_ROWS -> size = 63;
            case EIGHT_ROWS -> size = 72;
        }
        this.inventory = Bukkit.createInventory(null, size, title);
        this.plugin = plugin;
    }

    public int slotConverter(Slots slot) {
        return switch (slot) {
            case RowOneSlotOne -> 0;
            case RowOneSlotTwo -> 1;
            case RowOneSlotThree -> 2;
            case RowOneSlotFour -> 3;
            case RowOneSlotFive -> 4;
            case RowOneSlotSix -> 5;
            case RowOneSlotSeven -> 6;
            case RowOneSlotEight -> 7;
            case RowOneSlotNine -> 8;

            case RowTwoSlotOne -> 9;
            case RowTwoSlotTwo -> 10;
            case RowTwoSlotThree -> 11;
            case RowTwoSlotFour -> 12;
            case RowTwoSlotFive -> 13;
            case RowTwoSlotSix -> 14;
            case RowTwoSlotSeven -> 15;
            case RowTwoSlotEight -> 16;
            case RowTwoSlotNine -> 17;

            case RowThreeSlotOne -> 18;
            case RowThreeSlotTwo -> 19;
            case RowThreeSlotThree -> 20;
            case RowThreeSlotFour -> 21;
            case RowThreeSlotFive -> 22;
            case RowThreeSlotSix -> 23;
            case RowThreeSlotSeven -> 24;
            case RowThreeSlotEight -> 25;
            case RowThreeSlotNine -> 26;

            case RowFourSlotOne -> 27;
            case RowFourSlotTwo -> 28;
            case RowFourSlotThree -> 29;
            case RowFourSlotFour -> 30;
            case RowFourSlotFive -> 31;
            case RowFourSlotSix -> 32;
            case RowFourSlotSeven -> 33;
            case RowFourSlotEight -> 34;
            case RowFourSlotNine -> 35;

            case RowFiveSlotOne -> 36;
            case RowFiveSlotTwo -> 37;
            case RowFiveSlotThree -> 38;
            case RowFiveSlotFour -> 39;
            case RowFiveSlotFive -> 40;
            case RowFiveSlotSix -> 41;
            case RowFiveSlotSeven -> 42;
            case RowFiveSlotEight -> 43;
            case RowFiveSlotNine -> 44;

            case RowSixSlotOne -> 45;
            case RowSixSlotTwo -> 46;
            case RowSixSlotThree -> 47;
            case RowSixSlotFour -> 48;
            case RowSixSlotFive -> 49;
            case RowSixSlotSix -> 50;
            case RowSixSlotSeven -> 51;
            case RowSixSlotEight -> 52;
            case RowSixSlotNine -> 53;

            case RowSevenSlotOne -> 54;
            case RowSevenSlotTwo -> 55;
            case RowSevenSlotThree -> 56;
            case RowSevenSlotFour -> 57;
            case RowSevenSlotFive -> 58;
            case RowSevenSlotSix -> 59;
            case RowSevenSlotSeven -> 60;
            case RowSevenSlotEight -> 61;
            case RowSevenSlotNine -> 62;

            case RowEightSlotOne -> 63;
            case RowEightSlotTwo -> 64;
            case RowEightSlotThree -> 65;
            case RowEightSlotFour -> 66;
            case RowEightSlotFive -> 67;
            case RowEightSlotSix -> 68;
            case RowEightSlotSeven -> 69;
            case RowEightSlotEight -> 70;
            case RowEightSlotNine -> 71;
        };
    }


    public GuiBuilder button(Slots slot, ItemStack item, Consumer<InventoryClickEvent> onClick) {
        int slotInt = slotConverter(slot);

        itemBuffer.put(slotInt, item);
        if (onClick != null) {
            clickHandlers.put(slotInt, onClick);
        } else {
            clickHandlers.remove(slotInt);
        }
        return this;
    }

    public GuiBuilder setItem(Slots slot, ItemStack item) {
        return button(slot, item, null);
    }

    public GuiBuilder fill(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (!itemBuffer.containsKey(i)) {
                itemBuffer.put(i, item);
                clickHandlers.put(i, event -> event.setCancelled(true));
            }
        }
        return this;
    }


    public GuiBuilder fillBorder(ItemStack item) {
        int size = inventory.getSize();
        int rows = size / 9;

        for (int i = 0; i < size; i++) {
            int row = i / 9;
            int col = i % 9;

            boolean isBorder = row == 0 || row == rows - 1 || col == 0 || col == 8;

            if (isBorder && !itemBuffer.containsKey(i)) {
                itemBuffer.put(i, item);
            }
        }
        return this;
    }

    public GuiBuilder animation(AnimationType type, int delayTicks) {
        this.animationType = type;
        this.animationDelay = delayTicks;
        return this;
    }


    public GuiBuilder sound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        return this;
    }

    public GuiBuilder open(Player player) {

        player.openInventory(inventory);

        Frameworks.getGuiManager().registerGui(player.getUniqueId(), this);

        if (animationType != null) {
            if (sound != null) {
                startAnimation(animationType, animationDelay, sound, volume, pitch);
            } else {
                startAnimation(animationType, animationDelay);
            }
        } else {
            build();
        }

        return this;
    }

    public GuiBuilder build() {
        itemBuffer.forEach(inventory::setItem);
        return this;
    }

    public void handleClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inventory)) return;

        Consumer<InventoryClickEvent> handler = clickHandlers.get(event.getSlot());
        if (handler != null) {
            handler.accept(event);
            event.setCancelled(true);
        }
    }

    private Player getPlayer(InventoryClickEvent event) {
        return (Player) event.getWhoClicked();
    }

    private void startAnimation(AnimationType type, int delayTicks) {
        List<Integer> orderedSlots = getOrderedSlots(type);

        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (index >= orderedSlots.size()) {
                    cancel();
                    return;
                }
                int slot = orderedSlots.get(index);
                ItemStack item = itemBuffer.get(slot);
                inventory.setItem(slot, item);
                index++;
            }
        }.runTaskTimer(plugin, 0L, delayTicks);
    }

    private void startAnimation(AnimationType type, int delayTicks, Sound sound, float volume, float pitch) {
        List<Integer> orderedSlots = getOrderedSlots(type);

        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (index >= orderedSlots.size()) {
                    cancel();
                    return;
                }
                int slot = orderedSlots.get(index);
                ItemStack item = itemBuffer.get(slot);
                inventory.setItem(slot, item);

                for (HumanEntity viewer : inventory.getViewers()) {
                    viewer.getWorld().playSound(viewer.getLocation(), sound, volume, pitch);
                }
                index++;
            }
        }.runTaskTimer(plugin, 0L, delayTicks);
    }

    private List<Integer> getOrderedSlots(AnimationType type) {
        int size = inventory.getSize();
        int rows = size / 9;
        List<Integer> allSlots = new ArrayList<>(itemBuffer.keySet());

        switch (type) {
            case LEFT_TO_RIGHT -> allSlots.sort(Comparator.comparingInt(i -> i));
            case RIGHT_TO_LEFT -> allSlots.sort(Comparator.comparingInt(i -> -i));
            case TOP_TO_BOTTOM -> allSlots.sort(Comparator.comparingInt(i -> i % 9));
            case BOTTOM_TO_TOP -> allSlots.sort(Comparator.comparingInt(i -> - (i % 9)));
            case CENTER_TO_OUTSIDE -> {
                allSlots.sort(Comparator.comparingDouble(i -> {
                    int x = i % 9;
                    int y = i / 9;
                    double dx = x - 4.0;
                    double dy = y - (rows - 1) / 2.0;
                    return dx * dx + dy * dy;
                }));
            }
            case OUTSIDE_TO_CENTER -> {
                allSlots.sort(Comparator.comparingDouble(i -> {
                    int x = i % 9;
                    int y = i / 9;
                    double dx = x - 4.0;
                    double dy = y - (rows - 1) / 2.0;
                    return - (dx * dx + dy * dy);
                }));
            }
            default -> {}
        }

        return allSlots;
    }
}
