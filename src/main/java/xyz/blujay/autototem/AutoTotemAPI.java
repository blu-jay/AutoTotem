package xyz.blujay.autototem;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class AutoTotemAPI {

    private int coolDown;
    public boolean includeVanillaTotemsInCooldown;
    private Map<UUID, Instant> coolDowns;
    public boolean hotBarOnly;


    AutoTotemAPI(final FileConfiguration config){
        setConfigOptions(config);
    }

    public void setConfigOptions(final FileConfiguration config){
        this.coolDown = config.getInt("cooldown");
        this.includeVanillaTotemsInCooldown = config.getBoolean("includeVanillaTotemsInCooldown");
        this.hotBarOnly = config.getBoolean("hotBarOnly");
        this.coolDowns = new HashMap<>();
    }

    //@return True if the player will use a totem when they die
    public boolean canUseTotem(final Player player){

        final var totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        final var playerInv = player.getInventory();
        final boolean playerHasTotem;

        if(hotBarOnly){
            final var offHand = playerInv.getItemInOffHand();
            final var hotBar1 = playerInv.getItem(0);
            final var hotBar2 = playerInv.getItem(1);
            final var hotBar3 = playerInv.getItem(2);
            final var hotBar4 = playerInv.getItem(3);
            final var hotBar5 = playerInv.getItem(4);
            final var hotBar6 = playerInv.getItem(5);
            final var hotBar7 = playerInv.getItem(6);
            final var hotBar8 = playerInv.getItem(7);
            final var hotBar9 = playerInv.getItem(8);

            playerHasTotem = ((offHand.isSimilar(totem)) ||
                    (hotBar1 != null && hotBar1.isSimilar(totem)) ||
                    (hotBar2 != null && hotBar2.isSimilar(totem)) ||
                    (hotBar3 != null && hotBar3.isSimilar(totem)) ||
                    (hotBar4 != null && hotBar4.isSimilar(totem)) ||
                    (hotBar5 != null && hotBar5.isSimilar(totem)) ||
                    (hotBar6 != null && hotBar6.isSimilar(totem)) ||
                    (hotBar7 != null && hotBar7.isSimilar(totem)) ||
                    (hotBar8 != null && hotBar8.isSimilar(totem)) ||
                    (hotBar9 != null && hotBar9.isSimilar(totem)) );
        }
        else{
            playerHasTotem = player.getInventory().containsAtLeast(totem, 1);
        }

        return playerHasTotem && player.hasPermission("autototem.use") && getCoolDown(player).isBefore(Instant.now());
    }

    //@return the instant when the player's cool down expires, and they can use a totem again.
    public Instant getCoolDown(final Player player){
        if(player.hasPermission("autototem.bypass")){
            return Instant.EPOCH;
        }

        return coolDowns.getOrDefault(player.getUniqueId(), Instant.EPOCH);
    }

    //Set a player's totem cool down X seconds into the future.
    public void setCoolDown(final Player player, final int coolDown){
        coolDowns.put(player.getUniqueId(), Instant.now().plusSeconds(coolDown));
    }

    //Set a player's totem cool down to the default number of seconds into the future.
    public void setCoolDown(final Player player){
        setCoolDown(player, this.coolDown);
    }
}
