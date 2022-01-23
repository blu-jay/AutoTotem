package xyz.blujay.autototem.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import xyz.blujay.autototem.AutoTotem;

public class PlayerKilledEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityResurrectEvent e){
        if(e.getEntity() instanceof Player p){
            var logger = AutoTotem.getPlugin().getLogger();

            if(e.isCancelled()){
                var inv = p.getInventory();
                if(inv.containsAtLeast(new ItemStack(Material.TOTEM_OF_UNDYING), 1) && p.hasPermission("autototem.use")){
                    inv.removeItem(new ItemStack(Material.TOTEM_OF_UNDYING));
                    e.setCancelled(false);
                }
            }
        }
    }
}
