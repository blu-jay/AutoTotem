package xyz.blujay.autototem.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.blujay.autototem.AutoTotem;
import xyz.blujay.autototem.AutoTotemAPI;

import static org.bukkit.EntityEffect.TOTEM_RESURRECT;

public class PlayerKilledEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityResurrectEvent e){
        if(e.getEntity() instanceof Player p){

            var plugin = AutoTotem.getPlugin();
            var api = plugin.getAPI();

            if(e.isCancelled()){
                var inv = p.getInventory();

                if(api.canUseTotem(p)){
                    api.setCoolDown(p);
                    inv.removeItem(new ItemStack(Material.TOTEM_OF_UNDYING));
                    e.setCancelled(false);

                    //Create copy of item in off hand.
                    var handItem = inv.getItemInOffHand().clone();
                    //Put Totem in off hand.
                    inv.setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));

                    //Next tick put item back in off hand. (Do this because minecraft deletes any item that is in off-hand even if it isn't a totem)
                    BukkitScheduler scheduler = Bukkit.getScheduler();
                    scheduler.runTaskLater(plugin, () -> {
                        inv.setItemInOffHand(handItem);
                    }, 1);
                }
            }
            else{
                //cancel event if player is on cool down.
                if(!api.canUseTotem(p)){
                    e.setCancelled(true);
                }
                else{
                    api.setCoolDown(p);
                }
            }
        }
    }
}
