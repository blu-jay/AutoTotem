package xyz.blujay.autototem;

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

        api = new AutoTotemAPI(getConfig().getInt("cooldown"));

        int pluginId = 14038;
        Metrics metrics = new Metrics(this, pluginId);


        getCommand("AutoTotem").setExecutor(new AutoTotemCommand());
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
