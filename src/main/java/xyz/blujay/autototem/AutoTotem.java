package xyz.blujay.autototem;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.blujay.autototem.commands.AutoTotemCommand;
import xyz.blujay.autototem.events.PlayerKilledEvent;
import xyz.blujay.autototem.utilities.Metrics;

public final class AutoTotem extends JavaPlugin {

    private static AutoTotem plugin;
    private static AutoTotemAPI api;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        api = new AutoTotemAPI(getConfig().getInt("cooldown"), getConfig().getBoolean("includeVanillaTotemsInCooldown"));

        int pluginId = 14038;
        new Metrics(this, pluginId);

        PluginCommand plugincommand = getCommand("AutoTotem");
        if (plugincommand != null) {
            plugincommand.setExecutor(new AutoTotemCommand());
        } else {
            getLogger().warning("plugincommand is null meaning we cannot set the \"AutoTotem\" command disabling plugin...");
            getPluginLoader().disablePlugin(this);
        }

        getServer().getPluginManager().registerEvents(new PlayerKilledEvent(), this);
        this.getLogger().info("AutoTotem has started!");
    }

    public static AutoTotem getPlugin() {
        return plugin;
    }

    public AutoTotemAPI getAPI(){
        return api;
    }
}
