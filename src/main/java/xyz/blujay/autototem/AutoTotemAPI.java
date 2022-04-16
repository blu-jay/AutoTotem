package xyz.blujay.autototem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoTotemAPI {

    private int coolDown;
    private Map<UUID, Instant> coolDowns = new HashMap<>();


    AutoTotemAPI(int coolDown){
        this.coolDown = coolDown;
    }

    //@return True if the player will use a totem when they die
    public boolean canUseTotem(Player player){
        if(player.getInventory().containsAtLeast(new ItemStack(Material.TOTEM_OF_UNDYING), 1) && player.hasPermission("autototem.use") && getCoolDown(player).isBefore(Instant.now())){
            return true;
        }
        else{
            return false;
        }
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
