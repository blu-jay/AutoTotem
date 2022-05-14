package xyz.blujay.autototem.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import xyz.blujay.autototem.AutoTotem;

public class PlayerResurrectionEvent implements Listener {

    @EventHandler
    public void onPlayerResurrect(EntityResurrectEvent e){
        if(e.getEntity() instanceof Player p){
            var inv = p.getInventory();
            //If player has a totem in their hand
            if(inv.getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING) || inv.getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING)){
                var plugin = AutoTotem.getInstance();
                var api = plugin.getAPI();

                //cancel the event if we are including vanilla totems in cooldown and if the player is currently on cooldown.
                if(api.includeVanillaTotemsInCooldown && api.isPlayerOnCoolDown(p)){
                    //(We know that this is a vanilla totem because PlayerDamageEvent will only swap the totem to the offhand when the player is off cooldown)
                    e.setCancelled(true);
                }
                else{
                    api.startCooldown(p, api.isPlayerOnCoolDown(p));
                }
            }
        }
    }
}