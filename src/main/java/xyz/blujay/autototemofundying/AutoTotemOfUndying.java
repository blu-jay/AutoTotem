package xyz.blujay.autototemofundying;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AutoTotemOfUndying extends JavaPlugin {

    private static AutoTotemOfUndying plugin;

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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AutoTotemOfUndying getPlugin() {
        return plugin;
    }
}
