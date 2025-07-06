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
## Registrierung
```java
public final class Main extends JavaPlugin {
    @Getter private GuiManager guiManager;

    @Override
    public void onEnable() {
        guiManager = Gui.getGuiManager();
        Bukkit.getPluginManager().registerEvents(guiManager, this);
    }
}
```

## Maven
```bash
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```bash
<dependency>
    <groupId>com.github.coreystndr</groupId>
    <artifactId>Frameworks</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Gradle
```bash
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
```bash
dependencies {
    implementation 'com.github.coreystndr:Frameworks:1.0.0'
}
```
