package de.icor.frameworks.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CommandManager {

    private final Plugin plugin;
    private final CommandMap commandMap;

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
        this.commandMap = getCommandMap();
    }

    public void register(Object commandInstance) {

        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(plugin, () -> register(commandInstance));
            return;
        }

        Class<?> clazz = commandInstance.getClass();

        if (!clazz.isAnnotationPresent(Command.class)) return;
        Command annotation = clazz.getAnnotation(Command.class);

        try {
            Method executeMethod = clazz.getDeclaredMethod("execute", Player.class);

            CommandWrapper commandWrapper = new CommandWrapper(
                    annotation.name(),
                    annotation.permission(),
                    commandInstance,
                    executeMethod
            );

            commandMap.register(plugin.getName(), commandWrapper);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CommandMap getCommandMap() {
        try {
            Field commandMapField = plugin.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(plugin.getServer());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class CommandWrapper extends org.bukkit.command.Command {
        private final Object instance;
        private final Method executeMethod;
        private final String permission;

        protected CommandWrapper(String name, String permission, Object instance, Method executeMethod) {
            super(name);
            this.instance = instance;
            this.executeMethod = executeMethod;
            this.permission = permission;
            if (!permission.isEmpty()) {
                this.setPermission(permission);
                this.setPermissionMessage("§cKeine Rechte.");
            }
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cNur Spieler dürfen diesen Befehl ausführen.");
                return true;
            }

            if (!permission.isEmpty() && !player.hasPermission(permission)) {
                player.sendMessage(getPermissionMessage());
                return true;
            }

            try {
                executeMethod.invoke(instance, player);
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("§cFehler beim Ausführen des Befehls.");
            }

            return true;
        }
    }
}
