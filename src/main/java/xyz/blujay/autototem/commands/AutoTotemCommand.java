package xyz.blujay.autototem.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AutoTotemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(
                ChatColor.GRAY + "---------------------",
                ChatColor.GRAY + "⋙ " + ChatColor.BLUE + "AutoTotem",
                ChatColor.GRAY + "⋙ " + ChatColor.BLUE + "Created by blujay",
                ChatColor.GRAY + "⋙ " + ChatColor.BLUE + "Like my work? You can support me!",
                ChatColor.GRAY + "⋙ " + ChatColor.BLUE + "Source Code: " + ChatColor.AQUA + ChatColor.UNDERLINE + "github.com/blu-jay/AutoTotem",
                ChatColor.GRAY + "⋙ " + ChatColor.BLUE + "Donations: " + ChatColor.AQUA + ChatColor.UNDERLINE + "paypal.com/paypalme/blujayxyz",
                ChatColor.GRAY + "⋙ " + ChatColor.BLUE + "Website: " + ChatColor.AQUA + ChatColor.UNDERLINE + "blujay.xyz",
                ChatColor.GRAY + "---------------------"
                );
        return true;
    }
}
