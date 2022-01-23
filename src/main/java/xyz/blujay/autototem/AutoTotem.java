package xyz.blujay.autototem;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.blujay.autototem.commands.AutoTotemCommand;
import xyz.blujay.autototem.events.PlayerKilledEvent;
import xyz.blujay.autototem.utilities.Metrics;

import java.util.concurrent.Callable;

public final class AutoTotem extends JavaPlugin {

    private static AutoTotem plugin;

    private BukkitAudiences adventure;

    public @NonNull
    BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        this.adventure = BukkitAudiences.create(this);

        int pluginId = 14038;
        Metrics metrics = new Metrics(this, pluginId);


        getCommand("AutoTotem").setExecutor(new AutoTotemCommand());
        getServer().getPluginManager().registerEvents(new PlayerKilledEvent(), this);
        this.getLogger().info("AutoTotem has started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public static AutoTotem getPlugin() {
        return plugin;
    }
}
