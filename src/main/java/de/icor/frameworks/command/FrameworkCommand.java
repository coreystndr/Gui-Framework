package de.icor.frameworks.command;

import org.bukkit.command.CommandSender;

public interface FrameworkCommand {
    void execute(CommandSender sender, String[] args);
}
