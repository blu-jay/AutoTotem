package xyz.blujay.autototem.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.blujay.autototem.AutoTotem;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        var plugin = AutoTotem.getInstance();
        plugin.reload();
        sender.sendMessage(plugin.getAPI().prefix + "config.yml has been reloaded.");
        return true;
    }
}
