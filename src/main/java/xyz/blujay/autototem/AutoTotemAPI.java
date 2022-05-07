package xyz.blujay.autototem;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoTotemAPI {

    private int coolDown;
    public boolean includeVanillaTotemsInCooldown;
    private Map<UUID, Instant> coolDowns;
    public boolean actionBarOnly;


    AutoTotemAPI(FileConfiguration config){
        setConfigOptions(config);
    }

    public void setConfigOptions(FileConfiguration config){
        this.coolDown = config.getInt("cooldown");
        this.includeVanillaTotemsInCooldown = config.getBoolean("includeVanillaTotemsInCooldown");
        this.actionBarOnly = config.getBoolean("actionBarOnly");
        this.coolDowns = new HashMap<>();
    }

    //@return True if the player will use a totem when they die
    public boolean canUseTotem(Player player){

        var totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        var playerInv = player.getInventory();
        boolean playerHasTotem;

        if(actionBarOnly){
            var offHand = playerInv.getItemInOffHand();
            var hotBar1 = playerInv.getItem(0);
            var hotBar2 = playerInv.getItem(1);
            var hotBar3 = playerInv.getItem(2);
            var hotBar4 = playerInv.getItem(3);
            var hotBar5 = playerInv.getItem(4);
            var hotBar6 = playerInv.getItem(5);
            var hotBar7 = playerInv.getItem(6);
            var hotBar8 = playerInv.getItem(7);
            var hotBar9 = playerInv.getItem(8);

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
    public Instant getCoolDown(Player player){
        if(player.hasPermission("autototem.bypass")){
            return Instant.EPOCH;
        }

        return coolDowns.getOrDefault(player.getUniqueId(), Instant.EPOCH);
    }

    //Set a player's totem cool down X seconds into the future.
    public void setCoolDown(Player player, int coolDown){
        coolDowns.put(player.getUniqueId(), Instant.now().plusSeconds(coolDown));
    }

    //Set a player's totem cool down to the default number of seconds into the future.
    public void setCoolDown(Player player){
        setCoolDown(player, this.coolDown);
    }
}
