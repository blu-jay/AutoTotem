package xyz.blujay.autototem.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.blujay.autototem.AutoTotem;

public class PlayerDamagedEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamaged(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player p){
            //If player received lethal damage
            if(e.getFinalDamage() >= p.getHealth()){
                //Check if player has a totem in their inv
                var inv = p.getInventory();
                var firstTotemSlot = inv.first(Material.TOTEM_OF_UNDYING);
                if(firstTotemSlot != -1) {
                    var plugin = AutoTotem.getInstance();
                    var api = plugin.getAPI();

                    //if totem is not in players hand
                    if (!inv.getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING) && !inv.getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                        if (api.canUseTotem(p)) {

                            api.setCoolDown(p);

                            var totem = inv.getItem(firstTotemSlot);
                            if (totem != null) {
                                inv.removeItem(totem);

                                //Create copy of item in offhand.
                                var handItem = inv.getItemInOffHand().clone();
                                //Put Totem in offhand.
                                inv.setItemInOffHand(totem);

                                //Next tick put item back in offhand. (Do this because minecraft deletes any item that is in offhand even if it isn't a totem)
                                BukkitScheduler scheduler = Bukkit.getScheduler();
                                scheduler.runTaskLater(plugin, () -> inv.setItemInOffHand(handItem), 1);
                            }
                        }
                    }
                    else { //else totem is already in their hand
                        if (api.includeVanillaTotemsInCooldown && api.canUseTotem(p)) {
                            api.setCoolDown(p);
                        }
                    }
                }
            }
        }
    }
}
