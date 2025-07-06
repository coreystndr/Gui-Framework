# GuiBuilder
Ein einfach zu verwendender GUI-Builder für Minecraft-Plugins (Spigot/Paper), mit dem sich benutzerdefinierte Inventar-GUIs erstellen und verwalten lassen.

## ✨ Features

- Unterstützung für alle Inventargrößen (z. B. `GuiType.SIX_ROWS`)
- Einfache Button-Registrierung mit Klick-Handlern
- Automatisches Border-Füllen oder Füllen
- Slot-Konvertierung (1D → 2D)
- Modular und leicht erweiterbar

- ## 📦 Abhängigkeiten

- Spigot oder Paper 1.21+
- Java 21+
- Optional: `ItemBuilder`-Klasse für einfaches ItemStack-Bauen

- ## 🧱 Beispiel

```java
GuiBuilder gui = new GuiBuilder("§aMein Menü", GuiType.SIX_ROWS, Main.getInstance())
    .fillBorder(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§").build())
    .button(Slots.RowTwoSlotFive, new ItemBuilder(Material.DIAMOND).setName("§bKlick mich!").build(), event -> {
        Player player = (Player) event.getWhoClicked();
        player.sendMessage("§aDu hast den Knopf gedrückt!");
    });

gui.open(player);
```
