package xyz.blujay.autototemofundying.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.blujay.autototemofundying.AutoTotemOfUndying;

public class AutoTotemOfUndyingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        var senderAudience = AutoTotemOfUndying.getPlugin().adventure().sender(sender);
        senderAudience.sendMessage(MiniMessage.get().parse("<gradient:#00BFFB:#FD00C5>---------------------</gradient>"));
        senderAudience.sendMessage(MiniMessage.get().parse("<gradient:#00BFFB:#FD00C5>⋙ Auto Totem Of Undying</gradient>"));
        senderAudience.sendMessage(MiniMessage.get().parse("<gradient:#00BFFB:#FD00C5>⋙ Created by blujay</gradient>"));
        senderAudience.sendMessage(MiniMessage.get().parse("<gradient:#00BFFB:#FD00C5>⋙ Like my work? You can support me!</gradient>"));
        senderAudience.sendMessage(MiniMessage.get().parse("<gradient:#00BFFB:#FD00C5>⋙ Source Code:</gradient> <blue><underlined>github.com/blu-jay/SplashArrows"));
        senderAudience.sendMessage(MiniMessage.get().parse("<gradient:#00BFFB:#FD00C5>⋙ Donations:</gradient> <blue><underlined>paypal.com/paypalme/blujayxyz"));
        senderAudience.sendMessage(MiniMessage.get().parse("<gradient:#00BFFB:#FD00C5>⋙ Website:</gradient> <blue><underlined>blujay.xyz"));
        senderAudience.sendMessage(MiniMessage.get().parse("<gradient:#00BFFB:#FD00C5>---------------------</gradient>"));

        return true;
    }
}
