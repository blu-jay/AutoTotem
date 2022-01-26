package xyz.blujay.autototem.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import xyz.blujay.autototem.AutoTotem;
import xyz.blujay.autototem.AutoTotemAPI;

public class PlayerKilledEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityResurrectEvent e){
        if(e.getEntity() instanceof Player p){
            if(e.isCancelled()){
                var inv = p.getInventory();
                if(new AutoTotemAPI().canUseTotem(p)){
                    inv.removeItem(new ItemStack(Material.TOTEM_OF_UNDYING));
                    e.setCancelled(false);
                }
            }
        }
    }
}
