package xyz.blujay.autototem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AutoTotemAPI {
    //@return True if the player will use a totem when they die
    public boolean canUseTotem(Player player){
        if(player.getInventory().containsAtLeast(new ItemStack(Material.TOTEM_OF_UNDYING), 1) && player.hasPermission("autototem.use")){
            return true;
        }
        else{
            return false;
        }
    }
}
