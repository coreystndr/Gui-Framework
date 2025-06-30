package de.icor.frameworks.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreboardBuilder {

    private final Scoreboard scoreboard;
    private final Objective objective;
    private final String objectiveName = "customScoreboard";

    // LinkedHashMap für geordnete Zeilen: Key = Zeilenname/Text, Value = Score (Reihenfolge)
    private final Map<String, Integer> lines = new LinkedHashMap<>();

    public ScoreboardBuilder(String title) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) throw new IllegalStateException("ScoreboardManager not found!");
        this.scoreboard = manager.getNewScoreboard();

        this.objective = scoreboard.registerNewObjective(objectiveName, "dummy", title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    /**
     * Fügt eine Zeile zum Scoreboard hinzu.
     * Die Reihenfolge der Zeilen ergibt sich aus der Reihenfolge des Aufrufs.
     * @param line Text der Zeile
     * @return this für chaining
     */
    public ScoreboardBuilder addLine(String line) {
        int score = 15 - lines.size(); // Score muss zwischen 0 und 15 liegen (max 16 Zeilen)
        if (score < 0) score = 0;

        lines.put(line, score);
        return this;
    }

    /**
     * Entfernt eine Zeile.
     */
    public ScoreboardBuilder removeLine(String line) {
        lines.remove(line);
        return this;
    }

    /**
     * Setzt den Titel neu (nicht automatisch im Scoreboard geändert, da Bukkit das nicht erlaubt)
     * Um den Titel zu ändern, musst du ein neues Objective registrieren.
     */
    public ScoreboardBuilder setTitle(String title) {
        objective.setDisplayName(title);
        return this;
    }

    /**
     * Baut das Scoreboard und weist es einem Spieler zu.
     */
    public void build(Player player) {
        objective.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

        // Clear vorherige Zeilen falls da noch welche sind
        for (String line : lines.keySet()) {
            Score score = objective.getScore(line);
            score.setScore(lines.get(line));
        }

        player.setScoreboard(scoreboard);
    }

    /**
     * Aktualisiert eine Zeile oder fügt sie neu hinzu.
     */
    public ScoreboardBuilder updateLine(String line) {
        if (!lines.containsKey(line)) {
            addLine(line);
        } else {
            // Score beibehalten
        }
        return this;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }
}
