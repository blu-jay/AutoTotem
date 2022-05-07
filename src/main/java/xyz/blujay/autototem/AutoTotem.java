package xyz.blujay.autototem;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.blujay.autototem.commands.AutoTotemCommand;
import xyz.blujay.autototem.commands.ReloadCommand;
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

        api = new AutoTotemAPI(getConfig());

        int pluginId = 14038;
        new Metrics(this, pluginId);

        PluginCommand autoTotemCommand = getCommand("AutoTotem");
        PluginCommand reloadCommand = getCommand("reload");

        if (autoTotemCommand != null && reloadCommand != null){
            autoTotemCommand.setExecutor(new AutoTotemCommand());
            reloadCommand.setExecutor(new ReloadCommand());
        }
        else{
            getLogger().warning("getCommand returned null, unable to set executors for commands. disabling plugin...");
            getPluginLoader().disablePlugin(this);
        }

        getServer().getPluginManager().registerEvents(new PlayerKilledEvent(), this);
        this.getLogger().info("AutoTotem has started!");
    }

    public void reload(){
        reloadConfig();
        api.setConfigOptions(getConfig());
    }

    public static AutoTotem getPlugin() {
        return plugin;
    }

    public AutoTotemAPI getAPI(){
        return api;
    }
}
