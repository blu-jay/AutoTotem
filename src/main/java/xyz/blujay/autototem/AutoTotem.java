package xyz.blujay.autototem;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.blujay.autototem.commands.AutoTotemCommand;
import xyz.blujay.autototem.commands.ReloadCommand;
import xyz.blujay.autototem.events.PlayerDamagedEvent;
import xyz.blujay.autototem.events.PlayerDeathEvent;
import xyz.blujay.autototem.events.PlayerResurrectionEvent;
import xyz.blujay.autototem.utilities.Metrics;

public final class AutoTotem extends JavaPlugin {

    private static AutoTotemAPI api;

    @Override
    public void onEnable() {
        // Plugin startup logic
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

        var pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerResurrectionEvent(), this);
        pluginManager.registerEvents(new PlayerDamagedEvent(), this);
        pluginManager.registerEvents(new PlayerDeathEvent(), this);

        this.getLogger().info("AutoTotem has started!");
    }

    public void reload(){
        reloadConfig();
        api.setConfigOptions(getConfig());
    }

    public static AutoTotem getInstance() {
        return getPlugin(AutoTotem.class);
    }

    public AutoTotemAPI getAPI(){
        return api;
    }
}
