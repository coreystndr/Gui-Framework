package de.icor.item;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class GradientText {

    private final String text;
    private final List<Integer> colors;
    private int offset = 0;

    private final JavaPlugin plugin;

    public GradientText(String text, List<Integer> colors, JavaPlugin plugin) {
        this.text = text;
        this.colors = colors;
        this.plugin = plugin;
    }

    public String getColoredText() {
        StringBuilder colored = new StringBuilder();

        int length = text.length();
        for (int i = 0; i < length; i++) {
            double progress = ((double)(i + offset) % length) / (length - 1);
            int color = interpolateColor(progress);
            colored.append(toMinecraftColor(color));
            colored.append(text.charAt(i));
        }

        return colored.toString();
    }

    private int interpolateColor(double progress) {
        if (colors.size() == 1) return colors.get(0);

        double scaledPos = progress * (colors.size() - 1);
        int index1 = (int) Math.floor(scaledPos);
        int index2 = Math.min(index1 + 1, colors.size() - 1);

        double fraction = scaledPos - index1;

        int c1 = colors.get(index1);
        int c2 = colors.get(index2);

        int r = (int) ( ((c2 >> 16) & 0xFF) * fraction + ((c1 >> 16) & 0xFF) * (1 - fraction));
        int g = (int) ( ((c2 >> 8) & 0xFF) * fraction + ((c1 >> 8) & 0xFF) * (1 - fraction));
        int b = (int) ( ((c2) & 0xFF) * fraction + ((c1) & 0xFF) * (1 - fraction));

        return (r << 16) | (g << 8) | b;
    }

    private String toMinecraftColor(int rgb) {
        char[] chars = Integer.toHexString(rgb).toCharArray();
        String hex = String.format("%06x", rgb);
        return "§x" +
                "§" + hex.charAt(0) +
                "§" + hex.charAt(1) +
                "§" + hex.charAt(2) +
                "§" + hex.charAt(3) +
                "§" + hex.charAt(4) +
                "§" + hex.charAt(5);
    }

    public void startAnimation(Player player, long ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                String colored = getColoredText();
                player.sendMessage(colored);
                offset = (offset + 1) % text.length();
            }
        }.runTaskTimer(plugin, 0, ticks);
    }
}
