package xyz.blujay.autototem.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.blujay.autototem.AutoTotem;

public final class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        AutoTotem.getInstance().reload();
        sender.sendMessage(ChatColor.BLUE + "AutoTotem config.yml has been reloaded.");
        return true;
    }
}
