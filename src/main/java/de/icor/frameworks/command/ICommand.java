package de.icor.frameworks.command;

import org.bukkit.command.CommandSender;

public interface ICommand {
    void execute(CommandSender sender, String label, String[] args);
}
